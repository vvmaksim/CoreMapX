package view.interfaceElements

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.commands.classes.Command
import model.commands.classes.Commands
import model.graphs.classes.DirectedWeightedGraph
import model.graphs.classes.UndirectedWeightedGraph
import view.graph.GraphView
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel

@Composable
fun <E: Comparable<E>, V: Comparable<V>> MainWorkArea(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier
) {
    val outputMessages = remember { mutableStateOf(mutableListOf<String>()) }
    val scrollState = rememberScrollState()
    val graph = viewModel.graph
    var commandCount by remember { mutableStateOf(0) }

    val graphViewModel by remember(graph, commandCount) {
        derivedStateOf {
            viewModel.graphViewModel = graph?.let { GraphViewModel(it, viewModel.showVerticesLabels) }
            viewModel.graphViewModel?.let { _ ->
                viewModel.resetGraphView()
            }
            viewModel.graphViewModel
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        graphViewModel?.let { graphViewModel ->
            GraphView(graphViewModel)
        }

        TopMenu(
            viewModel,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VertexAndEdgeCounters(
                viewModel,
                modifier = Modifier
                    .align(Alignment.Bottom)
            )

            Spacer(Modifier.weight(1f))

            CommandLine(
                modifier = Modifier
                    .width(666.dp)
                    .align(Alignment.CenterVertically),
                outputMessages = outputMessages.value,
                onCommand = { command ->
                    try {
                        if (graph == null) {
                            outputMessages.value.add("Error: No graph selected. Create a new graph first")
                            outputMessages.value = outputMessages.value.takeLast(50).toMutableList()
                            return@CommandLine
                        }
                        val result = Commands<E, V>(Command(command), graph, outputMessages.value).execute()
                        outputMessages.value.add(result)
                        outputMessages.value = outputMessages.value.takeLast(50).toMutableList()
                        commandCount++
                    } catch (ex: IllegalArgumentException) {
                        outputMessages.value.add("Error: ${ex.message}")
                        outputMessages.value = outputMessages.value.takeLast(50).toMutableList()
                    }
                }
            )

            Spacer(Modifier.weight(1f))
        }
    }
}
