package viewmodel.graph

import androidx.compose.runtime.State
import androidx.compose.ui.unit.dp
import model.dto.VisibleStates
import model.graph.contracts.Graph

class GraphViewModel<E : Comparable<E>, V : Comparable<V>>(
    private val graph: Graph<E, V>,
    private val visibleStates: State<VisibleStates>,
) {
    private val _vertices: Map<V, VertexViewModel<V>> =
        graph.vertices.mapValues { (_, vertex) ->
            VertexViewModel(
                x = 0.dp,
                y = 0.dp,
                vertex = vertex,
                _labelVisible = visibleStates.value.verticesLabels,
            )
        }

    private val _edges: Map<E, EdgeViewModel<E, V>> =
        graph.edges.mapValues { (edgeId, edge) ->
            val from =
                _vertices[edge.from.id]
                    ?: throw IllegalStateException("VertexView for ${edge.from.id} not found")
            val to =
                _vertices[edge.to.id]
                    ?: throw IllegalStateException("VertexView for ${edge.to.id} not found")
            EdgeViewModel(
                edgeId = edgeId,
                from = from,
                to = to,
                graph = graph,
                edge = edge,
            )
        }

    val vertices: Collection<VertexViewModel<V>>
        get() = _vertices.values

    val edges: Collection<EdgeViewModel<E, V>>
        get() = _edges.values
}
