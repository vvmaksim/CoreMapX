package view.appInterface.settingsElements.blocks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.coremapx.app.theme.AppTheme
import view.appInterface.settingsElements.lines.ColorPickLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun SettingsBlock(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = true,
    onClick: (Boolean) -> Unit = {},
) {
    Column(modifier = modifier) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.medium)
                    .clickable { onClick(!isExpanded) }
                    .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = MaterialTheme.colors.primary,
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300)),
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                content()
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewSettingsBlock() {
    AppTheme {
        val content: @Composable () -> Unit = {
            ColorPickLine(
                selectedColor = Color.Black,
                onColorSelected = {},
                title = "Some Color",
                description = "",
            )
        }
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colors.background,
            modifier = Modifier.padding(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                SettingsBlock(
                    title = "Some settings block 1",
                    content = content,
                    isExpanded = false,
                )
                Spacer(Modifier.height(8.dp))
                SettingsBlock(
                    title = "Some settings block 2",
                    content = content,
                    isExpanded = true,
                )
            }
        }
    }
}
