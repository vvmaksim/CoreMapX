package model.graph.entities

import model.graph.interfaces.Edge

data class UnweightedEdge<E : Comparable<E>, V : Comparable<V>>(
    override val id: E,
    override val from: Vertex<V>,
    override val to: Vertex<V>,
) : Edge<E, V>
