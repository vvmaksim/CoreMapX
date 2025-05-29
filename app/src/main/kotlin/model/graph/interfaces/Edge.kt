package model.graph.interfaces

import model.graph.dataClasses.Vertex

interface Edge<E : Comparable<E>, V : Comparable<V>> {
    val id: E
    val from: Vertex<V>
    val to: Vertex<V>
}
