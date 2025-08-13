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
import view.appInterface.button.ColorPickerButton
import view.appInterface.preview.PreviewSurface
import view.appInterface.dialogElements.description.SettingsDescriptionIconButton
import view.appInterface.dialogElements.description.SettingsDescriptionText

@Suppress("ktlint:standard:function-naming")
@Composable
fun ColorPickLine(
    selectedColor: Color,
    onColorSelected: (String) -> Unit,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.border,
    borderWidth: Dp = 1.dp,
    height: Dp = 56.dp,
    borderShape: CornerBasedShape = MaterialTheme.shapes.medium,
    buttonWidth: Dp = 190.dp,
    colorPreviewSize: Dp = 24.dp,
    isExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(isExpanded) }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(8.dp))
        ColorPickerButton(
            selectedColor = selectedColor,
            onColorSelected = onColorSelected,
            borderColor = borderColor,
            borderWidth = borderWidth,
            height = height,
            borderShape = borderShape,
            buttonWidth = buttonWidth,
            colorPreviewSize = colorPreviewSize,
        )
        SettingsDescriptionIconButton(
            onClick = { expanded = !expanded },
            isExpanded = expanded,
        )
    }
    if (expanded) {
        Spacer(modifier = Modifier.height(8.dp))
        SettingsDescriptionText(description = description)
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewColorPickLine() {
    AppTheme {
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    ColorPickLine(
                        selectedColor = Color.Red,
                        onColorSelected = {},
                        modifier = Modifier.fillMaxWidth(),
                        title = "Some Title 1",
                        description = "",
                    )
                    Spacer(Modifier.height(8.dp))
                    ColorPickLine(
                        selectedColor = Color.Magenta,
                        onColorSelected = {},
                        modifier = Modifier,
                        title = "Some Title 2",
                        description = "",
                    )
                    Spacer(Modifier.height(8.dp))
                    ColorPickLine(
                        selectedColor = Color(0xFF5489ED),
                        onColorSelected = {},
                        modifier = Modifier,
                        title = "Some Title 3",
                        isExpanded = true,
                        description = "Some color description",
                    )
                    Spacer(Modifier.height(8.dp))
                    ColorPickLine(
                        selectedColor = Color.Black,
                        onColorSelected = {},
                        modifier = Modifier,
                        title = "Some Title 4 with so looooooooooooooooooooooooooooooooong text",
                        description = "",
                    )
                }
            },
        )
    }
}
