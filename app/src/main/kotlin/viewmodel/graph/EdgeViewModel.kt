package viewmodel.graph

import model.graph.classes.DirectedUnweightedGraph
import model.graph.classes.DirectedWeightedGraph
import model.graph.dataClasses.WeightedEdge

class EdgeViewModel<E : Comparable<E>, V : Comparable<V>>(
    val from: VertexViewModel<V>,
    val to: VertexViewModel<V>,
    graph: Any,
    edge: Any,
) {
    val isDirected: Boolean = graph is DirectedUnweightedGraph<*> || graph is DirectedWeightedGraph<*>
    val weight: Int? = if (edge is WeightedEdge<*, *>) edge.weight else null
}
