package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import extensions.border
import model.dto.NewGraphData
import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.contracts.Graph
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.textField.CustomTextField

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
    var graphName by remember { mutableStateOf(TextFieldValue("")) }
    var isWeighted by remember { mutableStateOf(false) }
    var isDirected by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Surface(
        modifier =
            Modifier
                .width(dialogWidth)
                .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DialogHeader(
                title = LocalizationManager.states.dialogs.newGraphTitle.value,
                onButtonClick = onDismiss,
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomTextField(
                value = graphName,
                onValueChange = {
                    graphName = it
                    showError = false
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(LocalizationManager.states.dialogs.newGraphTextFieldPlaceholder.value) },
                isError = showError,
            )

            if (showError) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = LocalizationManager.states.dialogs.newGraphTextFieldError.value,
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
                        text = LocalizationManager.states.dialogs.newGraphIsDirected.value,
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
                        text = LocalizationManager.states.dialogs.newGraphIsWeighted.value,
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (graphName.text.isBlank()) {
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
                            graphName = graphName.text,
                        ),
                    )
                    onDismiss()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = LocalizationManager.states.dialogs.newGraphCreateButton.value,
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewNewGraph() {
    AppTheme {
        NewGraphContent<Long, Long>(
            dialogWidth = 450.dp,
            onDismiss = {},
            onCreate = {},
        )
    }
}
