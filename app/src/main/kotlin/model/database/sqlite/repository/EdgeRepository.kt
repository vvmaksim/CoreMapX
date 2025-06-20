package model.database.sqlite.repository

import org.coremapx.graph.GraphDatabase
import orgcoremapxapp.Edges
import orgcoremapxapp.EdgesQueries
import orgcoremapxapp.VerticesQueries

class EdgeRepository(
    database: GraphDatabase,
) {
    private val edgeQueries: EdgesQueries = database.edgesQueries
    private val vertexQueries: VerticesQueries = database.verticesQueries

    fun insertEdge(
        graphId: Long,
        fromVertex: Long,
        toVertex: Long,
        weight: Long?,
    ) {
        val fromVertexExists = vertexQueries.selectVertexByGraphAndId(graphId, fromVertex).executeAsOneOrNull() != null
        val toVertexExists = vertexQueries.selectVertexByGraphAndId(graphId, toVertex).executeAsOneOrNull() != null
        if (fromVertexExists && toVertexExists) {
            edgeQueries.insertEdge(graphId, fromVertex, toVertex, weight)
        } else {
            throw IllegalArgumentException("Vertex $fromVertex or $toVertex does not exist in graph $graphId")
        }
    }

    fun getEdgesByGraph(graphId: Long): List<Edges> = edgeQueries.selectAllEdgesByGraph(graphId).executeAsList()

    fun updateEdgeByGraphAndVertices(
        graphId: Long,
        newWeight: Long?,
        fromVertex: Long,
        toVertex: Long,
    ) {
        edgeQueries.updateEdgeByGraphAndVertices(weight = newWeight, graph_id = graphId, from_vertex = fromVertex, to_vertex = toVertex)
    }

    fun deleteEdgeByGraphAndVertices(
        graphId: Long,
        fromVertex: Long,
        toVertex: Long,
    ) {
        edgeQueries.deleteEdgeByGraphAndVertices(graphId, fromVertex, toVertex)
    }
}
