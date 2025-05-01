package model.graphs.dataClasses

data class Vertex<V : Comparable<V>>(
    val id: V,
    val label: String,
)
