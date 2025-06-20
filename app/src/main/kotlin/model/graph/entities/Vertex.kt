package model.graph.entities

data class Vertex<V : Comparable<V>>(
    val id: V,
    val label: String,
)
