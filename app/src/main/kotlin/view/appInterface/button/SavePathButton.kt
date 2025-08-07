package view.appInterface.button

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
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import extensions.border
import model.fileHandler.DialogManager
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface
import kotlin.text.ifEmpty

@Suppress("ktlint:standard:function-naming")
@Composable
fun SavePathButton(
    selectedPath: String = PrivateConfig.UserDirectory.HOME_DIR_PATH,
    onPathSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.border,
    borderWidth: Dp = 1.dp,
    height: Dp = 56.dp,
    iconSize: Dp = 24.dp,
    borderShape: CornerBasedShape = MaterialTheme.shapes.medium,
) {
    var currentPath by remember { mutableStateOf(selectedPath) }

    Box(
        modifier =
            modifier
                .clip(shape = borderShape)
                .border(
                    border = BorderStroke(borderWidth, borderColor),
                    shape = borderShape,
                ).clickable {
                    logDebug("Click on SavePathButton")
                    val dir = DialogManager.showSelectDirectoryDialog(directory = currentPath) ?: currentPath
                    currentPath = dir
                    onPathSelected(dir)
                },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(height)
                    .padding(horizontal = 16.dp),
        ) {
            Text(
                text = currentPath.ifEmpty { LocalizationManager.states.anyTextStates.savePathSelectDirectory.value },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1,
            )
            Icon(
                imageVector = Icons.Filled.Folder,
                contentDescription = LocalizationManager.states.anyIconDescriptionsStates.savePathOpenDirectoryDialog.value,
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colors.primary,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewSavePathButton() {
    AppTheme {
        PreviewSurface(
            content = {
                SavePathButton(
                    onPathSelected = {},
                    modifier = Modifier.width(450.dp),
                )
            },
        )
    }
}
