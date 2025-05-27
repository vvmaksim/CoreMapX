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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.coremapx.app.config
import java.awt.FileDialog
import java.awt.Frame
import kotlin.text.ifEmpty

@Composable
fun SavePathButton(
    selectedPath: String = System.getProperty("user.home"),
    onPathSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentPath by remember { mutableStateOf(selectedPath) }
    val mainColor = config.getColor("mainMenuColor")
    val additionalColor = config.getColor("mainMenuButtonTextColor")

    Box(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, mainColor),
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable {
                val dialog = FileDialog(null as Frame?, "Select Directory", FileDialog.LOAD)
                dialog.directory = currentPath.ifEmpty { System.getProperty("user.home") }
                dialog.isVisible = true
                val dir = dialog.directory
                if (dir != null) {
                    currentPath = dir
                    onPathSelected(dir)
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = currentPath.ifEmpty { "Select directory" },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = Icons.Filled.Folder,
                contentDescription = "Open Directory Dialog",
                modifier = Modifier.size(24.dp),
                tint = additionalColor
            )
        }
    }
}
