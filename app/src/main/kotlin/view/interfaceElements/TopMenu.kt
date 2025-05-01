package view.interfaceElements

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.MainScreenViewModel

@Composable
fun <E: Comparable<E>, V: Comparable<V>> TopMenu(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier
) {
    var actionsExpanded by remember { mutableStateOf(false) }
    var verticesExpanded by remember { mutableStateOf(false) }
    var edgesExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box {
            Button(onClick = { actionsExpanded = true }) {
                Text("Actions")
            }
            DropdownMenu(
                expanded = actionsExpanded,
                onDismissRequest = { actionsExpanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    viewModel.resetGraphView()
                    actionsExpanded = false
                }) {
                    Text("Reset default settings")
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        Box {
            Button(onClick = { verticesExpanded = true }) {
                Text("Vertices")
            }
            DropdownMenu(
                expanded = verticesExpanded,
                onDismissRequest = { verticesExpanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    viewModel.setShowVerticesLabels(!viewModel.showVerticesLabels.value)
                    verticesExpanded = false
                }) {
                    Text(if (viewModel.showVerticesLabels.value) "Hide vertices labels" else "Show vertices labels")
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        Box {
            Button(onClick = { edgesExpanded = true }) {
                Text("Edges")
            }
        }
    }
}
