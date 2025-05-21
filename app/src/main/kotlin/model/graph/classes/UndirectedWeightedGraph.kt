package model.graph.classes

import model.graph.abstractClasses.AbstractGraph
import model.graph.dataClasses.Vertex
import model.graph.dataClasses.WeightedEdge

class UndirectedWeightedGraph<V : Comparable<V>> : AbstractGraph<Int, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
        weight: Int,
    ): Int? = addEdge(WeightedEdge(nextEdgeId++, from, to, weight))
}
