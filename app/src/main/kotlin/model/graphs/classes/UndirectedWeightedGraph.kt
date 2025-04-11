package model.graphs.classes

import model.graphs.abstractClasses.AbstractGraph
import model.graphs.dataClasses.WeightedEdge
import model.graphs.interfaces.Vertex

class UndirectedWeightedGraph<V : Comparable<V>> : AbstractGraph<Int, V>() {
    fun addEdge(
        from: Vertex<V>,
        to: Vertex<V>,
        weight: Int,
    ): Int? = addEdge(WeightedEdge(nextEdgeId++, from, to, weight))

    fun getWeight(weightedEdge: WeightedEdge<Int, V>) = weightedEdge.weight
}
