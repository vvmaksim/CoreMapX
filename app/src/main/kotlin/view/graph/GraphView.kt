package view.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import viewmodel.graph.GraphViewModel

@Composable
fun <E : Comparable<E>, V : Comparable<V>> GraphView(viewModel: GraphViewModel<E, V>) {
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        viewModel.vertices.forEach { v ->
            VertexView(v, Modifier)
        }
        viewModel.edges.forEach { e ->
            EdgeView(e, Modifier)
        }
    }
}
