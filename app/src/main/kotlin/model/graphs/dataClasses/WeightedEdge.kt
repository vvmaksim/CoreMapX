package model.graphs.dataClasses

import model.graphs.interfaces.Edge
import model.graphs.interfaces.Vertex

data class WeightedEdge<E : Comparable<E>, V : Comparable<V>>(
    override val id: E,
    override val from: Vertex<V>,
    override val to: Vertex<V>,
    val weight: Int,
) : Edge<E, V>
