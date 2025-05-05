package view.interfaceElements

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.commands.classes.Command
import model.commands.classes.Commands
import view.graph.GraphView
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel

@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainWorkArea(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier,
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

    fun updateOutputMessages(newMessage: String) {
        outputMessages.value.add(newMessage)
        outputMessages.value = outputMessages.value.takeLast(50).toMutableList()
    }

    Box(modifier = modifier.fillMaxSize()) {
        graphViewModel?.let { graphViewModel ->
            GraphView(graphViewModel)
        }

        TopMenu(
            viewModel,
            modifier = Modifier.align(Alignment.TopCenter),
        )

        Row(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            VertexAndEdgeCounters(
                viewModel,
                modifier =
                    Modifier
                        .align(Alignment.Bottom),
            )

            Spacer(Modifier.weight(1f))

            CommandLine(
                modifier =
                    Modifier
                        .width(666.dp)
                        .align(Alignment.CenterVertically),
                outputMessages = outputMessages.value,
                onCommand = { command ->
                    try {
                        if (graph == null) {
                            updateOutputMessages("Error: No graph selected. Create a new graph first")
                            return@CommandLine
                        }
                        val result = Commands<E, V>(Command(command), graph, outputMessages.value).execute()
                        updateOutputMessages(result)
                        commandCount++
                    } catch (ex: IllegalArgumentException) {
                        updateOutputMessages("Error: ${ex.message}")
                    }
                },
            )

            Spacer(Modifier.weight(1f))
        }
    }
}
