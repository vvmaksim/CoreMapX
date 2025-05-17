package view.interfaceElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config

@Composable
fun TitleBar(
    onClose: () -> Unit,
    onMinimize: () -> Unit,
    onMaximize: () -> Unit,
    isMaximized: Boolean,
) {
    val titleBarColor = config.getColor("titleBarColor")
    val titleBarIconTintColor = config.getColor("titleBarIconTintColor")
    val titleBarHeight = (config.getIntValue("titleBarHeight") ?: 0).dp
    val titleBarIconSize = (config.getIntValue("titleBarIconSize") ?: 0).dp

    var showMenuButtons by remember { mutableStateOf(false) }
    var showFileMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .background(titleBarColor)
            .fillMaxWidth()
            .height(titleBarHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showMenuButtons) {
                IconButton(onClick = { showMenuButtons = false }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Title Bar Menu",
                        tint = titleBarIconTintColor,
                        modifier = Modifier.size(titleBarIconSize)
                    )
                }

                TextButton(onClick = { showFileMenu = true }) {
                    Text(text = "File", color = titleBarIconTintColor)
                }
                DropdownMenu(
                    expanded = showFileMenu,
                    onDismissRequest = { showFileMenu = false },
                ) {
                    DropdownMenuItem(onClick = { showFileMenu = false }) {
                        Text("New")
                    }
                    DropdownMenuItem(onClick = { showFileMenu = false }) {
                        Text("Open")
                    }
                    DropdownMenuItem(onClick = { showFileMenu = false }) {
                        Text("Save")
                    }
                    DropdownMenuItem(onClick = { showFileMenu = false }) {
                        Text("Save as..")
                    }
                }
                TextButton(onClick = { }) {
                    Text(text = "Settings", color = titleBarIconTintColor)
                }
                TextButton(onClick = { }) {
                    Text(text = "Help", color = titleBarIconTintColor)
                }
            } else {
                IconButton(onClick = { showMenuButtons = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Title Bar Menu",
                        tint = titleBarIconTintColor,
                        modifier = Modifier.size(titleBarIconSize)
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(end = 4.dp)) {
            IconButton(onClick = onMinimize) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Minimize",
                    tint = titleBarIconTintColor,
                    modifier = Modifier.size(titleBarIconSize)
                )
            }
            IconButton(onClick = onMaximize) {
                Icon(
                    imageVector = if (isMaximized) Icons.Filled.FullscreenExit else Icons.Filled.Fullscreen,
                    contentDescription = if (isMaximized) "Recover" else "Maximize",
                    tint = titleBarIconTintColor,
                    modifier = Modifier.size(titleBarIconSize)
                )
            }
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = titleBarIconTintColor,
                    modifier = Modifier.size(titleBarIconSize)
                )
            }
        }
    }
}
