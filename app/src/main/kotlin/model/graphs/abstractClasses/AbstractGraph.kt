package model.graphs.abstractClasses

import model.graphs.interfaces.Edge
import model.graphs.interfaces.Graph
import model.graphs.interfaces.Vertex

abstract class AbstractGraph<E : Comparable<E>, V : Comparable<V>> : Graph<E, V> {
    protected var nextEdgeId = 0
    protected val _vertices = HashMap<V, Vertex<V>>()
    protected val _edges = HashMap<E, Edge<E, V>>()

    override val vertices: Map<V, Vertex<V>>
        get() = _vertices.toMap()

    override val edges: Map<E, Edge<E, V>>
        get() = _edges.toMap()

    override val countVertices: Int
        get() = _vertices.size

    override val countEdges: Int
        get() = _edges.size

    override fun getVertex(id: V): Vertex<V>? = _vertices[id]

    override fun getEdge(id: E): Edge<E, V>? = _edges[id]

    override fun getAllVertices(): List<Vertex<V>> = _vertices.values.toList()

    override fun getAllEdges(): List<Edge<E, V>> = _edges.values.toList()

    override fun getNeighbors(vertex: Vertex<V>): List<Vertex<V>> {
        if (vertex !in _vertices.values) return emptyList()
        val neighbors = mutableSetOf<Vertex<V>>()
        _edges.values.forEach {
            if (it.from == vertex) neighbors.add(it.to)
            if (it.to == vertex) neighbors.add(it.from)
        }
        return neighbors.toList()
    }

    override fun addVertex(vertex: Vertex<V>): V {
        _vertices[vertex.id] = vertex
        return vertex.id
    }

    override fun addEdge(edge: Edge<E, V>): E? {
        if (edge.from !in _vertices.values || edge.to !in _vertices.values) return null
        if (edge.id in _edges) return null
        _edges[edge.id] = edge
        return edge.id
    }

    override fun removeEdge(id: E): E? = if (_edges.remove(id) == null) null else id

    override fun removeVertex(id: V): V? {
        _vertices.remove(id) ?: return null
        _edges.entries.removeIf { it.value.from.id == id || it.value.to.id == id }
        return id
    }
}
