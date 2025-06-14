package view.appInterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import view.appInterface.dialogs.GenerateRandomGraph
import viewmodel.MainScreenViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> TopMenu(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier,
) {
    var actionsExpanded by remember { mutableStateOf(false) }
    var verticesExpanded by remember { mutableStateOf(false) }
    var edgesExpanded by remember { mutableStateOf(false) }
    var showGenerateRandomGraphDialog by remember { mutableStateOf(false) }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        Box {
            Button(onClick = { actionsExpanded = true }) {
                Text(
                    text = "Actions",
                    style = MaterialTheme.typography.button,
                )
            }
            DropdownMenu(
                expanded = actionsExpanded,
                onDismissRequest = { actionsExpanded = false },
                modifier = Modifier.background(color = MaterialTheme.colors.background),
            ) {
                DropdownMenuItem(
                    onClick = {
                        viewModel.resetGraphView()
                        actionsExpanded = false
                    },
                ) {
                    Text(
                        text = "Reset default settings",
                        style = MaterialTheme.typography.button,
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        viewModel.resetCanvas()
                        actionsExpanded = false
                    },
                ) {
                    Text(
                        text = "Reset default canvas state",
                        style = MaterialTheme.typography.button,
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        showGenerateRandomGraphDialog = true
                        actionsExpanded = false
                    },
                ) {
                    Text(
                        text = "Generate random graph",
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        Box {
            Button(onClick = { verticesExpanded = true }) {
                Text(
                    text = "Vertices",
                    style = MaterialTheme.typography.button,
                )
            }
            DropdownMenu(
                expanded = verticesExpanded,
                onDismissRequest = { verticesExpanded = false },
                modifier = Modifier.background(color = MaterialTheme.colors.background),
            ) {
                DropdownMenuItem(
                    onClick = {
                        viewModel.setShowVerticesLabels(!viewModel.showVerticesLabels.value)
                        verticesExpanded = false
                    },
                ) {
                    Text(
                        text = if (viewModel.showVerticesLabels.value) "Hide vertices labels" else "Show vertices labels",
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        Box {
            Button(onClick = { edgesExpanded = true }) {
                Text(
                    text = "Edges",
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
    if (showGenerateRandomGraphDialog) {
        GenerateRandomGraph(
            onDismiss = { showGenerateRandomGraphDialog = false },
            viewModel = viewModel,
        )
    }
}
