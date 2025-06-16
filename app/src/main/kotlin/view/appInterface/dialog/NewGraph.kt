package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import extensions.border
import model.dto.NewGraphData
import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.interfaces.Graph
import org.coremapx.app.theme.AppTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> NewGraph(
    onDismiss: () -> Unit,
    onCreate: (NewGraphData<E, V>) -> Unit,
    dialogWidth: Dp = 450.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        NewGraphContent(
            onDismiss = onDismiss,
            onCreate = onCreate,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> NewGraphContent(
    onDismiss: () -> Unit,
    onCreate: (NewGraphData<E, V>) -> Unit,
    dialogWidth: Dp = 450.dp,
) {
    var graphName by remember { mutableStateOf("") }
    var isWeighted by remember { mutableStateOf(false) }
    var isDirected by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Card(
        modifier =
            Modifier
                .width(dialogWidth)
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
                    text = "Create New Graph",
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
                value = graphName,
                onValueChange = {
                    graphName = it
                    showError = false
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                label = { Text("Graph Name") },
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

            if (showError) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Field Graph Name cannot be empty",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
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
                    onCreate(
                        NewGraphData(
                            graph = newGraph,
                            graphName = graphName,
                        ),
                    )
                    onDismiss()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Create",
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun PreviewNewGraph() {
    AppTheme {
        NewGraphContent<Long, Long>(
            dialogWidth = 450.dp,
            onDismiss = {},
            onCreate = {},
        )
    }
}
