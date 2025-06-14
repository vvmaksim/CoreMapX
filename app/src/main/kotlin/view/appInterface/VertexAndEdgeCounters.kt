package view.appInterface

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

@Suppress("ktlint:standard:function-naming")
@Composable
fun VertexAndEdgeCounters(
    vertexCount: Long,
    edgeCount: Long,
    vertexLabel: String = "vertices",
    edgeLabel: String = "edges",
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
fun PreviewCountersWithSomeData() {
    MaterialTheme {
        VertexAndEdgeCounters(
            vertexCount = 52,
            edgeCount = 66,
        )
    }
}
