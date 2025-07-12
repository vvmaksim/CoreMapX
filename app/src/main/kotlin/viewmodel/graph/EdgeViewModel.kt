package viewmodel.graph

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.entities.WeightedEdge
import org.coremapx.app.config

class EdgeViewModel<E : Comparable<E>, V : Comparable<V>>(
    val edgeId: E,
    val from: VertexViewModel<V>,
    val to: VertexViewModel<V>,
    color: Color = config.states.edgeMainColor.value,
    graph: Any,
    edge: Any,
) {
    val isDirected: Boolean = graph is DirectedUnweightedGraph<*> || graph is DirectedWeightedGraph<*>
    val weight: Long? = if (edge is WeightedEdge<*, *>) edge.weight else null

    private var _color = mutableStateOf(color)
    var color: Color
        get() = _color.value
        set(value) {
            _color.value = value
        }
}
