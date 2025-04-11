package model.graphs.interfaces

interface Graph<E : Comparable<E>, V : Comparable<V>> {
    val vertices: Map<V, Vertex<V>>
    val edges: Map<E, Edge<E, V>>
    val countVertices: Int
        get() = vertices.size
    val countEdges: Int
        get() = edges.size

    fun addVertex(vertex: Vertex<V>): V

    fun addEdge(edge: Edge<E, V>): E?

    fun removeVertex(id: V): V?

    fun removeEdge(id: E): E?

    fun getVertex(id: V): Vertex<V>?

    fun getEdge(id: E): Edge<E, V>?

    fun getNeighbors(vertex: Vertex<V>): List<Vertex<V>>

    fun getAllEdges(): List<Edge<E, V>>

    fun getAllVertices(): List<Vertex<V>>
}
