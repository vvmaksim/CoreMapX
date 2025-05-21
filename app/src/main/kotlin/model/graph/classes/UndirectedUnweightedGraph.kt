package model.graph.classes

import model.graph.abstractClasses.AbstractGraph
import model.graph.dataClasses.UnweightedEdge
import model.graph.dataClasses.Vertex

class UndirectedUnweightedGraph<V : Comparable<V>> : AbstractGraph<Int, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
    ): Int? = addEdge(UnweightedEdge(nextEdgeId++, from, to))
}
