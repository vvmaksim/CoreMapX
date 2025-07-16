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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import extensions.canvasBackground
import extensions.commandLineBackground
import model.command.concrete.Command
import model.command.concrete.Commands
import model.result.CommandErrors
import model.result.Result
import mu.KotlinLogging
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import view.appInterface.button.ZoomButtons
import view.appInterface.workspace.CommandLine
import view.appInterface.workspace.GraphElementCounters
import view.appInterface.workspace.TopMenu
import view.graph.GraphView
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel

private val logger = KotlinLogging.logger {}

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainWorkArea(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier,
) {
    val outputMessages = remember { mutableStateOf(mutableListOf<String>()) }
    val userCommands = remember { mutableStateOf(mutableListOf<String>()) }
    val scrollState = rememberScrollState()
    val graph = viewModel.graphManager.graph.value
    var commandCount by remember { mutableStateOf(0) }
    var commandText by remember { mutableStateOf(TextFieldValue("")) }
    var commandHistoryIndex by remember { mutableStateOf(-1) }

    val maxCountMessages = config.states.maxCountMessages.value
    val maxUserCommands = config.states.maxCountUserCommands.value
    val commandFieldWidth = config.states.commandFieldWidth.value.dp
    val isTransparentCommandLine = config.states.isTransparentCommandLine.value

    val graphViewModel by remember(graph, commandCount) {
        derivedStateOf {
            graph?.let { viewModel.graphManager.updateGraphViewModel(GraphViewModel(it, viewModel.graphManager.showVerticesLabels)) }
            viewModel.graphManager.graphViewModel.value?.let { _ ->
                viewModel.graphManager.resetGraphView()
            }
            viewModel.graphManager.graphViewModel
        }
    }

    fun updateOutputMessages(newMessage: String) {
        outputMessages.value.add(newMessage)
        outputMessages.value = outputMessages.value.takeLast(maxCountMessages).toMutableList()
    }

    fun updateUserCommands(newCommand: String) {
        userCommands.value.add(newCommand)
        userCommands.value = userCommands.value.takeLast(maxUserCommands).toMutableList()
    }

    fun errorMessage(errorResult: Result.Error): String =
        LocalizationFormatter.getErrorMessage(
            startString = LocalizationManager.states.ui.errorBasicString.value,
            errorType = errorResult.error.type,
            errorDescription = errorResult.error.description,
        )

    fun handleCommand(commandLine: String) {
        if (graph == null) {
            updateOutputMessages(
                LocalizationFormatter.getErrorMessage(
                    startString = LocalizationManager.states.ui.errorBasicString.value,
                    errorType = CommandErrors.NoGraphSelected().type,
                    errorDescription = CommandErrors.NoGraphSelected().description,
                ),
            )
            return
        }
        val commands = commandLine.split(";")
        commands.forEach { command ->
            updateUserCommands(command)
            val commandResult = Command.create(command)
            when (commandResult) {
                is Result.Success -> {
                    val executeResult = Commands(commandResult.data, graph, outputMessages.value, viewModel).execute()
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
        graphViewModel.value?.let { graphViewModel ->
            GraphView(
                viewModel = graphViewModel,
                offsetX = viewModel.canvasManager.offsetX.value,
                offsetY = viewModel.canvasManager.offsetY.value,
                scale = viewModel.canvasManager.scale.value,
                onPan = { dx, dy -> viewModel.canvasManager.moveCanvas(dx, dy) },
                onZoom = { delta -> viewModel.canvasManager.zoomCanvas(delta) },
            )
        }

        TopMenu(
            viewModel = viewModel,
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
            GraphElementCounters(
                vertexCount =
                    viewModel.graphManager.graph.value
                        ?.vertices
                        ?.size
                        ?.toLong() ?: 0L,
                edgeCount =
                    viewModel.graphManager.graph.value
                        ?.edges
                        ?.size
                        ?.toLong() ?: 0L,
                vertexLabel = LocalizationManager.states.anyTextStates.vertices.value,
                edgeLabel = LocalizationManager.states.anyTextStates.edges.value,
                modifier = Modifier.align(Alignment.Bottom),
            )
            Spacer(Modifier.weight(1f))
            CommandLine(
                modifier =
                    Modifier
                        .width(commandFieldWidth)
                        .align(Alignment.Bottom),
                outputMessages = outputMessages.value,
                commandLineBackgroundColor =
                    if (isTransparentCommandLine) {
                        Color.Transparent
                    } else {
                        MaterialTheme.colors.commandLineBackground
                    },
                placeholderText = LocalizationManager.states.anyTextStates.enterCommand.value,
                onCommand = { command -> handleCommand(command) },
                commandText = commandText,
                onCommandTextChange = { commandText = it },
                userCommands = userCommands.value,
                commandHistoryIndex = commandHistoryIndex,
                onCommandHistoryIndexChange = { commandHistoryIndex = it },
            )
            Spacer(Modifier.weight(1f))
            ZoomButtons(
                modifier = Modifier.padding(8.dp),
                onZoom = { zoomDelta -> viewModel.canvasManager.zoomCanvas(zoomDelta) },
            )
        }
    }
}
