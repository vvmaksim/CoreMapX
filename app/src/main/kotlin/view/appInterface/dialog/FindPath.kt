package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import model.graph.pathfinding.PathfindingValidator
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import view.appInterface.settingsElements.lines.DropdownSelectLine
import view.appInterface.textField.CustomTextFieldLine
import viewmodel.MainScreenViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> FindPath(
    viewModel: MainScreenViewModel<E, V>,
    onDismiss: () -> Unit,
    dialogWidth: Dp = 550.dp,
    isExpanded: Boolean = config.states.isExpandedSettings.value,
) {
    Dialog(onDismissRequest = onDismiss) {
        FindPathContent(
            viewModel = viewModel,
            onDismiss = onDismiss,
            dialogWidth = dialogWidth,
            isExpanded = isExpanded,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun <E : Comparable<E>, V : Comparable<V>> FindPathContent(
    viewModel: MainScreenViewModel<E, V>,
    onDismiss: () -> Unit,
    dialogWidth: Dp = 550.dp,
    isExpanded: Boolean = config.states.isExpandedSettings.value,
) {
    var startId by remember { mutableStateOf(TextFieldValue()) }
    var endId by remember { mutableStateOf(TextFieldValue()) }
    var maxPaths by remember { mutableStateOf(TextFieldValue("1")) }
    var startError by remember { mutableStateOf(false) }
    var endError by remember { mutableStateOf(false) }
    var maxPathsError by remember { mutableStateOf(false) }
    val (errorMessage, setErrorMessage) = remember { mutableStateOf<String?>(null) }

    Surface(
        modifier =
            Modifier
                .width(dialogWidth)
                .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DialogHeader(
                title = LocalizationManager.states.dialogs.findPathTitle.value,
                subtitle = LocalizationManager.states.dialogs.findPathSubtitle.value,
                onButtonClick = onDismiss,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                CustomTextFieldLine(
                    title = LocalizationManager.states.dialogs.findPathStartVertexId.value,
                    value = startId,
                    onValueChange = { startId = it },
                    description = LocalizationManager.states.dialogs.findPathStartVertexDescription.value,
                    placeholder = { Text(LocalizationManager.states.dialogs.findPathVertexIdPlaceholder.value) },
                    isError = startError,
                    isExpanded = isExpanded,
                )
                CustomTextFieldLine(
                    title = LocalizationManager.states.dialogs.findPathEndVertexId.value,
                    value = endId,
                    onValueChange = { endId = it },
                    description = LocalizationManager.states.dialogs.findPathEndVertexDescription.value,
                    placeholder = { Text(LocalizationManager.states.dialogs.findPathVertexIdPlaceholder.value) },
                    isError = endError,
                    isExpanded = isExpanded,
                )
                CustomTextFieldLine(
                    title = LocalizationManager.states.dialogs.findPathMaxPathsCount.value,
                    value = maxPaths,
                    onValueChange = { maxPaths = it },
                    description =
                        LocalizationFormatter.getStringWithLineBreak(
                            startString = LocalizationManager.states.dialogs.findPathMaxPathsDescription.value,
                        ),
                    placeholder = { Text(LocalizationManager.states.dialogs.findPathMaxPathsPlaceholder.value) },
                    isError = maxPathsError,
                    isExpanded = isExpanded,
                )
                DropdownSelectLine(
                    title = LocalizationManager.states.dialogs.findPathFindStrategy.value,
                    description =
                        LocalizationFormatter.getStringWithLineBreak(
                            startString = LocalizationManager.states.dialogs.findPathFindStrategyDescription.value,
                        ),
                    items = viewModel.pathfindingManager.getAllStrategiesAsList(),
                    selectedItem = viewModel.pathfindingManager.getCurrentStrategyAsString(),
                    onItemSelected = { newStrategyName: String ->
                        viewModel.pathfindingManager.setStrategyByStringName(newStrategyName)
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    isExpanded = isExpanded,
                )
            }
            if (errorMessage != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    softWrap = true,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    startError = false
                    endError = false
                    maxPathsError = false
                    setErrorMessage(null)

                    val startLong = startId.text.toLongOrNull()
                    val endLong = endId.text.toLongOrNull()
                    val maxPathsInt = maxPaths.text.toIntOrNull()

                    PathfindingValidator.validateInputParameters(
                        startId = startLong,
                        endId = endLong,
                        maxPaths = maxPathsInt,
                        isVertexExist = { vertexId: V ->
                            viewModel.graph.value
                                ?.vertices
                                ?.containsKey(vertexId)
                        },
                        onStartError = { startError = true },
                        onEndError = { endError = true },
                        onMaxPathsError = { maxPathsError = true },
                        onSetErrorMessage = { newErrorMessage ->
                            setErrorMessage(newErrorMessage)
                        },
                    )

                    if (!startError && !endError && !maxPathsError) {
                        val findResult =
                            viewModel.pathfindingManager.findPath(
                                start = startLong as V,
                                end = endLong as V,
                                maxPaths = maxPathsInt as Int,
                            )
                        when (findResult) {
                            is Result.Error -> {
                                setErrorMessage(
                                    LocalizationFormatter.getErrorMessage(
                                        startString = LocalizationManager.states.ui.errorBasicString.value,
                                        errorType = findResult.error.type,
                                        errorDescription = findResult.error.description,
                                    ),
                                )
                            }
                            is Result.Success -> {
                                onDismiss()
                            }
                        }
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = LocalizationManager.states.dialogs.findPathFindButton.value,
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewAnalytics() {
    AppTheme {
        FindPathContent(
            viewModel = MainScreenViewModel<Long, Long>(),
            onDismiss = {},
            isExpanded = false,
        )
    }
}
