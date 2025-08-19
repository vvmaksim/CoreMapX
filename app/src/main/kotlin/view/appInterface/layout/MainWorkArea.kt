package view.appInterface.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import extensions.border
import extensions.canvasBackground
import extensions.commandLineBlockBackground
import model.command.concrete.Command
import model.command.concrete.Commands
import model.result.CommandErrors
import model.result.Result
import org.coremapx.app.AppLogger.logInfo
import org.coremapx.app.AppLogger.logWarning
import org.coremapx.app.config
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import view.appInterface.button.ZoomButtons
import view.appInterface.workspace.CommandLine
import view.appInterface.workspace.FloatingMessagePanel
import view.appInterface.workspace.GraphElementCounters
import view.appInterface.workspace.TopMenu
import view.graph.GraphView
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel
import viewmodel.visualizationStrategy.AnimatedVisualizationStrategy
import viewmodel.visualizationStrategy.AnimationParameters

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
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.graphManager.animationScope = scope
    }

    val maxCountMessages = config.states.maxCountMessages.value
    val maxUserCommands = config.states.maxCountUserCommands.value
    val commandFieldWidth = config.states.commandFieldWidth.value.dp
    val isTransparentCommandLineBlock = config.states.isTransparentCommandLineBlock.value

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

            val animatedStrategy = viewModel.graphManager.layoutStrategy.value as? AnimatedVisualizationStrategy<E, V>
            if (animatedStrategy != null) {
                val params = animatedStrategy.getParameters()
                var iterations by remember { mutableStateOf(params.iterations.toFloat()) }
                var area by remember { mutableStateOf(params.area.toFloat()) }
                var gravity by remember { mutableStateOf(params.gravity.toFloat()) }
                var speed by remember { mutableStateOf(params.speed.toFloat()) }
                val isRunning = animatedStrategy.isRunning()

                Card(
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .widthIn(min = 320.dp, max = 400.dp),
                    elevation = 16.dp,
                    shape = MaterialTheme.shapes.large,
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = LocalizationManager.states.ui.forceDirectedMenuTitle.value,
                            style = MaterialTheme.typography.h6,
                        )
                        Row {
                            Button(onClick = {
                                animatedStrategy.setParameters(
                                    AnimationParameters(
                                        iterations = iterations.toInt(),
                                        area = area.toDouble(),
                                        gravity = gravity.toDouble(),
                                        speed = speed.toDouble(),
                                    ),
                                )
                                viewModel.graphManager.resetGraphView()
                            }) {
                                Text(LocalizationManager.states.ui.forceDirectedMenuApply.value)
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(onClick = {
                                if (isRunning) {
                                    animatedStrategy.stopAnimation()
                                } else {
                                    viewModel.graphManager.resetGraphView()
                                }
                            }) {
                                Text(
                                    text =
                                        if (isRunning) {
                                            LocalizationManager.states.ui.forceDirectedMenuStop.value
                                        } else {
                                            LocalizationManager.states.ui.forceDirectedMenuStart.value
                                        },
                                )
                            }
                        }
                        Text(
                            text =
                                LocalizationFormatter.getStringWithOneNumber(
                                    startString = LocalizationManager.states.ui.forceDirectedMenuIterations.value,
                                    number = iterations.toLong(),
                                ),
                        )
                        Slider(
                            value = iterations,
                            onValueChange = { iterations = it },
                            valueRange = PrivateConfig.LayoutStrategies.ForceDirected.iterationsRange,
                        )
                        Text(
                            text =
                                LocalizationFormatter.getStringWithOneNumber(
                                    startString = LocalizationManager.states.ui.forceDirectedMenuArea.value,
                                    number = area.toLong(),
                                ),
                        )
                        Slider(
                            value = area,
                            onValueChange = { area = it },
                            valueRange = PrivateConfig.LayoutStrategies.ForceDirected.areaRange,
                        )
                        Text(
                            text =
                                LocalizationFormatter.getStringWithOneNumber(
                                    startString = LocalizationManager.states.ui.forceDirectedMenuGravity.value,
                                    number = gravity,
                                ),
                        )
                        Slider(
                            value = gravity,
                            onValueChange = { gravity = it },
                            valueRange = PrivateConfig.LayoutStrategies.ForceDirected.gravityRange,
                        )
                        Text(
                            text =
                                LocalizationFormatter.getStringWithOneNumber(
                                    startString = LocalizationManager.states.ui.forceDirectedMenuSpeed.value,
                                    number = speed,
                                ),
                        )
                        Slider(
                            value = speed,
                            onValueChange = { speed = it },
                            valueRange = PrivateConfig.LayoutStrategies.ForceDirected.speedRange,
                        )
                    }
                }
            }

            TopMenu(
                viewModel = viewModel,
                modifier = Modifier.align(Alignment.TopStart),
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

                var commandLinePosition by remember { mutableStateOf(Offset.Zero) }
                var commandLineHeight by remember { mutableStateOf(0f) }
                val density = LocalDensity.current

                Column(
                    modifier =
                        Modifier
                            .width(commandFieldWidth)
                            .align(Alignment.Bottom),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (commandLinePosition != Offset.Zero) {
                        FloatingMessagePanel(
                            outputMessages = outputMessages.value,
                            modifier = Modifier,
                            initialPosition =
                                with(density) {
                                    Offset(
                                        x = commandLinePosition.x,
                                        y = commandLinePosition.y - 224.dp.toPx() - commandLineHeight,
                                    )
                                },
                            backgroundColor =
                                if (isTransparentCommandLineBlock) {
                                    Color.Transparent
                                } else {
                                    MaterialTheme.colors.commandLineBlockBackground
                                },
                            borderColor = MaterialTheme.colors.border,
                        )
                    }
                    CommandLine(
                        modifier =
                            Modifier
                                .width(commandFieldWidth)
                                .align(Alignment.CenterHorizontally)
                                .onGloballyPositioned { coordinates ->
                                    commandLinePosition =
                                        Offset(
                                            x = coordinates.positionInWindow().x,
                                            y = coordinates.positionInWindow().y,
                                        )
                                    commandLineHeight = coordinates.size.height.toFloat()
                                },
                        commandLineBackgroundColor =
                            if (isTransparentCommandLineBlock) {
                                Color.Transparent
                            } else {
                                MaterialTheme.colors.commandLineBlockBackground
                            },
                        placeholderText = LocalizationManager.states.anyTextStates.enterCommand.value,
                        onCommand = { command -> handleCommand(command) },
                        commandText = commandText,
                        onCommandTextChange = { commandText = it },
                        userCommands = userCommands.value,
                        commandHistoryIndex = commandHistoryIndex,
                        onCommandHistoryIndexChange = { commandHistoryIndex = it },
                    )
                }
                Spacer(Modifier.weight(1f))
                ZoomButtons(
                    modifier =
                        Modifier
                            .padding(8.dp)
                            .align(Alignment.Bottom),
                    onZoom = { zoomDelta -> viewModel.canvasManager.zoomCanvas(zoomDelta) },
                )
            }
        },
    )
}
