package view.interfaceElements.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.fileHandler.FileDialogManager
import org.coremapx.app.config
import kotlin.text.ifEmpty

@Composable
fun SavePathButton(
    selectedPath: String = System.getProperty("user.home"),
    onPathSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    additionalColor: Color = config.getColor("mainMenuButtonTextColor"),
    borderColor: Color = config.getColor("dialogBorderColor"),
    borderWidth: Dp = 1.dp,
    backgroundColor: Color = config.getColor("dialogBackgroundColor"),
    textColor: Color = config.getColor("dialogTextColor"),
    fontSize: TextUnit = 16.sp,
    height: Dp = 56.dp,
    iconSize: Dp = 24.dp,
    roundedCornerShapeSize: Dp = 8.dp,
) {
    var currentPath by remember { mutableStateOf(selectedPath) }

    Box(
        modifier =
            modifier
                .clip(shape = RoundedCornerShape(roundedCornerShapeSize))
                .border(
                    border = BorderStroke(borderWidth, borderColor),
                    shape = RoundedCornerShape(roundedCornerShapeSize),
                ).background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(roundedCornerShapeSize)
                )
                .clickable {
                    val dir = FileDialogManager.showSelectDirectoryDialog(directory = currentPath) ?: currentPath
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
                text = currentPath.ifEmpty { "Select directory" },
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                fontSize = fontSize,
                fontWeight = FontWeight.Medium,
            )
            Icon(
                imageVector = Icons.Filled.Folder,
                contentDescription = "Open Directory Dialog",
                modifier = Modifier.size(iconSize),
                tint = additionalColor,
            )
        }
    }
}
