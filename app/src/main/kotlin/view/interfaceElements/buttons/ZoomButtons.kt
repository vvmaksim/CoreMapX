package view.interfaceElements.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ZoomButtons(
    contentColor: Color,
    onZoom: (Float) -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 62.dp,
    zoomFactor: Float = 0.5f,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column {
            IconButton(
                onClick = { onZoom(zoomFactor) },
                modifier = Modifier.size(iconSize),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = contentColor,
                )
            }

            IconButton(
                onClick = { onZoom(-zoomFactor) },
                modifier = Modifier.size(iconSize),
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Reduce",
                    tint = contentColor,
                )
            }
        }
    }
}
