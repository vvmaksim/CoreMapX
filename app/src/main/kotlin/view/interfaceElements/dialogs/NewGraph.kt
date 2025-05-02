package view.interfaceElements.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun NewGraph(
    onDismiss: () -> Unit,
    onCreate: (isDirected: Boolean, isWeighted: Boolean) -> Unit,
) {
    var newGraphName by remember { mutableStateOf("") }
    var isWeightedGraph by remember { mutableStateOf(false) }
    var isDirectedGraph by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier =
                Modifier
                    .width(300.dp)
                    .padding(8.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Create New Graph", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = newGraphName,
                    onValueChange = { newGraphName = it },
                    label = { Text("Graph Name") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = isDirectedGraph,
                        onCheckedChange = { isDirectedGraph = it },
                    )
                    Text("Directed", modifier = Modifier.padding(start = 8.dp))
                }
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = isWeightedGraph,
                        onCheckedChange = { isWeightedGraph = it },
                    )
                    Text("Weighted", modifier = Modifier.padding(start = 8.dp))
                }
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(onClick = { onCreate(isDirectedGraph, isWeightedGraph) }) {
                        Text("Create")
                    }
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
