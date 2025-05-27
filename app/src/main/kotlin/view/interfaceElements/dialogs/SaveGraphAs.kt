package view.interfaceElements.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import model.fileHandler.FileExtensions
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import view.interfaceElements.buttons.DropdownSelectButton
import view.interfaceElements.buttons.SavePathButton
import viewmodel.MainScreenViewModel

@Composable
fun <E: Comparable<E>, V: Comparable<V>> SaveGraphAs(
    onDismiss: () -> Unit,
    viewModel: MainScreenViewModel<E, V>,
) {
    val formats = listOf(".graph", ".json")
    var selectedFormat by remember { mutableStateOf(formats[0]) }
    var selectedPath by remember { mutableStateOf("$baseUserDirPath/data/graphs") }
    var selectedFileName by remember { mutableStateOf(viewModel.graphName) }
    var showNotification by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val additionalColor = config.getColor("mainMenuButtonTextColor")
    val secondAdditionalColor = Color(0xFFE0E0E0)

    if (showNotification) {
        UserNotification(
            onDismiss = { 
                showNotification = false
                onDismiss()
            },
            title = "Save Notification",
            message = errorMessage
        )
    } else {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF5F5F5)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Save graph",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "Select directory to save and file format",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "File name:",
                            fontSize = 14.sp,
                        )

                        OutlinedTextField(
                            value = selectedFileName,
                            onValueChange = { selectedFileName = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = additionalColor,
                                unfocusedBorderColor = secondAdditionalColor,
                                cursorColor = additionalColor,
                                focusedLabelColor = additionalColor,
                                unfocusedLabelColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true
                        )

                        Text(
                            text = "Save directory:",
                            fontSize = 14.sp,
                        )
                        
                        SavePathButton(
                            selectedPath = selectedPath,
                            onPathSelected = { selectedPath = it },
                        )

                        Text(
                            text = "File format:",
                            fontSize = 14.sp,
                        )

                        DropdownSelectButton(
                            items = formats,
                            selectedItem = selectedFormat,
                            onItemSelected = { selectedFormat = it },
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = secondAdditionalColor,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Cancel", fontSize = 16.sp)
                        }

                        Button(
                            onClick = {
                                val saveResult = viewModel.saveGraph(
                                    fileName = selectedFileName,
                                    directoryPath = selectedPath,
                                    fileFormat =
                                        when (selectedFormat) {
                                            ".graph" -> FileExtensions.GRAPH
                                            ".json" -> FileExtensions.JSON
                                            else -> FileExtensions.GRAPH
                                        }
                                )
                                errorMessage = when (saveResult) {
                                    is Result.Error -> {
                                        "ERROR: ${saveResult.error.type}.${saveResult.error.description}"
                                    }
                                    is Result.Success -> {
                                        "Graph $selectedFileName has been successfully saved to the directory $selectedPath as $selectedFormat"
                                    }
                                }
                                showNotification = true
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = additionalColor,
                                contentColor = secondAdditionalColor,
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Save", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}
