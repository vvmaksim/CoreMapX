package view.interfaceElements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.MainScreenViewModel

@Composable
fun <E: Comparable<E>, V: Comparable<V>> VertexAndEdgeCounters(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "${viewModel.graphViewModel?.edges?.size ?: 0} edges",
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "${viewModel.graphViewModel?.vertices?.size ?: 0} vertices",
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
