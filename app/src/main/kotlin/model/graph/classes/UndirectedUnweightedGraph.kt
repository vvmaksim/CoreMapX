package model.graph.classes

import model.graph.abstractClass.AbstractGraph
import model.graph.dataClasses.UnweightedEdge
import model.graph.dataClasses.Vertex

class UndirectedUnweightedGraph<V : Comparable<V>> : AbstractGraph<Long, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
    ): Long? = addEdge(UnweightedEdge(nextEdgeId++, from, to))
}
