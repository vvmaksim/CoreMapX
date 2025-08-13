package view.appInterface.dialogElements.lines

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import extensions.border
import org.coremapx.app.theme.AppTheme
import view.appInterface.button.DropdownSelectButton
import view.appInterface.dialogElements.description.DescriptionIconButton
import view.appInterface.dialogElements.description.DescriptionText
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun DropdownSelectLine(
    title: String,
    description: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.border,
    borderWidth: Dp = 1.dp,
    height: Dp = 56.dp,
    iconSize: Dp = 24.dp,
    borderShape: CornerBasedShape = MaterialTheme.shapes.medium,
    buttonWidth: Dp = 190.dp,
    isExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(isExpanded) }

    Column {
        Row(
            modifier =
                modifier
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(8.dp))
            DropdownSelectButton(
                items = items,
                selectedItem = selectedItem,
                onItemSelected = onItemSelected,
                modifier = Modifier.width(buttonWidth),
                borderColor = borderColor,
                borderWidth = borderWidth,
                height = height,
                iconSize = iconSize,
                borderShape = borderShape,
            )
            DescriptionIconButton(
                onClick = { expanded = !expanded },
                isExpanded = expanded,
            )
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            DescriptionText(description = description)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewDropdownSelectLine() {
    AppTheme {
        val items = listOf("Item 1", "Item 2")
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    DropdownSelectLine(
                        title = "Some Title 1",
                        items = items,
                        selectedItem = items[0],
                        onItemSelected = {},
                        description = "",
                    )
                    Spacer(Modifier.height(8.dp))
                    DropdownSelectLine(
                        title = "Some Title 2",
                        items = items,
                        selectedItem = items[1],
                        onItemSelected = {},
                        buttonWidth = 250.dp,
                        description = "Some Description",
                        isExpanded = true,
                    )
                }
            },
        )
    }
}
