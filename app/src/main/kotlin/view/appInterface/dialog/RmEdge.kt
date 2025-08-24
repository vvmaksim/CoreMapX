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
import model.dto.RmEdgeData
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.textField.CustomTextField

@Suppress("ktlint:standard:function-naming")
@Composable
fun RmEdge(
    onDismiss: () -> Unit,
    onRm: (commandLine: String) -> Unit,
    dialogWidth: Dp = 450.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        RmEdgeContent(
            onDismiss = onDismiss,
            onRm = onRm,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun RmEdgeContent(
    onDismiss: () -> Unit,
    onRm: (commandLine: String) -> Unit,
    dialogWidth: Dp = 450.dp,
) {
    val edges = remember { mutableStateListOf(RmEdgeData()) }
    var redrawTrigger by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    fun checkParameters(index: Int): Boolean {
        val edge = edges[index]
        edge.showVertexFromIdError = false
        edge.showVertexToIdError = false
        val isVertexFromIdEmpty = edge.vertexFromId.text.isEmpty()
        val isVertexToIdEmpty = edge.vertexToId.text.isEmpty()
        if (isVertexFromIdEmpty || isVertexToIdEmpty) {
            if (isVertexFromIdEmpty && isVertexToIdEmpty) {
                edge.errorMessage = LocalizationManager.states.dialogs.rmEdgeFromVertexIdAndToVertexIdCannotBeEmpty.value
                edge.showVertexFromIdError = true
                edge.showVertexToIdError = true
            }
            if (isVertexFromIdEmpty && !isVertexToIdEmpty) {
                edge.errorMessage = LocalizationManager.states.dialogs.rmEdgeFromVertexIdCannotBeEmpty.value
                edge.showVertexFromIdError = true
            }
            if (!isVertexFromIdEmpty && isVertexToIdEmpty) {
                edge.errorMessage = LocalizationManager.states.dialogs.rmEdgeToVertexIdCannotBeEmpty.value
                edge.showVertexToIdError = true
            }
            return false
        }
        val isFromVertexIdNotLong = edge.vertexFromId.text.toLongOrNull() == null
        val isToVertexIdNotLong = edge.vertexToId.text.toLongOrNull() == null
        if (isFromVertexIdNotLong || isToVertexIdNotLong) {
            if (isFromVertexIdNotLong && isToVertexIdNotLong) {
                edge.errorMessage = LocalizationManager.states.dialogs.rmEdgeFromVertexIdAndToVertexIdMustBeLong.value
                edge.showVertexFromIdError = true
                edge.showVertexToIdError = true
            }
            if (isFromVertexIdNotLong && !isToVertexIdNotLong) {
                edge.errorMessage = LocalizationManager.states.dialogs.rmEdgeFromVertexIdMustBeLong.value
                edge.showVertexFromIdError = true
            }
            if (!isFromVertexIdNotLong && isToVertexIdNotLong) {
                edge.errorMessage = LocalizationManager.states.dialogs.rmEdgeToVertexIdMustBeLong.value
                edge.showVertexToIdError = true
            }
            return false
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
                    title = LocalizationManager.states.dialogs.rmEdgeTitle.value,
                    onButtonClick = onDismiss,
                )
                Spacer(modifier = Modifier.height(24.dp))

                Column {
                    edges.forEachIndexed { index, _ ->
                        var vertexFromId by remember { mutableStateOf(edges[index].vertexFromId) }
                        var vertexToId by remember { mutableStateOf(edges[index].vertexToId) }
                        var errorMessage by remember { mutableStateOf(edges[index].errorMessage) }
                        var showFromVertexIdError by remember { mutableStateOf(edges[index].showVertexFromIdError) }
                        var showToVertexIdError by remember { mutableStateOf(edges[index].showVertexToIdError) }

                        CustomTextField(
                            value = vertexFromId,
                            onValueChange = { newValue ->
                                vertexFromId = newValue
                                edges[index].vertexFromId = newValue
                                checkParameters(index)
                                errorMessage = edges[index].errorMessage
                                showFromVertexIdError = edges[index].showVertexFromIdError
                                showToVertexIdError = edges[index].showVertexToIdError
                            },
                            modifier = Modifier.fillMaxWidth(),
                            isError = showFromVertexIdError,
                            label = { Text(LocalizationManager.states.dialogs.rmEdgeFromVertexIdFieldLabel.value) },
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        CustomTextField(
                            value = vertexToId,
                            onValueChange = { newValue ->
                                vertexToId = newValue
                                edges[index].vertexToId = newValue
                                checkParameters(index)
                                errorMessage = edges[index].errorMessage
                                showFromVertexIdError = edges[index].showVertexFromIdError
                                showToVertexIdError = edges[index].showVertexToIdError
                            },
                            modifier = Modifier.fillMaxWidth(),
                            isError = showToVertexIdError,
                            label = { Text(LocalizationManager.states.dialogs.rmEdgeToVertexIdFieldLabel.value) },
                        )

                        if (showFromVertexIdError || showToVertexIdError) {
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
                        edges.add(RmEdgeData())
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
                                edges.joinToString("; ") { edge ->
                                    "rm edge from:${edge.vertexFromId.text} to:${edge.vertexToId.text}"
                                }

                            onRm(commandLine)
                            onDismiss()
                        } else {
                            redrawTrigger++
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = LocalizationManager.states.dialogs.rmEdgeButton.value,
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
        RmEdgeContent(
            dialogWidth = 450.dp,
            onDismiss = {},
            onRm = {},
        )
    }
}
