package model.dto

import model.graph.contracts.Graph

data class NewGraphData<E : Comparable<E>, V : Comparable<V>>(
    val graph: Graph<E, V>,
    val graphName: String,
)
