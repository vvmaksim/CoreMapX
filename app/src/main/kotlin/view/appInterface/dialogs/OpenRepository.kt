package view.appInterface.dialogs

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import extensions.toBooleanOrNull
import extensions.toLong
import org.coremapx.app.theme.AppTheme
import orgcoremapxapp.Graphs
import kotlin.random.Random

@Suppress("ktlint:standard:function-naming")
@Composable
fun OpenRepository(
    onDismiss: () -> Unit,
    graphs: List<Graphs>,
    onGraphSelected: (Long) -> Unit,
    getCountVerticesByGraph: (Long) -> Long,
    getCountEdgesByGraph: (Long) -> Long,
    dialogWidth: Dp = 550.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        OpenRepositoryContent(
            onDismiss = onDismiss,
            graphs = graphs,
            onGraphSelected = onGraphSelected,
            getCountVerticesByGraph = getCountVerticesByGraph,
            getCountEdgesByGraph = getCountEdgesByGraph,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun OpenRepositoryContent(
    onDismiss: () -> Unit,
    graphs: List<Graphs>,
    onGraphSelected: (Long) -> Unit,
    getCountVerticesByGraph: (Long) -> Long,
    getCountEdgesByGraph: (Long) -> Long,
    dialogWidth: Dp = 550.dp,
) {
    var selectedGraphId by remember { mutableStateOf<Long?>(null) }
    val listState = rememberLazyListState()

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
                    .padding(16.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp),
            ) {
                Text(
                    text = "Available Graphs",
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
            if (graphs.isEmpty()) {
                Text(
                    text = "No graphs found in repository",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            } else {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp),
                ) {
                    Row {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.weight(1f),
                        ) {
                            items(graphs) { graph ->
                                Card(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable { selectedGraphId = graph.graph_id }
                                            .then(
                                                if (selectedGraphId == graph.graph_id) {
                                                    Modifier.border(
                                                        width = 2.dp,
                                                        color = MaterialTheme.colors.primary,
                                                        shape = MaterialTheme.shapes.medium,
                                                    )
                                                } else {
                                                    Modifier
                                                },
                                            ),
                                    backgroundColor =
                                        if (selectedGraphId == graph.graph_id) {
                                            MaterialTheme.colors.primary
                                        } else {
                                            MaterialTheme.colors.background
                                        },
                                    shape = MaterialTheme.shapes.medium,
                                ) {
                                    Row(
                                        modifier =
                                            Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Box(
                                            modifier = Modifier.weight(1f),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Text(
                                                    text = graph.name ?: "None",
                                                    style = MaterialTheme.typography.body1,
                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                                )
                                                Text(
                                                    text = "graphId: ${graph.graph_id}",
                                                    style = MaterialTheme.typography.body1,
                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Row(
                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {
                                                    val isDirected = graph.isDirected.toBooleanOrNull() ?: false
                                                    val isWeighted = graph.isWeighted.toBooleanOrNull() ?: false
                                                    Text(
                                                        text = if (isDirected) "Directed" else "Undirected",
                                                        style = MaterialTheme.typography.body2,
                                                        modifier = Modifier.align(Alignment.CenterVertically),
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(
                                                        text = if (isWeighted) "Weighted" else "Unweighted",
                                                        style = MaterialTheme.typography.body2,
                                                        modifier = Modifier.align(Alignment.CenterVertically),
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Row(
                                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {
                                                    val vertexCount = getCountVerticesByGraph(graph.graph_id)
                                                    val edgeCount = getCountEdgesByGraph(graph.graph_id)
                                                    Text(
                                                        text = "Vertices: $vertexCount",
                                                        style = MaterialTheme.typography.body2,
                                                        modifier = Modifier.align(Alignment.CenterVertically),
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(
                                                        text = "Edges: $edgeCount",
                                                        style = MaterialTheme.typography.body2,
                                                        modifier = Modifier.align(Alignment.CenterVertically),
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        VerticalScrollbar(
                            adapter = rememberScrollbarAdapter(listState),
                            modifier =
                                Modifier
                                    .fillMaxHeight()
                                    .width(8.dp)
                                    .align(Alignment.CenterVertically),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    selectedGraphId?.let { onGraphSelected(it) }
                    onDismiss()
                },
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.background,
                    ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedGraphId != null,
            ) {
                Text(
                    text = "Open",
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun PreviewOpenRepositoryWithRandomData() {
    val graphs = mutableListOf<Graphs>()
    for (i in 1..(Random.nextLong(1, 6))) {
        graphs.add(
            Graphs(
                graph_id = i,
                name = "Graph $i",
                author = "Patrick Bateman",
                isDirected = Random.nextBoolean().toLong(),
                isWeighted = Random.nextBoolean().toLong(),
            ),
        )
    }

    AppTheme {
        OpenRepositoryContent(
            onDismiss = {},
            graphs = graphs,
            onGraphSelected = {},
            getCountVerticesByGraph = { Random.nextLong(0, 1000000) },
            getCountEdgesByGraph = { Random.nextLong(0, 1000000) },
            dialogWidth = 550.dp,
        )
    }
}
