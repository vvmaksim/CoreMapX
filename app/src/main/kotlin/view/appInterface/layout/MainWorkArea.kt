package view.appInterface.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import extensions.canvasBackground
import extensions.commandLineBackground
import model.commands.classes.Command
import model.commands.classes.Commands
import model.result.CommandErrors
import model.result.Result
import mu.KotlinLogging
import org.coremapx.app.config
import view.appInterface.CommandLine
import view.appInterface.GraphElementCounters
import view.appInterface.TopMenu
import view.appInterface.buttons.ZoomButtons
import view.graph.GraphView
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel

private val logger = KotlinLogging.logger {}

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainWorkArea(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier.Companion,
) {
    val outputMessages = remember { mutableStateOf(mutableListOf<String>()) }
    val scrollState = rememberScrollState()
    val graph = viewModel.graph.value
    var commandCount by remember { mutableStateOf(0) }

    val maxCountMessages = config.states.maxCountMessages.value
    val commandFieldWidth = config.states.commandFieldWidth.value.dp
    val isTransparentCommandLine = config.states.isTransparentCommandLine.value

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

    fun handleCommand(commandLine: String) {
        if (graph == null) {
            updateOutputMessages("Error: ${CommandErrors.NoGraphSelected().type}.${CommandErrors.NoGraphSelected().description}")
            return
        }
        val commands = commandLine.split(";")
        commands.forEach { command ->
            val commandResult = Command.Companion.create(command)
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
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.canvasBackground),
    ) {
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
            viewModel = viewModel,
            modifier = Modifier.Companion.align(Alignment.Companion.TopCenter),
        )

        Row(
            modifier =
                Modifier.Companion
                    .align(Alignment.Companion.BottomCenter)
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(8.dp),
            verticalAlignment = Alignment.Companion.CenterVertically,
        ) {
            GraphElementCounters(
                vertexCount =
                    viewModel.graph.value
                        ?.vertices
                        ?.size
                        ?.toLong() ?: 0L,
                edgeCount =
                    viewModel.graph.value
                        ?.edges
                        ?.size
                        ?.toLong() ?: 0L,
                vertexLabel = "vertices",
                edgeLabel = "edges",
                modifier = Modifier.Companion.align(Alignment.Companion.Bottom),
            )
            Spacer(Modifier.Companion.weight(1f))
            CommandLine(
                modifier =
                    Modifier.Companion
                        .width(commandFieldWidth)
                        .align(Alignment.Companion.Bottom),
                outputMessages = outputMessages.value,
                commandLineBackgroundColor =
                    if (isTransparentCommandLine) {
                        Color.Companion.Transparent
                    } else {
                        MaterialTheme.colors.commandLineBackground
                    },
                placeholderText = "Enter command",
                onCommand = { command -> handleCommand(command) },
            )
            Spacer(Modifier.Companion.weight(1f))
            ZoomButtons(
                modifier = Modifier.Companion.padding(8.dp),
                onZoom = { zoomDelta -> viewModel.zoomCanvas(zoomDelta) },
            )
        }
    }
}
