package model.databases.sqlite.repository

import org.coremapx.graph.GraphDatabase
import orgcoremapxapp.Vertices
import orgcoremapxapp.VerticesQueries

class VertexRepository(
    database: GraphDatabase,
) {
    private val queries: VerticesQueries = database.verticesQueries

    fun insertVertex(
        graphId: Long,
        vertexId: Long,
        label: String,
    ) {
        queries.insertVertex(graphId, vertexId, label)
    }

    fun getVerticesByGraph(graphId: Long): List<Vertices> = queries.selectAllVerticesByGraph(graphId).executeAsList()

    fun updateVertexLabelByGraphAndId(
        graphId: Long,
        vertexId: Long,
        newLabel: String,
    ) {
        queries.updateVertexLabelByGraphAndId(label = newLabel, graph_id = graphId, vertex_id = vertexId)
    }

    fun deleteVertexByGraphAndId(
        graphId: Long,
        vertexId: Long,
    ) {
        queries.deleteVertexByGraphAndId(graphId, vertexId)
    }
}
