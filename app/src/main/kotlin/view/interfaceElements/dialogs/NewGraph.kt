package view.interfaceElements.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp
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
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF666666)
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
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        cursorColor = additionalColor,
                        focusedLabelColor = additionalColor,
                        unfocusedLabelColor = Color(0xFF666666)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                if (showError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Graph name cannot be empty",
                        color = Color(0xFFD32F2F),
                        fontSize = 14.sp,
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
                                uncheckedColor = Color(0xFF666666)
                            )
                        )
                        Text(
                            "Directed Graph",
                            fontSize = 16.sp
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
                                uncheckedColor = Color(0xFF666666)
                            )
                        )
                        Text(
                            "Weighted Graph",
                            fontSize = 16.sp
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
                        .height(48.dp)
                ) {
                    Text(
                        "Create",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
