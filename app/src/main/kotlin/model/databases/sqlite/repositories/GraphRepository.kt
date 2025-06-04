package model.databases.sqlite.repositories

import extensions.toLong
import org.coremapx.graph.GraphDatabase
import orgcoremapxapp.Graphs
import orgcoremapxapp.GraphsQueries

class GraphRepository(database: GraphDatabase) {
    private val queries: GraphsQueries = database.graphsQueries

    fun insertGraph(name: String?, author: String?, isDirected: Boolean, isWeighted: Boolean): Long {
        queries.insertGraph(name, author, isDirected.toLong(), isWeighted.toLong())
        return queries.selectAllGraphs().executeAsList().last().graph_id
    }

    fun getAllGraphs(): List<Graphs> = queries.selectAllGraphs().executeAsList()

    fun getGraphById(graphId: Long): Graphs? = queries.selectGraphById(graphId).executeAsOneOrNull()

    fun updateGraphById(graphId: Long, name: String?, author: String?, isDirected: Boolean, isWeighted: Boolean) {
        queries.updateGraphById(name, author, isDirected.toLong(), isWeighted.toLong(), graphId)
    }
}
