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
    var vertexId by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }
    var showVertexIdError by remember { mutableStateOf(false) }

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
                title = LocalizationManager.states.dialogs.rmVertexTitle.value,
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
            Button(
                onClick = {
                    if (vertexId.text.isEmpty()) {
                        errorMessage = LocalizationManager.states.dialogs.rmVertexVertexIdCannotBeEmpty.value
                        showVertexIdError = true
                        return@Button
                    }
                    if (vertexId.text.toLongOrNull() == null) {
                        errorMessage = LocalizationManager.states.dialogs.rmVertexVertexIdMustBeLong.value
                        showVertexIdError = true
                        return@Button
                    }
                    onRm("rm vertex id:${vertexId.text}")
                    onDismiss()
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
