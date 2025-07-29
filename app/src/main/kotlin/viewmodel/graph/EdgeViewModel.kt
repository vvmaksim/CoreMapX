package viewmodel.graph

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graph.entities.WeightedEdge
import org.coremapx.app.config

class EdgeViewModel<E : Comparable<E>, V : Comparable<V>>(
    val edgeId: E,
    val from: VertexViewModel<V>,
    val to: VertexViewModel<V>,
    val isDirected: Boolean,
    private val _weightVisible: State<Boolean>,
    private val _idVisible: State<Boolean>,
    color: Color = config.states.edgeMainColor.value,
    edge: Any,
) {
    val weight: Long? = if (edge is WeightedEdge<*, *>) edge.weight else null

    private var _color = mutableStateOf(color)
    var color: Color
        get() = _color.value
        set(value) {
            _color.value = value
        }

    val weightVisible
        get() = _weightVisible.value

    val idVisible
        get() = _idVisible.value

    fun getEdgeText(): String {
        val weight = weight?.toString() ?: ""
        return if (weightVisible || idVisible) {
            if (weightVisible && idVisible) {
                "id: $edgeId\nweight: $weight"
            } else if (weightVisible) {
                weight
            } else {
                "id: $edgeId"
            }
        } else {
            ""
        }
    }
}
