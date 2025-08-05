package view.appInterface.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import extensions.border
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun DropdownSelectButton(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.border,
    borderWidth: Dp = 1.dp,
    height: Dp = 56.dp,
    iconSize: Dp = 24.dp,
    borderShape: CornerBasedShape = MaterialTheme.shapes.medium,
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f)

    Box(
        modifier =
            modifier
                .wrapContentSize()
                .clip(shape = borderShape)
                .border(
                    border = BorderStroke(borderWidth, borderColor),
                    shape = borderShape,
                ).clickable {
                    logDebug("Click on DropdownSelectButton")
                    expanded = !expanded
                },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(height)
                    .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = selectedItem,
                style = MaterialTheme.typography.body1,
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = LocalizationManager.states.anyIconDescriptionsStates.dropdownSelectArrow.value,
                modifier =
                    Modifier
                        .size(iconSize)
                        .rotate(rotation),
                tint = MaterialTheme.colors.primary,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.body1,
                        color = if (item == selectedItem) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                    )
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewDropdownSelectButton() {
    AppTheme {
        val extensions = listOf(".json", ".graph", ".db", ".someExtension")
        PreviewSurface(
            content = {
                DropdownSelectButton(
                    items = extensions,
                    selectedItem = extensions[3],
                    onItemSelected = {},
                    modifier = Modifier.width(500.dp),
                )
            },
        )
    }
}
