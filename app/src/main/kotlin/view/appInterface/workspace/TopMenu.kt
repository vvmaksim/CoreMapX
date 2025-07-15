package view.appInterface.workspace

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
import org.coremapx.app.localization.LocalizationManager
import view.appInterface.dialog.FindPath
import view.appInterface.dialog.GenerateRandomGraph
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
    var showFindPathDialog by remember { mutableStateOf(false) }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        Box {
            Button(onClick = { actionsExpanded = true }) {
                Text(
                    text = LocalizationManager.states.ui.topMenuActions.value,
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
                        text = LocalizationManager.states.ui.topMenuDrawGraphAgain.value,
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
                        text = LocalizationManager.states.ui.topMenuResetDefaultCanvasState.value,
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
                        text = LocalizationManager.states.ui.topMenuGenerateRandomGraph.value,
                        style = MaterialTheme.typography.button,
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        showFindPathDialog = true
                        actionsExpanded = false
                    },
                ) {
                    Text(
                        text = LocalizationManager.states.ui.topMenuFindPath.value,
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        Box {
            Button(onClick = { verticesExpanded = true }) {
                Text(
                    text = LocalizationManager.states.ui.topMenuVertices.value,
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
                        text =
                            if (viewModel.showVerticesLabels.value) {
                                LocalizationManager.states.ui.topMenuHideVerticesLabels.value
                            } else {
                                LocalizationManager.states.ui.topMenuShowVerticesLabels.value
                            },
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        Box {
            Button(onClick = { edgesExpanded = true }) {
                Text(
                    text = LocalizationManager.states.ui.topMenuEdges.value,
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
    if (showGenerateRandomGraphDialog) {
        GenerateRandomGraph(
            onDismiss = { showGenerateRandomGraphDialog = false },
            onGraphUpdate = { newGraph ->
                viewModel.updateGraph(newGraph)
            },
        )
    }
    if (showFindPathDialog) {
        FindPath(
            viewModel = viewModel,
            onDismiss = { showFindPathDialog = false },
        )
    }
}
