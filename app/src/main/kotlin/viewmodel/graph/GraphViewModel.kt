package viewmodel.graph

import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.graphs.interfaces.Graph

class GraphViewModel<E : Comparable<E>, V : Comparable<V>>(
    private val graph: Graph<E, V>,
    private val showVerticesLabels: State<Boolean>,
) {
    private val _vertices: Map<V, VertexViewModel<V>> =
        graph.vertices.mapValues { (_, vertex) ->
            VertexViewModel(0.dp, 0.dp, Color.Gray, vertex, showVerticesLabels)
        }

    private val _edges: Map<E, EdgeViewModel<E, V>> =
        graph.edges.mapValues { (_, edge) ->
            val from =
                _vertices[edge.from.id]
                    ?: throw IllegalStateException("VertexView for ${edge.from.id} not found")
            val to =
                _vertices[edge.to.id]
                    ?: throw IllegalStateException("VertexView for ${edge.to.id} not found")
            EdgeViewModel(from, to, graph, edge)
        }

    val vertices: Collection<VertexViewModel<V>>
        get() = _vertices.values

    val edges: Collection<EdgeViewModel<E, V>>
        get() = _edges.values
}
