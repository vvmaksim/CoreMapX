package model.commands.classes

import model.commands.enums.CommandEntities
import model.commands.enums.CommandTypes
import model.graphs.classes.DirectedUnweightedGraph
import model.graphs.classes.DirectedWeightedGraph
import model.graphs.classes.UndirectedUnweightedGraph
import model.graphs.classes.UndirectedWeightedGraph
import model.graphs.dataClasses.Vertex
import model.graphs.interfaces.Graph

class Commands<E : Comparable<E>, V : Comparable<V>>(
    private val command: Command,
    private val graph: Graph<E, V>,
    private val outputMessages: MutableList<String>,
) {
    fun execute(): Result<String> =
        when (command.type) {
            CommandTypes.ADD -> {
                when (command.entity) {
                    CommandEntities.VERTEX -> addVertex()
                    CommandEntities.EDGE -> addEdge()
                    else -> Result.Error(CommandError.UnknownEntity("Unsupported entity for add command"))
                }
            }
            CommandTypes.RM -> {
                when (command.entity) {
                    CommandEntities.VERTEX -> removeVertex()
                    CommandEntities.EDGE -> removeEdge()
                    else -> Result.Error(CommandError.UnknownEntity("Unsupported entity for rm/remove command"))
                }
            }
            CommandTypes.CLEAR -> {
                when (command.entity) {
                    CommandEntities.GRAPH -> graphClear()
                    CommandEntities.COMMAND_OUTPUT -> clear()
                    else -> Result.Error(CommandError.UnknownEntity("Unsupported entity for clear command"))
                }
            }
            CommandTypes.HELP -> help()
        }

    private fun addVertex(): Result<String> {
        val id = command.parameters["id"] ?: return Result.Error(CommandError.MissingParameters("id is required"))
        val label = command.parameters["label"] ?: return Result.Error(CommandError.MissingParameters("label is required"))
        val idAsV = id.toIntOrNull() as? V ?: return Result.Error(CommandError.InvalidParameterType("id", "Int"))
        graph.addVertex(Vertex(idAsV, label))
        return Result.Success("Vertex added: id:$idAsV, label:$label")
    }

    private fun addEdge(): Result<String> {
        val from = command.parameters["from"] ?: return Result.Error(CommandError.MissingParameters("from is required"))
        val to = command.parameters["to"] ?: return Result.Error(CommandError.MissingParameters("to is required"))

        val fromAsV = from.toIntOrNull() as? V ?: return Result.Error(CommandError.InvalidParameterType("from", "Int"))
        val toAsV = to.toIntOrNull() as? V ?: return Result.Error(CommandError.InvalidParameterType("to", "Int"))

        val fromVertex = graph.getVertex(fromAsV) ?: return Result.Error(CommandError.VertexNotFound(from))
        val toVertex = graph.getVertex(toAsV) ?: return Result.Error(CommandError.VertexNotFound(to))

        return when (graph) {
            is UndirectedUnweightedGraph -> {
                val edgeId = (graph as UndirectedUnweightedGraph).addEdge(fromVertex, toVertex)
                if (edgeId != null) {
                    Result.Success("Edge added: from=$from, to=$to")
                } else {
                    Result.Error(CommandError.EdgeAdditionFailed(from, to))
                }
            }
            is UndirectedWeightedGraph -> {
                val weight =
                    command.parameters["weight"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("weight", "Int"))
                val edgeId = (graph as UndirectedWeightedGraph).addEdge(fromVertex, toVertex, weight)
                if (edgeId != null) {
                    Result.Success("Edge added: from=$from, to=$to, weight=$weight")
                } else {
                    Result.Error(CommandError.EdgeAdditionFailed(from, to, ", weight=$weight"))
                }
            }
            is DirectedUnweightedGraph -> {
                val edgeId = (graph as DirectedUnweightedGraph).addEdge(fromVertex, toVertex)
                if (edgeId != null) {
                    Result.Success("Edge added: from=$from, to=$to")
                } else {
                    Result.Error(CommandError.EdgeAdditionFailed(from, to))
                }
            }
            is DirectedWeightedGraph -> {
                val weight =
                    command.parameters["weight"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("weight", "Int"))
                val edgeId = (graph as DirectedWeightedGraph).addEdge(fromVertex, toVertex, weight)
                if (edgeId != null) {
                    Result.Success("Edge added: from=$from, to=$to, weight=$weight")
                } else {
                    Result.Error(CommandError.EdgeAdditionFailed(from, to, ", weight=$weight"))
                }
            }
            else -> {
                Result.Error(CommandError.UnknownGraphType(graph::class.simpleName ?: "UnknownGraphType"))
            }
        }
    }

    private fun removeVertex(): Result<String> {
        val id = command.parameters["id"] ?: return Result.Error(CommandError.MissingParameters("id is required"))
        val idAsV = id.toIntOrNull() as? V ?: return Result.Error(CommandError.InvalidParameterType("id", "Int"))
        graph.removeVertex(idAsV)
        return Result.Success("Vertex with id:$id removed")
    }

    private fun removeEdge(): Result<String> {
        if (command.parameters.containsKey("from") && command.parameters.containsKey("to")) {
            val from = command.parameters["from"] ?: return Result.Error(CommandError.MissingParameters("from is required"))
            val to = command.parameters["to"] ?: return Result.Error(CommandError.MissingParameters("to is required"))

            val fromAsV = from.toIntOrNull() as? V ?: return Result.Error(CommandError.InvalidParameterType("from", "Int"))
            val toAsV = to.toIntOrNull() as? V ?: return Result.Error(CommandError.InvalidParameterType("to", "Int"))

            graph.removeEdge(fromAsV, toAsV)
            if (graph is UndirectedUnweightedGraph || graph is UndirectedWeightedGraph) {
                graph.removeEdge(toAsV, fromAsV)
            }
            return Result.Success("Edge with from:$from and to:$to removed")
        } else if (command.parameters.containsKey("id")) {
            val id = command.parameters["id"] ?: return Result.Error(CommandError.MissingParameters("id is required"))
            val idAsV = id.toIntOrNull() as? E ?: return Result.Error(CommandError.InvalidParameterType("id", "Int"))
            graph.removeEdge(idAsV)
            return Result.Success("Edge with id:$id removed")
        } else {
            return Result.Error(CommandError.MissingParameters("Remove edge command must specify 'from' and 'to' or 'id'"))
        }
    }

    private fun clear(): Result<String> {
        outputMessages.clear()
        return Result.Success("Command line cleared")
    }

    private fun graphClear(): Result<String> {
        graph.clear()
        return Result.Success("Graph cleared")
    }

    private fun help(): Result.Success<String> {
        val text =
            "Base commands:\n" +
                "add vertex id:* label:* - add vertex by unique id\n" +
                "add edge from:* to:* (weight:*)? - add edge by from and to vertex id and optional weight\n" +
                "rm vertex id:* - remove vertex by id\n" +
                "rm edge id:* - remove edge by id\n" +
                "rn edge from:* to:* - remove edge by from and to vertex id\n" +
                "clear - output messages clear\n" +
                "graph_clear - graph clear\n" +
                "help - show help information"
        outputMessages.add(text)
        return Result.Success("Help information")
    }
}
