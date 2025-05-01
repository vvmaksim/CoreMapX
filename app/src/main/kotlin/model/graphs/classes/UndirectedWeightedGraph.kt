package model.graphs.classes

import model.graphs.abstractClasses.AbstractGraph
import model.graphs.dataClasses.Vertex
import model.graphs.dataClasses.WeightedEdge

class UndirectedWeightedGraph<V : Comparable<V>> : AbstractGraph<Int, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
        weight: Int,
    ): Int? = addEdge(WeightedEdge(nextEdgeId++, from, to, weight))
}
