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
    var fromVertexId by remember { mutableStateOf(TextFieldValue("")) }
    var toVertexId by remember { mutableStateOf(TextFieldValue("")) }
    var weight by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf("") }
    var showFromVertexIdError by remember { mutableStateOf(false) }
    var showToVertexIdError by remember { mutableStateOf(false) }
    var showWeightError by remember { mutableStateOf(false) }

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
                title = LocalizationManager.states.dialogs.addEdgeTitle.value,
                onButtonClick = onDismiss,
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomTextField(
                value = fromVertexId,
                onValueChange = {
                    fromVertexId = it
                    showFromVertexIdError = false
                },
                modifier = Modifier.fillMaxWidth(),
                isError = showFromVertexIdError,
                label = { Text(LocalizationManager.states.dialogs.addEdgeFromVertexIdFieldLabel.value) },
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomTextField(
                value = toVertexId,
                onValueChange = {
                    toVertexId = it
                    showToVertexIdError = false
                },
                modifier = Modifier.fillMaxWidth(),
                isError = showToVertexIdError,
                label = { Text(LocalizationManager.states.dialogs.addEdgeToVertexIdFieldLabel.value) },
            )

            if (isWeighted) {
                Spacer(modifier = Modifier.height(24.dp))
                CustomTextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                        showWeightError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showWeightError,
                    label = { Text(LocalizationManager.states.dialogs.addEdgeWeightFieldLabel.value) },
                )
            }

            if (showFromVertexIdError || showToVertexIdError || showWeightError) {
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
                    val isFromVertexIdEmpty = fromVertexId.text.isEmpty()
                    val isToVertexIdEmpty = toVertexId.text.isEmpty()
                    if (isFromVertexIdEmpty || isToVertexIdEmpty) {
                        if (isFromVertexIdEmpty && isToVertexIdEmpty) {
                            errorMessage = LocalizationManager.states.dialogs.addEdgeFromVertexIdAndToVertexIdCannotBeEmpty.value
                            showFromVertexIdError = true
                            showToVertexIdError = true
                        }
                        if (isFromVertexIdEmpty && !isToVertexIdEmpty) {
                            errorMessage = LocalizationManager.states.dialogs.addEdgeFromVertexIdCannotBeEmpty.value
                            showFromVertexIdError = true
                        }
                        if (!isFromVertexIdEmpty && isToVertexIdEmpty) {
                            errorMessage = LocalizationManager.states.dialogs.addEdgeToVertexIdCannotBeEmpty.value
                            showToVertexIdError = true
                        }
                        return@Button
                    }
                    if (isWeighted) {
                        if (weight.text.isEmpty()) {
                            errorMessage = LocalizationManager.states.dialogs.addEdgeWeightCannotBeEmpty.value
                            showWeightError = true
                            return@Button
                        }
                    }
                    val isFromVertexIdNotLong = fromVertexId.text.toLongOrNull() == null
                    val isToVertexIdNotLong = toVertexId.text.toLongOrNull() == null
                    if (isFromVertexIdNotLong || isToVertexIdNotLong) {
                        if (isFromVertexIdNotLong && isToVertexIdNotLong) {
                            errorMessage = LocalizationManager.states.dialogs.addEdgeFromVertexIdAndToVertexIdMustBeLong.value
                            showFromVertexIdError = true
                            showToVertexIdError = true
                        }
                        if (isFromVertexIdNotLong && !isToVertexIdNotLong) {
                            errorMessage = LocalizationManager.states.dialogs.addEdgeFromVertexIdMustBeLong.value
                            showFromVertexIdError = true
                        }
                        if (!isFromVertexIdNotLong && isToVertexIdNotLong) {
                            errorMessage = LocalizationManager.states.dialogs.addEdgeToVertexIdMustBeLong.value
                            showToVertexIdError = true
                        }
                        return@Button
                    }
                    if (isWeighted) {
                        if (weight.text.toLongOrNull() == null) {
                            errorMessage = LocalizationManager.states.dialogs.addEdgeWeightMustBeLong.value
                            showWeightError = true
                            return@Button
                        }
                    }
                    if (isWeighted) {
                        onAdd("add edge from:${fromVertexId.text} to:${toVertexId.text} weight:${weight.text}")
                    } else {
                        onAdd("add edge from:${fromVertexId.text} to:${toVertexId.text}")
                    }
                    onDismiss()
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
