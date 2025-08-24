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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigRepository
import view.appInterface.button.MainMenuTextButton
import view.appInterface.button.ZoomButtons

@Suppress("ktlint:standard:function-naming")
@Composable
fun LowerRightMenu(
    onZoom: (Float) -> Unit,
    onAddVertex: () -> Unit,
    onAddEdge: () -> Unit,
    onGraphClear: (command: String) -> Unit,
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
                    color = ConfigRepository.states.background.value,
                    shape = borderShape,
                ).border(
                    width = borderWidth,
                    shape = borderShape,
                    color = ConfigRepository.states.borderColor.value,
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
                iconContentDescription = LocalizationManager.states.ui.lowerRightMenuAddEdgeIconDescription.value,
                buttonText = LocalizationManager.states.ui.lowerRightMenuAddVertexButton.value,
                modifier = Modifier.padding(2.dp),
            )
            MainMenuTextButton(
                onClick = onAddEdge,
                iconVector = Icons.Default.Add,
                iconContentDescription = LocalizationManager.states.ui.lowerRightMenuAddEdgeIconDescription.value,
                buttonText = LocalizationManager.states.ui.lowerRightMenuAddEdgeButton.value,
                modifier = Modifier.padding(2.dp),
            )
            MainMenuTextButton(
                onClick = { onGraphClear("graph_clear") },
                iconVector = Icons.Default.Delete,
                iconContentDescription = LocalizationManager.states.ui.lowerRightMenuGraphClearIconDescription.value,
                buttonText = LocalizationManager.states.ui.lowerRightMenuGraphClearButton.value,
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
            onGraphClear = {},
        )
    }
}
