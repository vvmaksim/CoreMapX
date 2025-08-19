package view.appInterface.dialogElements.blocks

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
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.dialogElements.lines.ColorPickLine
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun BaseBlock(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = true,
    onClick: (Boolean) -> Unit = {},
) {
    val animationDuration = config.states.animationDuration.value

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
                contentDescription =
                    if (isExpanded) {
                        LocalizationManager.states.anyIconDescriptionsStates.settingsCollapse.value
                    } else {
                        LocalizationManager.states.anyIconDescriptionsStates.settingsExpand.value
                    },
                tint = MaterialTheme.colors.primary,
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(animationSpec = tween(animationDuration)) + expandVertically(animationSpec = tween(animationDuration)),
            exit = fadeOut(animationSpec = tween(animationDuration)) + shrinkVertically(animationSpec = tween(animationDuration)),
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
private fun PreviewBaseBlock() {
    AppTheme {
        val content: @Composable () -> Unit = {
            ColorPickLine(
                selectedColor = Color.Black,
                onColorSelected = {},
                title = "Some Color",
                description = "",
            )
        }
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    BaseBlock(
                        title = "Some settings block 1",
                        content = content,
                        isExpanded = false,
                    )
                    Spacer(Modifier.height(8.dp))
                    BaseBlock(
                        title = "Some settings block 2",
                        content = content,
                        isExpanded = true,
                    )
                }
            },
        )
    }
}
