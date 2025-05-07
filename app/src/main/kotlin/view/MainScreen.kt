package view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.graphs.classes.DirectedUnweightedGraph
import model.graphs.classes.DirectedWeightedGraph
import model.graphs.classes.UndirectedUnweightedGraph
import model.graphs.classes.UndirectedWeightedGraph
import model.graphs.interfaces.Graph
import view.interfaceElements.MainMenu
import view.interfaceElements.WorkSpace
import view.interfaceElements.dialogs.NewGraph
import viewmodel.MainScreenViewModel

@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainScreen(viewModel: MainScreenViewModel<E, V>) {
    var isMenuVisible by remember { mutableStateOf(true) }
    var showNewGraphDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        WorkSpace(
            viewModel,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(start = 200.dp),
        )

        MainMenu(
            isMenuVisible = isMenuVisible,
            onMenuVisibilityChange = { isMenuVisible = it },
            onNewGraphClick = { showNewGraphDialog = true },
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .align(Alignment.CenterStart),
        )
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
