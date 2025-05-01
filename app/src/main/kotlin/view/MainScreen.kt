package view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import model.graphs.classes.DirectedUnweightedGraph
import model.graphs.classes.DirectedWeightedGraph
import model.graphs.classes.UndirectedUnweightedGraph
import model.graphs.classes.UndirectedWeightedGraph
import model.graphs.interfaces.Graph
import view.interfaceElements.MainMenu
import view.interfaceElements.MainWorkArea
import view.interfaceElements.dialogs.NewGraph
import viewmodel.MainScreenViewModel

@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainScreen(viewModel: MainScreenViewModel<E, V>) {
    var isMenuVisible by remember { mutableStateOf(true) }
    var showNewGraphDialog by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize()) {
        MainMenu(
            isMenuVisible = isMenuVisible,
            onMenuVisibilityChange = { isMenuVisible = it },
            onNewGraphClick = { showNewGraphDialog = true },
        )
        if (viewModel.isGraphActive) {
            MainWorkArea(
                viewModel,
                modifier = Modifier.weight(1f),
            )
        }
    }

    if (showNewGraphDialog) {
        NewGraph(
            onDismiss = { showNewGraphDialog = false },
            onCreate = { isDirected, isWeighted ->
                val newGraph: Graph<E, V> =
                    when {
                        isDirected && isWeighted -> DirectedWeightedGraph<V>()
                        isDirected && !isWeighted -> DirectedUnweightedGraph<V>()
                        !isDirected && isWeighted -> UndirectedWeightedGraph<V>()
                        else -> UndirectedUnweightedGraph<V>()
                    } as Graph<E, V>
                viewModel.updateGraph(newGraph)
                showNewGraphDialog = false
            },
        )
    }
}
