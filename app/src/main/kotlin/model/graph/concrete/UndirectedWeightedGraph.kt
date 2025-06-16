package model.graph.concrete

import model.graph.base.AbstractGraph
import model.graph.entities.Vertex
import model.graph.entities.WeightedEdge

class UndirectedWeightedGraph<V : Comparable<V>> : AbstractGraph<Long, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
        weight: Long,
    ): Long? = addEdge(WeightedEdge(nextEdgeId++, from, to, weight))
}
