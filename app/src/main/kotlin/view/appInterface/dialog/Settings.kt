package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.dialogElements.blocks.ColorsBlock
import view.appInterface.dialogElements.blocks.CommandFieldBlock
import view.appInterface.dialogElements.blocks.GeneralBlock
import view.appInterface.dialogElements.blocks.MainScreenBlock
import view.appInterface.dialogElements.blocks.PerformanceBlock
import view.appInterface.dialogElements.blocks.SettingsBlock
import view.appInterface.dialogElements.blocks.TitleBarBlock
import view.appInterface.dialogElements.blocks.WorkAreaBlock

@Suppress("ktlint:standard:function-naming")
@Composable
fun Settings(
    onDismiss: () -> Unit,
    dialogWidth: Dp = 800.dp,
    dialogHeight: Dp = 1000.dp,
    isExpandedSettings: Boolean = config.states.isExpandedSettings.value,
) {
    Dialog(onDismissRequest = onDismiss) {
        SettingsContent(
            onDismiss = onDismiss,
            dialogWidth = dialogWidth,
            dialogHeight = dialogHeight,
            isExpandedSettings = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun SettingsContent(
    onDismiss: () -> Unit,
    dialogWidth: Dp = 800.dp,
    dialogHeight: Dp = 1000.dp,
    isExpandedSettings: Boolean = config.states.isExpandedSettings.value,
) {
    var isThemeExpanded by remember { mutableStateOf(isExpandedSettings) }
    var isColorsExpanded by remember { mutableStateOf(isExpandedSettings) }
    var isMainScreenExpanded by remember { mutableStateOf(isExpandedSettings) }
    var isTitleBarExpanded by remember { mutableStateOf(isExpandedSettings) }
    var isCommandFieldExpanded by remember { mutableStateOf(isExpandedSettings) }
    var isWorkAreaExpanded by remember { mutableStateOf(isExpandedSettings) }
    var isPerformanceExpanded by remember { mutableStateOf(isExpandedSettings) }

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
            DialogHeader(
                title = LocalizationManager.states.dialogs.settingsTitle.value,
                onButtonClick = onDismiss,
                subtitle = LocalizationManager.states.dialogs.settingsSubTitle.value,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier =
                    Modifier
                        .heightIn(max = dialogHeight)
                        .verticalScroll(rememberScrollState()),
            ) {
                SettingsBlock(
                    title = LocalizationManager.states.dialogs.settingsGeneralBlockName.value,
                    content = { GeneralBlock(isExpandedSettings) },
                    isExpanded = isThemeExpanded,
                    onClick = { isThemeExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = LocalizationManager.states.dialogs.settingsColorsBlockName.value,
                    content = { ColorsBlock(isExpandedSettings) },
                    isExpanded = isColorsExpanded,
                    onClick = { isColorsExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = LocalizationManager.states.dialogs.settingsMainScreenBlockName.value,
                    content = { MainScreenBlock(isExpandedSettings) },
                    isExpanded = isMainScreenExpanded,
                    onClick = { isMainScreenExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = LocalizationManager.states.dialogs.settingsTitleBarBlockName.value,
                    content = { TitleBarBlock(isExpandedSettings) },
                    isExpanded = isTitleBarExpanded,
                    onClick = { isTitleBarExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = LocalizationManager.states.dialogs.settingsCommandFieldBlockName.value,
                    content = { CommandFieldBlock(isExpandedSettings) },
                    isExpanded = isCommandFieldExpanded,
                    onClick = { isCommandFieldExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = LocalizationManager.states.dialogs.settingsWorkAreaBlockName.value,
                    content = { WorkAreaBlock(isExpandedSettings) },
                    isExpanded = isWorkAreaExpanded,
                    onClick = { isWorkAreaExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsBlock(
                    title = LocalizationManager.states.dialogs.settingsPerformanceBlockName.value,
                    content = { PerformanceBlock(isExpandedSettings) },
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
            dialogWidth = 800.dp,
            dialogHeight = 1000.dp,
        )
    }
}
