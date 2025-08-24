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
import model.dto.RmVertexData
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import view.appInterface.textField.CustomTextField

@Suppress("ktlint:standard:function-naming")
@Composable
fun RmVertex(
    onDismiss: () -> Unit,
    onRm: (commandLine: String) -> Unit,
    dialogWidth: Dp = 450.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        RmVertexContent(
            onDismiss = onDismiss,
            onRm = onRm,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun RmVertexContent(
    onDismiss: () -> Unit,
    onRm: (commandLine: String) -> Unit,
    dialogWidth: Dp = 450.dp,
) {
    val vertices = remember { mutableStateListOf(RmVertexData()) }
    var redrawTrigger by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    fun checkParameters(index: Int): Boolean {
        val vertex = vertices[index]
        vertex.showVertexIdError = false

        if (vertex.vertexId.text.isEmpty()) {
            vertex.errorMessage = LocalizationManager.states.dialogs.rmVertexVertexIdCannotBeEmpty.value
            vertex.showVertexIdError = true
            return false
        }
        if (vertex.vertexId.text.toLongOrNull() == null) {
            vertex.errorMessage = LocalizationManager.states.dialogs.rmVertexVertexIdMustBeLong.value
            vertex.showVertexIdError = true
            return false
        }

        return true
    }

    fun validateAll(): Boolean {
        var allValid = true
        for (i in vertices.indices) {
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
                    title = LocalizationManager.states.dialogs.rmVertexTitle.value,
                    onButtonClick = onDismiss,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Column {
                    vertices.forEachIndexed { index, _ ->
                        var vertexId by remember { mutableStateOf(vertices[index].vertexId) }
                        var errorMessage by remember { mutableStateOf(vertices[index].errorMessage) }
                        var showVertexIdError by remember { mutableStateOf(vertices[index].showVertexIdError) }

                        Column {
                            Text(
                                text =
                                    LocalizationFormatter.getStringWithOneNumber(
                                        startString = LocalizationManager.states.anyTextStates.vertexWithNumber.value,
                                        number = index + 1,
                                    ),
                                style = MaterialTheme.typography.h5,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            CustomTextField(
                                value = vertexId,
                                onValueChange = { newValue ->
                                    vertexId = newValue
                                    vertices[index].vertexId = newValue
                                    checkParameters(index)
                                    errorMessage = vertices[index].errorMessage
                                    showVertexIdError = vertices[index].showVertexIdError
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(LocalizationManager.states.dialogs.rmVertexVertexIdFieldLabel.value) },
                                isError = showVertexIdError,
                            )

                            if (showVertexIdError) {
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
                        if (vertices.size > 1) {
                            vertices.removeAt(vertices.size - 1)
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    enabled = vertices.size > 1,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = LocalizationManager.states.anyTextStates.removeLast.value,
                        style = MaterialTheme.typography.button,
                    )
                }
                Button(
                    onClick = {
                        vertices.add(RmVertexData())
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
                        for (i in vertices.indices) {
                            checkParameters(i)
                        }

                        if (validateAll() && vertices.isNotEmpty()) {
                            val commandLine =
                                vertices.joinToString("; ") { vertex ->
                                    "rm vertex id:${vertex.vertexId.text}"
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
                        text = LocalizationManager.states.dialogs.rmVertexButton.value,
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
private fun PreviewRmVertex() {
    AppTheme {
        RmVertexContent(
            dialogWidth = 450.dp,
            onDismiss = {},
            onRm = {},
        )
    }
}
