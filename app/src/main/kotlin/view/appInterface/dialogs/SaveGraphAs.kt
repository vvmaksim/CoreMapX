package view.appInterface.dialogs

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import extensions.border
import model.fileHandler.FileExtensions
import model.fileHandler.SavedGraphDetails
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import view.appInterface.buttons.DropdownSelectButton
import view.appInterface.buttons.SavePathButton
import java.io.File

@Suppress("ktlint:standard:function-naming")
@Composable
fun SaveGraphAs(
    graphName: String,
    onDismiss: () -> Unit,
    onSave: (SavedGraphDetails) -> Unit,
    formats: List<String> = listOf(".graph", ".json", ".db"),
    dialogWidth: Dp = 550.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        SaveGraphAsContent(
            graphName = graphName,
            onDismiss = onDismiss,
            onSave = onSave,
            formats = formats,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SaveGraphAsContent(
    graphName: String,
    onDismiss: () -> Unit,
    onSave: (SavedGraphDetails) -> Unit,
    formats: List<String> = listOf(".graph", ".json", ".db"),
    dialogWidth: Dp = 550.dp,
) {
    var selectedFormat by remember { mutableStateOf(formats[0]) }
    var selectedPath by remember { mutableStateOf("$baseUserDirPath/data/graphs") }
    var selectedFileName by remember { mutableStateOf(graphName) }
    var showError by remember { mutableStateOf(false) }

    Card(
        modifier =
            Modifier
                .width(dialogWidth)
                .wrapContentHeight(),
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Save graph",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.Center),
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }
            }

            Text(
                text = "Select directory to save and file format",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "File name:",
                    style = MaterialTheme.typography.body2,
                )

                OutlinedTextField(
                    value = selectedFileName,
                    onValueChange = {
                        selectedFileName = it
                        showError = File("$selectedPath/$selectedFileName$selectedFormat").exists()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Name",
                            tint = MaterialTheme.colors.primary,
                        )
                    },
                    colors =
                        TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.primary,
                            unfocusedBorderColor = MaterialTheme.colors.border,
                            cursorColor = MaterialTheme.colors.primary,
                            focusedLabelColor = MaterialTheme.colors.primary,
                            unfocusedLabelColor = MaterialTheme.colors.onSurface,
                        ),
                    shape = MaterialTheme.shapes.medium,
                    singleLine = true,
                )

                Text(
                    text = "Save directory:",
                    style = MaterialTheme.typography.body2,
                )

                SavePathButton(
                    selectedPath = selectedPath,
                    onPathSelected = {
                        selectedPath = it
                        showError = File("$selectedPath/$selectedFileName$selectedFormat").exists()
                    },
                )

                Text(
                    text = "File format:",
                    style = MaterialTheme.typography.body2,
                )

                DropdownSelectButton(
                    items = formats,
                    selectedItem = selectedFormat,
                    onItemSelected = {
                        selectedFormat = it
                        showError = File("$selectedPath/$selectedFileName$selectedFormat").exists()
                    },
                )
            }

            if (showError) {
                Text(
                    text = "A file with this configuration already exists, and its contents will be replaced when saved",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }

            Button(
                onClick = {
                    onSave(
                        SavedGraphDetails(
                            fileName = selectedFileName,
                            directoryPath = selectedPath,
                            fileFormat =
                                when (selectedFormat) {
                                    ".graph" -> FileExtensions.GRAPH
                                    ".json" -> FileExtensions.JSON
                                    ".db" -> FileExtensions.SQL
                                    else -> FileExtensions.GRAPH
                                },
                        ),
                    )
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.background,
                    ),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun PreviewSaveGraphAs() {
    AppTheme {
        SaveGraphAsContent(
            graphName = "Some graph name",
            onDismiss = {},
            onSave = {},
            formats = listOf(".graph", ".json", ".db"),
            dialogWidth = 550.dp,
        )
    }
}
