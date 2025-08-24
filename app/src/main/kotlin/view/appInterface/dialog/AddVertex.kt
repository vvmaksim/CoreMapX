package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
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
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.textField.CustomTextField

@Suppress("ktlint:standard:function-naming")
@Composable
fun AddVertex(
    onDismiss: () -> Unit,
    onAdd: (commandLine: String) -> Unit,
    dialogWidth: Dp = 450.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        AddVertexContent(
            onDismiss = onDismiss,
            onAdd = onAdd,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun AddVertexContent(
    onDismiss: () -> Unit,
    onAdd: (commandLine: String) -> Unit,
    dialogWidth: Dp = 450.dp,
) {
    var vertexId by remember { mutableStateOf(TextFieldValue("")) }
    var vertexLabel by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }
    var showVertexIdError by remember { mutableStateOf(false) }
    var showVertexLabelError by remember { mutableStateOf(false) }

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
                    .fillMaxWidth()
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DialogHeader(
                title = LocalizationManager.states.dialogs.addVertexTitle.value,
                onButtonClick = onDismiss,
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomTextField(
                value = vertexId,
                onValueChange = {
                    vertexId = it
                    showVertexIdError = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(LocalizationManager.states.dialogs.addVertexVertexIdFieldLabel.value) },
                isError = showVertexIdError,
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomTextField(
                value = vertexLabel,
                onValueChange = {
                    vertexLabel = it
                    showVertexLabelError = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(LocalizationManager.states.dialogs.addVertexVertexLabelCannotBeEmpty.value) },
                isError = showVertexLabelError,
            )

            if (showVertexIdError || showVertexLabelError) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val isVertexIdEmpty = vertexId.text.isEmpty()
                    val isVertexLabelEmpty = vertexLabel.text.isEmpty()
                    if (isVertexIdEmpty || isVertexLabelEmpty) {
                        if (isVertexIdEmpty && isVertexLabelEmpty) {
                            errorMessage = LocalizationManager.states.dialogs.addVertexVertexIdAndVertexLabelCannotBeEmpty.value
                            showVertexIdError = true
                            showVertexLabelError = true
                        }
                        if (isVertexIdEmpty && !isVertexLabelEmpty) {
                            errorMessage = LocalizationManager.states.dialogs.addVertexVertexIdCannotBeEmpty.value
                            showVertexIdError = true
                        }
                        if (!isVertexIdEmpty && isVertexLabelEmpty) {
                            errorMessage = LocalizationManager.states.dialogs.addVertexVertexLabelFieldLabel.value
                            showVertexLabelError = true
                        }
                        return@Button
                    }
                    if (vertexId.text.toLongOrNull() == null) {
                        errorMessage = LocalizationManager.states.dialogs.addVertexVertexIdMustBeLong.value
                        showVertexIdError = true
                        return@Button
                    }
                    onAdd("add vertex id:${vertexId.text} label:${vertexLabel.text}")
                    onDismiss()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = LocalizationManager.states.dialogs.addVertexButton.value,
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewAddVertex() {
    AppTheme {
        AddVertexContent(
            dialogWidth = 450.dp,
            onDismiss = {},
            onAdd = {},
        )
    }
}
