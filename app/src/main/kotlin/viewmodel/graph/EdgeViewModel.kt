package viewmodel.graph

import model.graphs.classes.DirectedUnweightedGraph
import model.graphs.classes.DirectedWeightedGraph
import model.graphs.dataClasses.WeightedEdge

class EdgeViewModel<E : Comparable<E>, V : Comparable<V>>(
    val from: VertexViewModel<V>,
    val to: VertexViewModel<V>,
    graph: Any,
    edge: Any,
) {
    val isDirected: Boolean = graph is DirectedUnweightedGraph<*> || graph is DirectedWeightedGraph<*>
    val weight: Int? = if (edge is WeightedEdge<*, *>) edge.weight else null
}
