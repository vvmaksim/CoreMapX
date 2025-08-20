package view.appInterface.workspace

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.button.MainMenuTextButton
import view.appInterface.button.ZoomButtons

@Suppress("ktlint:standard:function-naming")
@Composable
fun LowerRightMenu(
    onZoom: (Float) -> Unit,
    onAddVertex: () -> Unit,
    onAddEdge: () -> Unit,
    modifier: Modifier = Modifier,
    menuWidth: Dp = 188.dp,
    borderWidth: Dp = 1.dp,
    borderShape: Shape = MaterialTheme.shapes.medium,
) {
    Box(
        modifier =
            modifier
                .width(menuWidth)
                .clip(borderShape)
                .background(
                    color = config.states.background.value,
                    shape = borderShape,
                ).border(
                    width = borderWidth,
                    shape = borderShape,
                    color = config.states.borderColor.value,
                ),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MainMenuTextButton(
                onClick = onAddVertex,
                iconVector = Icons.Default.Add,
                iconContentDescription = "Add Vertex Icon",
                buttonText = "Add Vertex",
                modifier = Modifier.padding(2.dp),
            )
            MainMenuTextButton(
                onClick = onAddEdge,
                iconVector = Icons.Default.Add,
                iconContentDescription = "Add Edge Icon",
                buttonText = "Add Edge",
                modifier = Modifier.padding(2.dp),
            )
            ZoomButtons(
                onZoom = onZoom,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewLowerRightMenu() {
    AppTheme {
        LowerRightMenu(
            onZoom = {},
            onAddVertex = {},
            onAddEdge = {},
        )
    }
}
