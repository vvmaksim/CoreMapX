package view.interfaceElements.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import extensions.border
import model.fileHandler.FileExtensions
import model.result.Result
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import view.interfaceElements.buttons.DropdownSelectButton
import view.interfaceElements.buttons.SavePathButton
import viewmodel.MainScreenViewModel
import java.io.File

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> SaveGraphAs(
    onDismiss: () -> Unit,
    viewModel: MainScreenViewModel<E, V>,
) {
    val formats = listOf(".graph", ".json", ".db")
    var selectedFormat by remember { mutableStateOf(formats[0]) }
    var selectedPath by remember { mutableStateOf("$baseUserDirPath/data/graphs") }
    var selectedFileName by remember { mutableStateOf(viewModel.graphName) }
    var showNotification by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    if (showNotification) {
        UserNotification(
            onDismiss = {
                showNotification = false
                onDismiss()
            },
            title = "Save Notification",
            message = message,
        )
    } else {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colors.background,
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
                            val saveResult =
                                viewModel.saveGraph(
                                    fileName = selectedFileName,
                                    directoryPath = selectedPath,
                                    fileFormat =
                                        when (selectedFormat) {
                                            ".graph" -> FileExtensions.GRAPH
                                            ".json" -> FileExtensions.JSON
                                            ".db" -> FileExtensions.SQL
                                            else -> FileExtensions.GRAPH
                                        },
                                )
                            message =
                                when (saveResult) {
                                    is Result.Error -> {
                                        "ERROR: ${saveResult.error.type}.${saveResult.error.description}"
                                    }
                                    is Result.Success -> {
                                        "Graph $selectedFileName has been successfully saved to the directory $selectedPath as $selectedFormat"
                                    }
                                }
                            showNotification = true
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
    }
}
