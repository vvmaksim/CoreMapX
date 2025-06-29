package model.graph.concrete

import model.graph.base.AbstractGraph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex

class DirectedUnweightedGraph<V : Comparable<V>> : AbstractGraph<Long, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
    ): Long? = addEdge(UnweightedEdge(nextEdgeId++, from, to))

    override fun getNeighbors(vertex: Vertex<V>): List<Vertex<V>> {
        if (vertex !in _vertices.values) return emptyList()
        val neighbors = mutableSetOf<Vertex<V>>()
        _edges.values.forEach { if (it.from == vertex) neighbors.add(it.to) }
        return neighbors.toList()
    }
}
