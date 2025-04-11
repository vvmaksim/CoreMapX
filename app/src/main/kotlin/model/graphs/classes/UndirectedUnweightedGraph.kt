package model.graphs.classes

import model.graphs.abstractClasses.AbstractGraph
import model.graphs.dataClasses.UnweightedEdge
import model.graphs.interfaces.Vertex

class UndirectedUnweightedGraph<V: Comparable<V>>: AbstractGraph<Int, V>() {
    fun addEdge(from: Vertex<V>, to: Vertex<V>): Int? = addEdge(UnweightedEdge(nextEdgeId++, from, to))
}
