package view.appInterface.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import extensions.border
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.graph.classes.DirectedUnweightedGraph
import model.graph.classes.DirectedWeightedGraph
import model.graph.classes.UndirectedUnweightedGraph
import model.graph.classes.UndirectedWeightedGraph
import model.graph.dataClasses.UnweightedEdge
import model.graph.dataClasses.Vertex
import model.graph.dataClasses.WeightedEdge
import model.graph.interfaces.Edge
import model.graph.interfaces.Graph
import viewmodel.MainScreenViewModel
import kotlin.random.Random

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> GenerateRandomGraph(
    onDismiss: () -> Unit,
    viewModel: MainScreenViewModel<E, V>,
) {
    var verticesCount by remember { mutableStateOf("") }
    var edgesCount by remember { mutableStateOf("") }
    var showVerticesCountError by remember { mutableStateOf(false) }
    var showEdgesCountError by remember { mutableStateOf(false) }
    var isDirected by remember { mutableStateOf(false) }
    var isWeighted by remember { mutableStateOf(false) }

    var isGenerating by remember { mutableStateOf(false) }
    var generationProgress by remember { mutableStateOf(0f) }
    var isVisualizing by remember { mutableStateOf(false) }

    fun isIncorrectParameter(param: String): Boolean = param.isEmpty() || param.toLongOrNull() == null || param.toLong() <= 0L

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier =
                Modifier
                    .width(450.dp)
                    .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            backgroundColor = MaterialTheme.colors.background,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Random Graph",
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
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = verticesCount,
                    onValueChange = {
                        verticesCount = it
                        showVerticesCountError = isIncorrectParameter(it)
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                    label = { Text("Vertices Count") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Vertices Count",
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

                if (showVerticesCountError) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Vertices Count must be a positive integer",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = edgesCount,
                    onValueChange = {
                        edgesCount = it
                        showEdgesCountError = isIncorrectParameter(it)
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                    label = { Text("Edges Count") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edges Count",
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

                if (showEdgesCountError) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Edges Count must be a positive integer",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = isDirected,
                            onCheckedChange = { isDirected = it },
                            colors =
                                CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colors.primary,
                                    uncheckedColor = MaterialTheme.colors.border,
                                ),
                        )
                        Text(
                            text = "Directed Graph",
                            style = MaterialTheme.typography.body1,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = isWeighted,
                            onCheckedChange = { isWeighted = it },
                            colors =
                                CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colors.primary,
                                    uncheckedColor = MaterialTheme.colors.border,
                                ),
                        )
                        Text(
                            text = "Weighted Graph",
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
                if (isGenerating) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        LinearProgressIndicator(
                            progress = generationProgress,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Generating graph: ${(generationProgress * 100).toInt()}%",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                } else if (isVisualizing) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = Icons.Default.HourglassEmpty,
                            contentDescription = "Visualizing",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.width(48.dp),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Visualizing graph...",
                            style = MaterialTheme.typography.body2,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "This may take a few minutes",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (isIncorrectParameter(verticesCount)) showVerticesCountError = true
                        if (isIncorrectParameter(edgesCount)) showEdgesCountError = true

                        if (!showVerticesCountError && !showEdgesCountError) {
                            val newGraph: Graph<E, V> =
                                when {
                                    isDirected && isWeighted -> DirectedWeightedGraph<V>()
                                    isDirected && !isWeighted -> DirectedUnweightedGraph<V>()
                                    !isDirected && isWeighted -> UndirectedWeightedGraph<V>()
                                    else -> UndirectedUnweightedGraph<V>()
                                } as Graph<E, V>

                            val actualVerticesCount = verticesCount.toLong()
                            val actualEdgesCount = edgesCount.toLong()

                            isGenerating = true
                            generationProgress = 0f
                            isVisualizing = false

                            CoroutineScope(Dispatchers.Default).launch {
                                for (i in 0L until actualVerticesCount) {
                                    newGraph.addVertex(Vertex(i, i.toString()) as Vertex<V>)
                                    generationProgress = i.toFloat() / actualVerticesCount * 0.5f
                                }

                                for (i in 0L until actualEdgesCount) {
                                    val fromId = Random.nextInt(actualVerticesCount.toInt())
                                    val toId = Random.nextInt(actualVerticesCount.toInt())
                                    if (newGraph is UndirectedUnweightedGraph || newGraph is DirectedUnweightedGraph) {
                                        newGraph.addEdge(
                                            UnweightedEdge(
                                                id = i,
                                                from = Vertex(fromId.toLong(), fromId.toString()) as Vertex<V>,
                                                to = Vertex(toId.toLong(), toId.toString()) as Vertex<V>,
                                            ) as Edge<E, V>,
                                        )
                                    } else {
                                        newGraph.addEdge(
                                            WeightedEdge(
                                                id = i,
                                                from = Vertex(fromId.toLong(), fromId.toString()) as Vertex<V>,
                                                to = Vertex(toId.toLong(), toId.toString()) as Vertex<V>,
                                                weight = Random.nextLong(1, 100),
                                            ) as Edge<E, V>,
                                        )
                                    }
                                    generationProgress = 0.5f + i.toFloat() / actualEdgesCount * 0.5f
                                }

                                isGenerating = false
                                isVisualizing = true

                                CoroutineScope(Dispatchers.Main).launch {
                                    kotlinx.coroutines.delay(1)
                                    viewModel.updateGraph(newGraph)
                                    isVisualizing = false
                                    onDismiss()
                                }
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isGenerating && !isVisualizing,
                ) {
                    Text(
                        text = "Generate",
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
    }
}
