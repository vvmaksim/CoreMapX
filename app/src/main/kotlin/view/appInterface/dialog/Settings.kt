package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.coremapx.app.theme.AppTheme
import view.appInterface.settingsElements.blocks.ColorsBlock
import view.appInterface.settingsElements.blocks.CommandFieldBlock
import view.appInterface.settingsElements.blocks.GeneralBlock
import view.appInterface.settingsElements.blocks.MainScreenBlock
import view.appInterface.settingsElements.blocks.PerformanceBlock
import view.appInterface.settingsElements.blocks.SettingsBlock
import view.appInterface.settingsElements.blocks.TitleBarBlock
import view.appInterface.settingsElements.blocks.WorkAreaBlock

@Suppress("ktlint:standard:function-naming")
@Composable
fun Settings(
    onDismiss: () -> Unit,
    dialogWidth: Dp = 700.dp,
    dialogHeight: Dp = 1000.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        SettingsContent(
            onDismiss = onDismiss,
            dialogWidth = dialogWidth,
            dialogHeight = dialogHeight,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SettingsContent(
    onDismiss: () -> Unit,
    dialogWidth: Dp = 700.dp,
    dialogHeight: Dp = 1000.dp,
) {
    var isThemeExpanded by remember { mutableStateOf(false) }
    var isColorsExpanded by remember { mutableStateOf(false) }
    var isMainScreenExpanded by remember { mutableStateOf(false) }
    var isTitleBarExpanded by remember { mutableStateOf(false) }
    var isCommandFieldExpanded by remember { mutableStateOf(false) }
    var isWorkAreaExpanded by remember { mutableStateOf(false) }
    var isPerformanceExpanded by remember { mutableStateOf(false) }

    Surface(
        modifier =
            Modifier
                .width(dialogWidth)
                .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.Center),
                )
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colors.onSurface,
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier =
                    Modifier
                        .heightIn(max = dialogHeight)
                        .verticalScroll(rememberScrollState()),
            ) {
                SettingsBlock(
                    title = "General",
                    content = { GeneralBlock() },
                    isExpanded = isThemeExpanded,
                    onClick = { isThemeExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = "Colors",
                    content = { ColorsBlock() },
                    isExpanded = isColorsExpanded,
                    onClick = { isColorsExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = "Main Screen",
                    content = { MainScreenBlock() },
                    isExpanded = isMainScreenExpanded,
                    onClick = { isMainScreenExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = "Title Bar",
                    content = { TitleBarBlock() },
                    isExpanded = isTitleBarExpanded,
                    onClick = { isTitleBarExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = "Command Field",
                    content = { CommandFieldBlock() },
                    isExpanded = isCommandFieldExpanded,
                    onClick = { isCommandFieldExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = "Work Area",
                    content = { WorkAreaBlock() },
                    isExpanded = isWorkAreaExpanded,
                    onClick = { isWorkAreaExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = "Performance",
                    content = { PerformanceBlock() },
                    isExpanded = isPerformanceExpanded,
                    onClick = { isPerformanceExpanded = it },
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewSettings() {
    AppTheme {
        SettingsContent(
            onDismiss = {},
            dialogWidth = 700.dp,
            dialogHeight = 1000.dp,
        )
    }
}
