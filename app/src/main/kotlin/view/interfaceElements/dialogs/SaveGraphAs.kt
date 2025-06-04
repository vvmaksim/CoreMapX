package view.interfaceElements.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import model.fileHandler.FileExtensions
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import view.interfaceElements.buttons.DropdownSelectButton
import view.interfaceElements.buttons.SavePathButton
import viewmodel.MainScreenViewModel
import java.io.File

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
    var errorMessage by remember { mutableStateOf("") }
    val additionalColor = config.getColor("mainMenuButtonTextColor")
    val dialogBackgroundColor = config.getColor("dialogBackgroundColor")
    val dialogBorderColor = config.getColor("dialogBorderColor")
    val dialogTextColor = config.getColor("dialogTextColor")
    val dialogWarningTextColor = config.getColor("dialogWarningTextColor")
    val contentColorButton1 = config.getColor("contentColorButton1")
    val contentColorButton2 = config.getColor("contentColorButton2")

    val titleSize = 24.sp
    val hintsSize = 14.sp
    val buttonTextSize = 16.sp

    if (showNotification) {
        UserNotification(
            onDismiss = {
                showNotification = false
                onDismiss()
            },
            title = "Save Notification",
            message = errorMessage,
        )
    } else {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                color = dialogBackgroundColor,
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
                            fontSize = titleSize,
                            fontWeight = FontWeight.Bold,
                            color = dialogTextColor,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = contentColorButton1,
                            )
                        }
                    }

                    Text(
                        text = "Select directory to save and file format",
                        fontSize = hintsSize,
                        textAlign = TextAlign.Center,
                        color = dialogTextColor,
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = "File name:",
                            fontSize = hintsSize,
                            color = dialogTextColor,
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
                                    tint = additionalColor
                                )
                            },
                            colors =
                                TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = additionalColor,
                                    unfocusedBorderColor = dialogBorderColor,
                                    cursorColor = additionalColor,
                                    focusedLabelColor = additionalColor,
                                    unfocusedLabelColor = dialogTextColor,
                                    textColor = dialogTextColor,
                                ),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                        )

                        Text(
                            text = "Save directory:",
                            fontSize = hintsSize,
                            color = dialogTextColor,
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
                            fontSize = hintsSize,
                            color = dialogTextColor,
                        )

                        DropdownSelectButton(
                            items = formats,
                            selectedItem = selectedFormat,
                            onItemSelected = {
                                selectedFormat = it
                                showError = File("$selectedPath/$selectedFileName$selectedFormat").exists()
                            },
                            additionalColor = additionalColor,
                            borderColor = dialogBorderColor,
                            backgroundColor = dialogBackgroundColor,
                            textColor = dialogTextColor,
                        )
                    }

                    if (showError) {
                        Text(
                            text = "A file with this configuration already exists, and its contents will be replaced when saved",
                            color = dialogWarningTextColor,
                            fontSize = hintsSize,
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
                            errorMessage =
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
                                backgroundColor = additionalColor,
                                contentColor = contentColorButton2,
                            ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(text = "Save", fontSize = buttonTextSize)
                    }
                }
            }
        }
    }
}
