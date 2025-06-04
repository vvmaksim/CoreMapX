package view.interfaceElements.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import extensions.toBooleanOrNull
import model.databases.sqlite.createDatabase
import model.databases.sqlite.repositories.EdgeRepository
import model.databases.sqlite.repositories.GraphRepository
import model.databases.sqlite.repositories.VertexRepository
import org.coremapx.app.config
import org.coremapx.graph.GraphDatabase
import java.io.File

@Composable
fun OpenRepository(
    onDismiss: () -> Unit,
    file: File,
    onGraphSelected: (Long) -> Unit
) {
    val dialogBackgroundColor = config.getColor("dialogBackgroundColor")
    val additionalColor = config.getColor("mainMenuButtonTextColor")
    val dialogTextColor = config.getColor("dialogTextColor")
    val dialogWarningTextColor = config.getColor("dialogWarningTextColor")
    val selectedItemColor = config.getColor("selectedItemColor")
    val contentColorButton1 = config.getColor("contentColorButton1")
    val contentColorButton2 = config.getColor("contentColorButton2")

    var selectedGraphId by remember { mutableStateOf<Long?>(null) }

    val database: GraphDatabase = createDatabase(file.absolutePath)
    val graphs = GraphRepository(database).getAllGraphs()

    val listState = rememberLazyListState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = dialogBackgroundColor
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                ) {
                    Text(
                        text = "Available Graphs",
                        fontSize = 20.sp,
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

                Spacer(modifier = Modifier.height(24.dp))

                if (graphs.isEmpty()) {
                    Text(
                        text = "No graphs found in repository",
                        fontSize = 16.sp,
                        color = dialogWarningTextColor,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 600.dp)
                    ) {
                        Row {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier.weight(1f)
                            ) {
                                items(graphs) { graph ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable { selectedGraphId = graph.graph_id }
                                            .then(
                                                if (selectedGraphId == graph.graph_id)
                                                    Modifier.border(2.dp, additionalColor, RoundedCornerShape(8.dp))
                                                else Modifier
                                            ),
                                        backgroundColor = if (selectedGraphId == graph.graph_id) selectedItemColor else dialogBackgroundColor,
                                        shape = RoundedCornerShape(8.dp),
                                        elevation = 0.dp
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Text(
                                                        text = graph.name ?: "None",
                                                        fontSize = 18.sp,
                                                        color = dialogTextColor,
                                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                    Row(
                                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        val isDirected = graph.isDirected.toBooleanOrNull() ?: false
                                                        val isWeighted = graph.isWeighted.toBooleanOrNull() ?: false
                                                        Text(
                                                            text = if (isDirected) "Directed" else "Undirected",
                                                            fontSize = 14.sp,
                                                            color = dialogTextColor,
                                                            modifier = Modifier.align(Alignment.CenterVertically)
                                                        )
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Text(
                                                            text = if (isWeighted) "Weighted" else "Unweighted",
                                                            fontSize = 14.sp,
                                                            color = dialogTextColor,
                                                            modifier = Modifier.align(Alignment.CenterVertically)
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                    Row(
                                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        val vertexCount = VertexRepository(database).getVerticesByGraph(graph.graph_id).size
                                                        val edgeCount = EdgeRepository(database).getEdgesByGraph(graph.graph_id).size
                                                        Text(
                                                            text = "Vertices: $vertexCount",
                                                            fontSize = 14.sp,
                                                            color = dialogTextColor,
                                                            modifier = Modifier.align(Alignment.CenterVertically)
                                                        )
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Text(
                                                            text = "Edges: $edgeCount",
                                                            fontSize = 14.sp,
                                                            color = dialogTextColor,
                                                            modifier = Modifier.align(Alignment.CenterVertically)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            VerticalScrollbar(
                                adapter = rememberScrollbarAdapter(listState),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(8.dp)
                                    .align(Alignment.CenterVertically)
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
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = additionalColor,
                        contentColor = contentColorButton2
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedGraphId != null
                ) {
                    Text(
                        text = "Open",
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}
