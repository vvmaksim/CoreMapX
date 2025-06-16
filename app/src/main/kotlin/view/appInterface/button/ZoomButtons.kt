package view.appInterface.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import extensions.border
import extensions.huge

@Suppress("ktlint:standard:function-naming")
@Composable
fun ZoomButtons(
    onZoom: (Float) -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 62.dp,
    zoomFactor: Float = 0.5f,
    borderWidth: Dp = 1.dp,
) {
    Box(
        modifier =
            modifier
                .clip(MaterialTheme.shapes.huge)
                .background(MaterialTheme.colors.background)
                .border(
                    border =
                        BorderStroke(
                            width = borderWidth,
                            color = MaterialTheme.colors.border,
                        ),
                    shape = MaterialTheme.shapes.huge,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Row {
            IconButton(
                onClick = { onZoom(zoomFactor) },
                modifier = Modifier.size(iconSize),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = MaterialTheme.colors.primary,
                )
            }

            IconButton(
                onClick = { onZoom(-zoomFactor) },
                modifier = Modifier.size(iconSize),
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Reduce",
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    }
}
