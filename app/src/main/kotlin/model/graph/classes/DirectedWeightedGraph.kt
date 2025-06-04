package model.graph.classes

import model.graph.abstractClasses.AbstractGraph
import model.graph.dataClasses.Vertex
import model.graph.dataClasses.WeightedEdge

class DirectedWeightedGraph<V : Comparable<V>> : AbstractGraph<Long, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
        weight: Long,
    ): Long? = addEdge(WeightedEdge(nextEdgeId++, from, to, weight))

    override fun getNeighbors(vertex: Vertex<V>): List<Vertex<V>> {
        if (vertex !in _vertices.values) return emptyList()
        val neighbors = mutableSetOf<Vertex<V>>()
        _edges.values.forEach { if (it.from == vertex) neighbors.add(it.to) }
        return neighbors.toList()
    }
}
