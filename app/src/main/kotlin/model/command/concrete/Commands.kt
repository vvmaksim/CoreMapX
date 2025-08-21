package model.command.concrete

import model.command.enums.CommandEntities
import model.command.enums.CommandTypes
import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.contracts.Graph
import model.graph.entities.Vertex
import model.result.CommandErrors
import model.result.Result
import viewmodel.MainScreenViewModel

class Commands<E : Comparable<E>, V : Comparable<V>>(
    private val command: Command,
    private val graph: Graph<E, V>,
    private val outputMessages: MutableList<String>,
    private val viewModel: MainScreenViewModel<E, V>? = null,
) {
    fun execute(): Result<String> =
        when (command.type) {
            CommandTypes.ADD -> {
                when (command.entity) {
                    CommandEntities.VERTEX -> addVertex()
                    CommandEntities.EDGE -> addEdge()
                    else -> Result.Error(CommandErrors.UnknownEntity("Unsupported entity for add command"))
                }
            }
            CommandTypes.RM -> {
                when (command.entity) {
                    CommandEntities.VERTEX -> removeVertex()
                    CommandEntities.EDGE -> removeEdge()
                    else -> Result.Error(CommandErrors.UnknownEntity("Unsupported entity for rm/remove command"))
                }
            }
            CommandTypes.SET -> {
                when (command.entity) {
                    CommandEntities.LAYOUT_STRATEGY -> setStrategy()
                    else -> Result.Error(CommandErrors.UnknownEntity("Unsupported entity for set command"))
                }
            }
            CommandTypes.CLEAR -> {
                when (command.entity) {
                    CommandEntities.GRAPH -> graphClear()
                    CommandEntities.COMMAND_OUTPUT -> clear()
                    else -> Result.Error(CommandErrors.UnknownEntity("Unsupported entity for clear command"))
                }
            }
            CommandTypes.HELP -> help()
        }

    private fun addVertex(): Result<String> {
        val id = command.parameters["id"] ?: return Result.Error(CommandErrors.MissingParameters("id is required"))
        val label = command.parameters["label"] ?: return Result.Error(CommandErrors.MissingParameters("label is required"))
        val idAsV = id.toLongOrNull() as? V ?: return Result.Error(CommandErrors.InvalidParameterType("id", "Long"))
        graph.addVertex(Vertex(idAsV, label))
        return Result.Success("Vertex added: id:$idAsV, label:$label")
    }

    private fun addEdge(): Result<String> {
        val from = command.parameters["from"] ?: return Result.Error(CommandErrors.MissingParameters("from is required"))
        val to = command.parameters["to"] ?: return Result.Error(CommandErrors.MissingParameters("to is required"))

        val fromAsV = from.toLongOrNull() as? V ?: return Result.Error(CommandErrors.InvalidParameterType("from", "Long"))
        val toAsV = to.toLongOrNull() as? V ?: return Result.Error(CommandErrors.InvalidParameterType("to", "Long"))

        val fromVertex = graph.getVertex(fromAsV) ?: return Result.Error(CommandErrors.VertexNotFound(from))
        val toVertex = graph.getVertex(toAsV) ?: return Result.Error(CommandErrors.VertexNotFound(to))

        return when (graph) {
            is UndirectedUnweightedGraph -> {
                val edgeId = (graph as UndirectedUnweightedGraph).addEdge(fromVertex, toVertex)
                if (edgeId != null) {
                    Result.Success("Edge added: from=$from, to=$to")
                } else {
                    Result.Error(CommandErrors.EdgeAdditionFailed(from, to))
                }
            }
            is UndirectedWeightedGraph -> {
                val weight =
                    command.parameters["weight"]?.toLongOrNull()
                        ?: return Result.Error(CommandErrors.InvalidParameterType("weight", "Long"))
                val edgeId = (graph as UndirectedWeightedGraph).addEdge(fromVertex, toVertex, weight)
                if (edgeId != null) {
                    Result.Success("Edge added: from=$from, to=$to, weight=$weight")
                } else {
                    Result.Error(CommandErrors.EdgeAdditionFailed(from, to, ", weight=$weight"))
                }
            }
            is DirectedUnweightedGraph -> {
                val edgeId = (graph as DirectedUnweightedGraph).addEdge(fromVertex, toVertex)
                if (edgeId != null) {
                    Result.Success("Edge added: from=$from, to=$to")
                } else {
                    Result.Error(CommandErrors.EdgeAdditionFailed(from, to))
                }
            }
            is DirectedWeightedGraph -> {
                val weight =
                    command.parameters["weight"]?.toLongOrNull()
                        ?: return Result.Error(CommandErrors.InvalidParameterType("weight", "Long"))
                val edgeId = (graph as DirectedWeightedGraph).addEdge(fromVertex, toVertex, weight)
                if (edgeId != null) {
                    Result.Success("Edge added: from=$from, to=$to, weight=$weight")
                } else {
                    Result.Error(CommandErrors.EdgeAdditionFailed(from, to, ", weight=$weight"))
                }
            }
            else -> {
                Result.Error(CommandErrors.UnknownGraphType(graph::class.simpleName ?: "UnknownGraphType"))
            }
        }
    }

    private fun setStrategy(): Result<String> {
        val strategyAsString =
            command.parameters["strategy"] ?: return Result.Error(CommandErrors.MissingParameters("Strategy is required"))
        if (viewModel != null) {
            val strategy =
                viewModel.graphManager.getLayoutStrategyByString(strategyAsString)
                    ?: return Result.Error(CommandErrors.UnknownLayoutStrategy(strategyAsString))
            viewModel.graphManager.updateLayoutStrategy(strategy)
            return Result.Success("Layout strategy updated. New strategy: $strategyAsString")
        } else {
            return Result.Error(CommandErrors.ViewmodelNotFounded())
        }
    }

    private fun removeVertex(): Result<String> {
        val id = command.parameters["id"] ?: return Result.Error(CommandErrors.MissingParameters("id is required"))
        val idAsV = id.toLongOrNull() as? V ?: return Result.Error(CommandErrors.InvalidParameterType("id", "Long"))
        graph.removeVertex(idAsV)
        return Result.Success("Vertex with id:$id removed")
    }

    private fun removeEdge(): Result<String> {
        if (command.parameters.containsKey("from") && command.parameters.containsKey("to")) {
            val from = command.parameters["from"] ?: return Result.Error(CommandErrors.MissingParameters("from is required"))
            val to = command.parameters["to"] ?: return Result.Error(CommandErrors.MissingParameters("to is required"))

            val fromAsV = from.toLongOrNull() as? V ?: return Result.Error(CommandErrors.InvalidParameterType("from", "Long"))
            val toAsV = to.toLongOrNull() as? V ?: return Result.Error(CommandErrors.InvalidParameterType("to", "Long"))

            graph.removeEdge(fromAsV, toAsV)
            // !graph.isDirected
//            if (graph is UndirectedUnweightedGraph || graph is UndirectedWeightedGraph) {
            if (!graph.isDirected) {
                graph.removeEdge(toAsV, fromAsV)
            }
            return Result.Success("Edge with from:$from and to:$to removed")
        } else if (command.parameters.containsKey("id")) {
            val id = command.parameters["id"] ?: return Result.Error(CommandErrors.MissingParameters("id is required"))
            val idAsV = id.toLongOrNull() as? E ?: return Result.Error(CommandErrors.InvalidParameterType("id", "Long"))
            graph.removeEdge(idAsV)
            return Result.Success("Edge with id:$id removed")
        } else {
            return Result.Error(CommandErrors.MissingParameters("Remove edge command must specify 'from' and 'to' or 'id'"))
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
