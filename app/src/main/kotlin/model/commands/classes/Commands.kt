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
    fun execute(): String =
        when (command.type) {
            CommandTypes.ADD -> {
                when (command.entity) {
                    CommandEntities.VERTEX -> addVertex()
                    CommandEntities.EDGE -> addEdge()
                    else -> "Unsupported entity for add command"
                }
            }
            CommandTypes.CLEAR -> {
                when (command.entity) {
                    CommandEntities.GRAPH -> graphClear()
                    CommandEntities.COMMAND_OUTPUT -> clear()
                    else -> "Unsupported entity for clear command"
                }
            }

            CommandTypes.RM -> TODO()
            CommandTypes.HELP -> TODO()
        }

    private fun addVertex(): String {
        val id = command.parameters["id"] as V
        val label = command.parameters["label"] ?: return "Something didn't go according to plan"
        graph.addVertex(Vertex(id, label))
        return "Vertex added: id:$id, label:$label"
    }

    private fun addEdge(): String {
        val fromId = command.parameters["from"] as V
        val toId = command.parameters["to"] as V

        val fromVertex = graph.getVertex(fromId) ?: return "Vertex not found: id=$fromId"
        val toVertex = graph.getVertex(toId) ?: return "Vertex not found: id=$toId"

        return when (graph) {
            is UndirectedUnweightedGraph -> {
                val edgeId = (graph as UndirectedUnweightedGraph).addEdge(fromVertex, toVertex)
                if (edgeId != null) {
                    "Edge added: from=$fromId, to=$toId"
                }
                "Failed to add edge: from=$fromId, to=$toId"
            }
            is UndirectedWeightedGraph -> {
                val weight = command.parameters["weight"]?.toInt() ?: return "The weight should be Int"
                val edgeId = (graph as UndirectedWeightedGraph).addEdge(fromVertex, toVertex, weight)
                if (edgeId != null) {
                    "Edge added: from=$fromId, to=$toId, weight=$weight"
                }
                "Failed to add edge: from=$fromId, to=$toId,  weight=$weight"
            }
            is DirectedUnweightedGraph -> {
                val edgeId = (graph as DirectedUnweightedGraph).addEdge(fromVertex, toVertex)
                if (edgeId != null) {
                    "Edge added: from=$fromId, to=$toId"
                }
                "Failed to add edge: from=$fromId, to=$toId"
            }
            is DirectedWeightedGraph -> {
                val weight = command.parameters["weight"]?.toInt() ?: return "The weight should be Int"
                val edgeId = (graph as DirectedWeightedGraph).addEdge(fromVertex, toVertex, weight)
                if (edgeId != null) {
                    "Edge added: from=$fromId, to=$toId, weight=$weight"
                }
                "Failed to add edge: from=$fromId, to=$toId,  weight=$weight"
            }
            else -> {
                "Something didn't go according to plan"
            }
        }
    }

    private fun clear(): String {
        outputMessages.clear()
        return "Command line cleared"
    }

    private fun graphClear(): String {
        graph.clear()
        return "Graph cleared"
    }
}
