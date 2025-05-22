package view.interfaceElements

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.commands.classes.Command
import model.commands.classes.Commands
import model.result.CommandErrors
import model.result.Result
import mu.KotlinLogging
import org.coremapx.app.config
import view.graph.GraphView
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel

private val logger = KotlinLogging.logger {}

@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainWorkArea(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier,
) {
    val outputMessages = remember { mutableStateOf(mutableListOf<String>()) }
    val scrollState = rememberScrollState()
    val graph = viewModel.graph.value
    var commandCount by remember { mutableStateOf(0) }

    val maxCountMessages = config.getIntValue("maxCountMessages") ?: 0
    val commandFieldWidth = (config.getIntValue("commandFieldWidth") ?: 0).dp
    val canvasBackgroundColor = config.getColor("canvasBackgroundColor")

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
        outputMessages.value = outputMessages.value.takeLast(maxCountMessages).toMutableList()
    }

    fun errorMessage(errorResult: Result.Error): String = "Error:${errorResult.error.type}.${errorResult.error.description}"

    fun handleCommand(command: String) {
        if (graph == null) {
            updateOutputMessages("Error: ${CommandErrors.NoGraphSelected().type}.${CommandErrors.NoGraphSelected().description}")
            return
        }
        val commandResult = Command.create(command)
        when (commandResult) {
            is Result.Success -> {
                val executeResult = Commands(commandResult.data, graph, outputMessages.value).execute()
                when (executeResult) {
                    is Result.Success -> {
                        updateOutputMessages(executeResult.data)
                        logger.info(executeResult.data)
                        commandCount++
                    }
                    is Result.Error -> {
                        updateOutputMessages(errorMessage(executeResult))
                        logger.warn(errorMessage(executeResult))
                    }
                }
            }
            is Result.Error -> {
                updateOutputMessages(errorMessage(commandResult))
                logger.warn(errorMessage(commandResult))
            }
        }
    }

    Box(modifier = modifier.fillMaxSize().background(canvasBackgroundColor)) {
        graphViewModel?.let { graphViewModel ->
            GraphView(
                viewModel = graphViewModel,
                offsetX = viewModel.offsetX.value,
                offsetY = viewModel.offsetY.value,
                scale = viewModel.scale.value,
                onPan = { dx, dy -> viewModel.moveCanvas(dx, dy) },
                onZoom = { delta -> viewModel.zoomCanvas(delta) },
            )
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
                        .width(commandFieldWidth)
                        .align(Alignment.CenterVertically),
                outputMessages = outputMessages.value,
                onCommand = { command -> handleCommand(command) },
            )

            Spacer(Modifier.weight(1f))
        }
    }
}
