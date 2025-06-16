package model.graph.classes

import model.graph.abstractClass.AbstractGraph
import model.graph.dataClasses.Vertex
import model.graph.dataClasses.WeightedEdge

class UndirectedWeightedGraph<V : Comparable<V>> : AbstractGraph<Long, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
        weight: Long,
    ): Long? = addEdge(WeightedEdge(nextEdgeId++, from, to, weight))
}
