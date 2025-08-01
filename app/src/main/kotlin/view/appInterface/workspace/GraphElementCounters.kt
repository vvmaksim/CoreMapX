package view.appInterface.workspace

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun GraphElementCounters(
    vertexCount: Long,
    edgeCount: Long,
    vertexLabel: String = LocalizationManager.states.anyTextStates.vertices.value,
    edgeLabel: String = LocalizationManager.states.anyTextStates.edges.value,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = MaterialTheme.colors.onSurface,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        Text(
            text = "$vertexCount $vertexLabel",
            style = textStyle,
            color = textColor,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        Text(
            text = "$edgeCount $edgeLabel",
            style = textStyle,
            color = textColor,
            modifier = Modifier.padding(bottom = 4.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewCountersWithSomeData() {
    AppTheme {
        PreviewSurface(
            content = {
                GraphElementCounters(
                    vertexCount = 52,
                    edgeCount = 66,
                )
            },
        )
    }
}
