package model.graphs.classes

import model.graphs.abstractClasses.AbstractGraph
import model.graphs.dataClasses.UnweightedEdge
import model.graphs.interfaces.Vertex

class DirectedUnweightedGraph<V : Comparable<V>> : AbstractGraph<Int, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
    ): Int? = addEdge(UnweightedEdge(nextEdgeId++, from, to))

    override fun getNeighbors(vertex: Vertex<V>): List<Vertex<V>> {
        if (vertex !in _vertices.values) return emptyList()
        val neighbors = mutableSetOf<Vertex<V>>()
        _edges.values.forEach { if (it.from == vertex) neighbors.add(it.to) }
        return neighbors.toList()
    }
}
