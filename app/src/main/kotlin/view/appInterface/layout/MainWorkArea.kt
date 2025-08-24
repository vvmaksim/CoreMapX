package view.appInterface.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import extensions.border
import extensions.canvasBackground
import extensions.consoleBackground
import model.command.concrete.Command
import model.command.concrete.Commands
import model.result.CommandErrors
import model.result.Result
import org.coremapx.app.AppLogger.logInfo
import org.coremapx.app.AppLogger.logWarning
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.userDirectory.config.ConfigRepository
import view.appInterface.dialog.AddEdge
import view.appInterface.dialog.AddVertex
import view.appInterface.workspace.Console
import view.appInterface.workspace.ForceDirectedMenu
import view.appInterface.workspace.GraphElementCounters
import view.appInterface.workspace.LowerRightMenu
import view.appInterface.workspace.TopMenu
import view.graph.GraphView
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainWorkArea(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier,
) {
    var commandCount by remember { mutableStateOf(0) }
    var showAddVertexDialog by remember { mutableStateOf(false) }
    var showAddEdgeDialog by remember { mutableStateOf(false) }
    var consolePosition by remember { mutableStateOf(Offset.Zero) }

    val outputMessages = remember { mutableStateOf(mutableListOf<String>()) }
    val userCommands = remember { mutableStateOf(mutableListOf<String>()) }

    val scope = rememberCoroutineScope()
    val graph = viewModel.graphManager.graph.value

    LaunchedEffect(Unit) {
        viewModel.graphManager.animationScope = scope
    }

    val graphViewModel by remember(graph, commandCount) {
        derivedStateOf {
            graph?.let {
                viewModel.graphManager.updateGraphViewModel(
                    newViewModel =
                        GraphViewModel(
                            graph = it,
                            visibleStates = viewModel.graphManager.visibleStates,
                        ),
                )
            }
            viewModel.graphManager.graphViewModel.value?.let { _ ->
                viewModel.graphManager.resetGraphView()
            }
            viewModel.graphManager.graphViewModel
        }
    }

    fun updateOutputMessages(newMessage: String) {
        outputMessages.value.add(newMessage)
        outputMessages.value = outputMessages.value.takeLast(ConfigRepository.states.maxCountMessages.value).toMutableList()
    }

    fun updateUserCommands(newCommand: String) {
        userCommands.value.add(newCommand)
        userCommands.value = userCommands.value.takeLast(ConfigRepository.states.maxCountUserCommands.value).toMutableList()
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
                            logInfo(executeResult.data)
                            commandCount++
                        }
                        is Result.Error -> {
                            updateOutputMessages(errorMessage(executeResult))
                            logWarning(errorMessage(executeResult))
                        }
                    }
                }
                is Result.Error -> {
                    updateOutputMessages(errorMessage(commandResult))
                    logWarning(errorMessage(commandResult))
                }
            }
        }
    }

    BoxWithConstraints(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.canvasBackground)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { _, dragAmount ->
                            viewModel.canvasManager.moveCanvas(dragAmount.x, dragAmount.y)
                        },
                    )
                }.pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            if (event.type == PointerEventType.Scroll) {
                                val scrollAmount = event.changes.first().scrollDelta
                                viewModel.canvasManager.zoomCanvas(-scrollAmount.y * 0.1f)
                            }
                        }
                    }
                },
        content = {
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

            ForceDirectedMenu(
                viewModel = viewModel,
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .widthIn(min = 320.dp, max = 400.dp),
            )

            TopMenu(
                viewModel = viewModel,
                modifier = Modifier.align(Alignment.TopStart),
            )

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
                modifier =
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart),
            )

            LowerRightMenu(
                onZoom = { zoomDelta -> viewModel.canvasManager.zoomCanvas(zoomDelta) },
                onAddVertex = { showAddVertexDialog = true },
                onAddEdge = { showAddEdgeDialog = true },
                onGraphClear = { commandLine: String ->
                    handleCommand(commandLine)
                },
                modifier =
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
            )

            if (showAddVertexDialog) {
                AddVertex(
                    onDismiss = { showAddVertexDialog = false },
                    onAdd = { commandLine: String ->
                        handleCommand(commandLine)
                    },
                )
            }

            if (showAddEdgeDialog) {
                AddEdge(
                    onDismiss = { showAddEdgeDialog = false },
                    onAdd = { commandLine: String ->
                        handleCommand(commandLine)
                    },
                    isWeighted =
                        viewModel.graphManager.graph.value
                            ?.isWeighted ?: true,
                )
            }
        },
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Console(
            outputMessages = outputMessages.value,
            userCommands = userCommands,
            onCommand = { commandLine: String ->
                handleCommand(commandLine)
            },
            modifier =
                Modifier
                    .offset { IntOffset(consolePosition.x.toInt(), consolePosition.y.toInt()) }
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter),
            backgroundColor =
                if (ConfigRepository.states.isTransparentConsoleBlock.value) {
                    Color.Transparent
                } else {
                    MaterialTheme.colors.consoleBackground
                },
            borderColor = MaterialTheme.colors.border,
            onDrag = { dragAmount: Offset ->
                consolePosition += dragAmount
            },
        )
    }
}
