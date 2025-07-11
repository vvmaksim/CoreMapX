package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun DialogHeader(
    title: String,
    onButtonClick: () -> Unit,
    subtitle: String? = null,
    subtitleStyle: TextStyle = MaterialTheme.typography.body2,
    subtitleAlign: TextAlign = TextAlign.Center,
    subtitleSpacerHeight: Dp = 8.dp,
    titleStyle: TextStyle = MaterialTheme.typography.h5,
    titleAlignment: Alignment = Alignment.Center,
    iconAlignment: Alignment = Alignment.CenterEnd,
    iconImageVector: ImageVector = Icons.Default.Close,
    iconContentDescription: String = LocalizationManager.states.dialogs.dialogHeaderCloseIconDescription.value,
    iconTintColor: Color = MaterialTheme.colors.onSurface,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = titleStyle,
                modifier = Modifier.align(titleAlignment),
            )
            IconButton(
                onClick = onButtonClick,
                modifier = Modifier.align(iconAlignment),
            ) {
                Icon(
                    imageVector = iconImageVector,
                    contentDescription = iconContentDescription,
                    tint = iconTintColor,
                )
            }
        }
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(subtitleSpacerHeight))
            Text(
                text = subtitle,
                style = subtitleStyle,
                textAlign = subtitleAlign,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewDialogHeader() {
    AppTheme {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            PreviewSurface(
                content = {
                    DialogHeader(
                        title = "Some title",
                        onButtonClick = {},
                    )
                },
            )
            Spacer(Modifier.height(8.dp))
            PreviewSurface(
                content = {
                    DialogHeader(
                        title = "Some title",
                        onButtonClick = {},
                        subtitle = "Some subtitle",
                    )
                },
            )
        }
    }
}
