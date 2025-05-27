package view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.graph.classes.DirectedUnweightedGraph
import model.graph.classes.DirectedWeightedGraph
import model.graph.classes.UndirectedUnweightedGraph
import model.graph.classes.UndirectedWeightedGraph
import model.graph.interfaces.Graph
import org.coremapx.app.config
import view.interfaceElements.MainMenu
import view.interfaceElements.WorkSpace
import view.interfaceElements.dialogs.NewGraph
import viewmodel.MainScreenViewModel

@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainScreen(viewModel: MainScreenViewModel<E, V>) {
    var isMenuVisible by remember { mutableStateOf(true) }
    var showNewGraphDialog by remember { mutableStateOf(false) }

    val mainMenuWidth = (config.getIntValue("mainMenuWidth") ?: 0).dp

    Box(modifier = Modifier.fillMaxSize()) {
        WorkSpace(
            viewModel,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(start = mainMenuWidth),
        )

        MainMenu(
            isMenuVisible = isMenuVisible,
            onMenuVisibilityChange = { isMenuVisible = it },
            onNewGraphClick = { showNewGraphDialog = true },
            viewModel = viewModel,
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(mainMenuWidth)
                    .align(Alignment.CenterStart),
        )
    }

    if (showNewGraphDialog) {
        NewGraph(
            onDismiss = { showNewGraphDialog = false },
            onCreate = { isDirected, isWeighted, graphName ->
                val newGraph: Graph<E, V> =
                    when {
                        isDirected && isWeighted -> DirectedWeightedGraph<V>()
                        isDirected && !isWeighted -> DirectedUnweightedGraph<V>()
                        !isDirected && isWeighted -> UndirectedWeightedGraph<V>()
                        else -> UndirectedUnweightedGraph<V>()
                    } as Graph<E, V>
                viewModel.graphName = graphName
                viewModel.graphAuthor = "None"
                viewModel.graphPath = null
                viewModel.graphFormat = null
                viewModel.updateGraph(newGraph)
                showNewGraphDialog = false
            },
        )
    }
}
