package view.interfaceElements.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import model.graph.classes.DirectedUnweightedGraph
import model.graph.classes.DirectedWeightedGraph
import model.graph.classes.UndirectedUnweightedGraph
import model.graph.classes.UndirectedWeightedGraph
import model.graph.interfaces.Graph
import org.coremapx.app.config
import viewmodel.MainScreenViewModel

@Composable
fun <E: Comparable<E>, V: Comparable<V>>NewGraph(
    onDismiss: () -> Unit,
    viewModel: MainScreenViewModel<E, V>,
) {
    var graphName by remember { mutableStateOf("") }
    var isWeighted by remember { mutableStateOf(false) }
    var isDirected by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val additionalColor = config.getColor("mainMenuButtonTextColor")
    val dialogBackgroundColor = config.getColor("dialogBackgroundColor")
    val dialogBorderColor = config.getColor("dialogBorderColor")
    val dialogTextColor = config.getColor("dialogTextColor")
    val dialogWarningTextColor = config.getColor("dialogWarningTextColor")
    val contentColorButton1 = config.getColor("contentColorButton1")
    val contentColorButton2 = config.getColor("contentColorButton2")
    val checkboxUncheckedColor = config.getColor("checkboxUncheckedColor")

    val titleSize = 24.sp
    val textSize = 16.sp
    val buttonHeight = 48.dp
    val buttonTextSize = 16.sp

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            backgroundColor = dialogBackgroundColor,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Create New Graph",
                        fontSize = titleSize,
                        fontWeight = FontWeight.Bold,
                        color = dialogTextColor,
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = contentColorButton1,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = graphName,
                    onValueChange = {
                        graphName = it
                        showError = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    label = { Text("Graph Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Name",
                            tint = additionalColor
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = additionalColor,
                        unfocusedBorderColor = dialogBorderColor,
                        cursorColor = additionalColor,
                        focusedLabelColor = additionalColor,
                        unfocusedLabelColor = dialogTextColor,
                        textColor = dialogTextColor,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                if (showError) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Field Graph Name cannot be empty",
                        color = dialogWarningTextColor,
                        fontSize = textSize,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isDirected,
                            onCheckedChange = { isDirected = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = additionalColor,
                                uncheckedColor = checkboxUncheckedColor
                            )
                        )
                        Text(
                            "Directed Graph",
                            fontSize = textSize,
                            color = dialogTextColor,
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isWeighted,
                            onCheckedChange = { isWeighted = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = additionalColor,
                                uncheckedColor = checkboxUncheckedColor
                            )
                        )
                        Text(
                            "Weighted Graph",
                            fontSize = textSize,
                            color = dialogTextColor,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (graphName.isBlank()) {
                            showError = true
                            return@Button
                        }
                        val newGraph: Graph<E, V> =
                            when {
                                isDirected && isWeighted -> DirectedWeightedGraph<V>()
                                isDirected && !isWeighted -> DirectedUnweightedGraph<V>()
                                !isDirected && isWeighted -> UndirectedWeightedGraph<V>()
                                else -> UndirectedUnweightedGraph<V>()
                            } as Graph<E, V>
                        viewModel.graphName = graphName
                        viewModel.graphAuthor = "None"
                        viewModel.graphPath = null
                        viewModel.graphFormat = null
                        viewModel.updateGraph(newGraph)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = additionalColor),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight)
                ) {
                    Text(
                        "Create",
                        color = contentColorButton2,
                        fontSize = buttonTextSize,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
