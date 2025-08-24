package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import model.dto.AddEdgeData
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import view.appInterface.textField.CustomTextField

@Suppress("ktlint:standard:function-naming")
@Composable
fun AddEdge(
    onDismiss: () -> Unit,
    onAdd: (commandLine: String) -> Unit,
    isWeighted: Boolean,
    dialogWidth: Dp = 450.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        AddEdgeContent(
            onDismiss = onDismiss,
            onAdd = onAdd,
            isWeighted = isWeighted,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun AddEdgeContent(
    onDismiss: () -> Unit,
    onAdd: (commandLine: String) -> Unit,
    isWeighted: Boolean,
    dialogWidth: Dp = 450.dp,
) {
    val edges = remember { mutableStateListOf(AddEdgeData()) }
    var redrawTrigger by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    fun checkParameters(index: Int): Boolean {
        val edge = edges[index]
        edge.showWeightError = false
        edge.showVertexFromIdError = false
        edge.showVertexToIdError = false
        val isVertexFromIdEmpty = edge.vertexFromId.text.isEmpty()
        val isVertexToIdEmpty = edge.vertexToId.text.isEmpty()

        if (isVertexFromIdEmpty || isVertexToIdEmpty) {
            if (isVertexFromIdEmpty && isVertexToIdEmpty) {
                edge.errorMessage = LocalizationManager.states.dialogs.addEdgeFromVertexIdAndToVertexIdCannotBeEmpty.value
                edge.showVertexFromIdError = true
                edge.showVertexToIdError = true
            } else if (isVertexFromIdEmpty && !isVertexToIdEmpty) {
                edge.errorMessage = LocalizationManager.states.dialogs.addEdgeFromVertexIdCannotBeEmpty.value
                edge.showVertexFromIdError = true
            } else if (!isVertexFromIdEmpty && isVertexToIdEmpty) {
                edge.errorMessage = LocalizationManager.states.dialogs.addEdgeToVertexIdCannotBeEmpty.value
                edge.showVertexToIdError = true
            }
            return false
        }
        if (isWeighted) {
            if (edge.weight.text.isEmpty()) {
                edge.errorMessage = LocalizationManager.states.dialogs.addEdgeWeightCannotBeEmpty.value
                edge.showWeightError = true
                return false
            }
        }
        val isVertexFromIdNotLong = edge.vertexFromId.text.toLongOrNull() == null
        val isVertexToIdNotLong = edge.vertexToId.text.toLongOrNull() == null

        if (isVertexFromIdNotLong || isVertexToIdNotLong) {
            if (isVertexFromIdNotLong && isVertexToIdNotLong) {
                edge.errorMessage = LocalizationManager.states.dialogs.addEdgeFromVertexIdAndToVertexIdMustBeLong.value
                edge.showVertexFromIdError = true
                edge.showVertexToIdError = true
            } else if (isVertexFromIdNotLong && !isVertexToIdNotLong) {
                edge.errorMessage = LocalizationManager.states.dialogs.addEdgeFromVertexIdMustBeLong.value
                edge.showVertexFromIdError = true
            } else if (!isVertexFromIdNotLong && isVertexToIdNotLong) {
                edge.errorMessage = LocalizationManager.states.dialogs.addEdgeToVertexIdMustBeLong.value
                edge.showVertexToIdError = true
            }
            return false
        }
        if (isWeighted) {
            if (edge.weight.text.toLongOrNull() == null) {
                edge.errorMessage = LocalizationManager.states.dialogs.addEdgeWeightMustBeLong.value
                edge.showWeightError = true
                return false
            }
        }

        return true
    }

    fun validateAll(): Boolean {
        var allValid = true
        for (i in edges.indices) {
            if (!checkParameters(i)) {
                allValid = false
            }
        }
        return allValid
    }

    Surface(
        modifier =
            Modifier
                .width(dialogWidth)
                .heightIn(max = 900.dp)
                .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            key(redrawTrigger) {
                DialogHeader(
                    title = LocalizationManager.states.dialogs.addEdgeTitle.value,
                    onButtonClick = onDismiss,
                )
                Spacer(modifier = Modifier.height(24.dp))

                Column {
                    edges.forEachIndexed { index, _ ->
                        var vertexFromId by remember { mutableStateOf(edges[index].vertexFromId) }
                        var vertexToId by remember { mutableStateOf(edges[index].vertexToId) }
                        var weight by remember { mutableStateOf(edges[index].weight) }
                        var errorMessage by remember { mutableStateOf(edges[index].errorMessage) }
                        var showVertexFromIdError by remember { mutableStateOf(edges[index].showVertexFromIdError) }
                        var showVertexToIdError by remember { mutableStateOf(edges[index].showVertexToIdError) }
                        var showWeightError by remember { mutableStateOf(edges[index].showWeightError) }

                        Column {
                            Text(
                                text =
                                    LocalizationFormatter.getStringWithOneNumber(
                                        startString = LocalizationManager.states.anyTextStates.edgeWithNumber.value,
                                        number = index + 1,
                                    ),
                                style = MaterialTheme.typography.h5,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            CustomTextField(
                                value = vertexFromId,
                                onValueChange = { newValue ->
                                    vertexFromId = newValue
                                    edges[index].vertexFromId = newValue
                                    checkParameters(index)
                                    errorMessage = edges[index].errorMessage
                                    showVertexFromIdError = edges[index].showVertexFromIdError
                                    showVertexToIdError = edges[index].showVertexToIdError
                                    showWeightError = edges[index].showWeightError
                                },
                                modifier = Modifier.fillMaxWidth(),
                                isError = showVertexFromIdError,
                                label = { Text(LocalizationManager.states.dialogs.addEdgeFromVertexIdFieldLabel.value) },
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            CustomTextField(
                                value = vertexToId,
                                onValueChange = { newValue ->
                                    vertexToId = newValue
                                    edges[index].vertexToId = newValue
                                    checkParameters(index)
                                    errorMessage = edges[index].errorMessage
                                    showVertexFromIdError = edges[index].showVertexFromIdError
                                    showVertexToIdError = edges[index].showVertexToIdError
                                    showWeightError = edges[index].showWeightError
                                },
                                modifier = Modifier.fillMaxWidth(),
                                isError = showVertexToIdError,
                                label = { Text(LocalizationManager.states.dialogs.addEdgeToVertexIdFieldLabel.value) },
                            )

                            if (isWeighted) {
                                Spacer(modifier = Modifier.height(24.dp))
                                CustomTextField(
                                    value = weight,
                                    onValueChange = { newValue ->
                                        weight = newValue
                                        edges[index].weight = newValue
                                        checkParameters(index)
                                        errorMessage = edges[index].errorMessage
                                        showVertexFromIdError = edges[index].showVertexFromIdError
                                        showVertexToIdError = edges[index].showVertexToIdError
                                        showWeightError = edges[index].showWeightError
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    isError = showWeightError,
                                    label = { Text(LocalizationManager.states.dialogs.addEdgeWeightFieldLabel.value) },
                                )
                            }

                            if (showVertexFromIdError || showVertexToIdError || showWeightError) {
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = errorMessage,
                                    color = MaterialTheme.colors.error,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
                Button(
                    onClick = {
                        if (edges.size > 1) {
                            edges.removeAt(edges.size - 1)
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    enabled = edges.size > 1,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = LocalizationManager.states.anyTextStates.removeLast.value,
                        style = MaterialTheme.typography.button,
                    )
                }
                Button(
                    onClick = {
                        edges.add(AddEdgeData())
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = LocalizationManager.states.anyTextStates.addMore.value,
                        style = MaterialTheme.typography.button,
                    )
                }
                Button(
                    onClick = {
                        for (i in edges.indices) {
                            checkParameters(i)
                        }

                        if (validateAll() && edges.isNotEmpty()) {
                            val commandLine =
                                if (isWeighted) {
                                    edges.joinToString("; ") { edge ->
                                        "add edge from:${edge.vertexFromId.text} to:${edge.vertexToId.text} weight:${edge.weight.text}"
                                    }
                                } else {
                                    edges.joinToString("; ") { edge ->
                                        "add edge from:${edge.vertexFromId.text} to:${edge.vertexToId.text}"
                                    }
                                }

                            onAdd(commandLine)
                            onDismiss()
                        } else {
                            redrawTrigger++
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = LocalizationManager.states.dialogs.addEdgeButton.value,
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewAddEdge() {
    AppTheme {
        AddEdgeContent(
            dialogWidth = 450.dp,
            onDismiss = {},
            isWeighted = true,
            onAdd = {},
        )
    }
}
