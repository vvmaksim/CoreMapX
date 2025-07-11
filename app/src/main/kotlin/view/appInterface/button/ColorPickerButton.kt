package view.appInterface.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import extensions.border
import extensions.toHexString
import model.fileHandler.DialogManager
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun ColorPickerButton(
    selectedColor: Color,
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.border,
    borderWidth: Dp = 1.dp,
    height: Dp = 56.dp,
    borderShape: CornerBasedShape = MaterialTheme.shapes.medium,
    buttonWidth: Dp = 190.dp,
    colorPreviewSize: Dp = 24.dp,
) {
    Box(
        modifier =
            modifier
                .width(buttonWidth)
                .clip(shape = borderShape)
                .border(
                    border =
                        BorderStroke(
                            width = borderWidth,
                            color = borderColor,
                        ),
                    shape = borderShape,
                ).clickable {
                    DialogManager.showSelectColorDialog(initialColor = selectedColor)?.let { hexColor ->
                        onColorSelected(hexColor)
                    }
                },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(height)
                    .padding(horizontal = 16.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .size(colorPreviewSize)
                        .border(
                            width = borderWidth,
                            color = borderColor,
                            shape = MaterialTheme.shapes.small,
                        ).background(
                            color = selectedColor,
                            shape = MaterialTheme.shapes.small,
                        ),
            )
            Text(
                text = selectedColor.toHexString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 16.dp),
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ColorLens,
                contentDescription = LocalizationManager.states.anyIconDescriptionsStates.colorPickerChooseColor.value,
                modifier = Modifier.padding(start = 8.dp),
                tint = MaterialTheme.colors.primary,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewColorPickerButton() {
    AppTheme {
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    ColorPickerButton(
                        selectedColor = Color.Red,
                        onColorSelected = {},
                        modifier = Modifier,
                    )
                    Spacer(Modifier.height(8.dp))
                    ColorPickerButton(
                        selectedColor = Color.Magenta,
                        onColorSelected = {},
                        modifier = Modifier,
                    )
                    Spacer(Modifier.height(8.dp))
                    ColorPickerButton(
                        selectedColor = Color(0xFF5489ED),
                        onColorSelected = {},
                        modifier = Modifier,
                    )
                    Spacer(Modifier.height(8.dp))
                    ColorPickerButton(
                        selectedColor = Color.Black,
                        onColorSelected = {},
                        modifier = Modifier,
                    )
                }
            },
        )
    }
}
