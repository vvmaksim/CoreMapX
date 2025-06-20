package view.appInterface.settingsElements.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigDescriptions
import org.coremapx.app.userDirectory.config.ConfigKeys.IS_EXPANDED_SETTINGS
import org.coremapx.app.userDirectory.config.ConfigKeys.LANGUAGE
import org.coremapx.app.userDirectory.config.ConfigKeys.SYSTEM_DIALOG_THEME
import org.coremapx.app.userDirectory.config.ConfigKeys.THEME
import view.appInterface.preview.PreviewSurface
import view.appInterface.settingsElements.lines.DropdownSelectLine
import view.appInterface.settingsElements.lines.SwitchLine
import java.util.Locale.getDefault

@Suppress("ktlint:standard:function-naming")
@Composable
fun GeneralBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val language by remember { config.states.language }
    val theme by remember { config.states.theme }
    val systemDialogTheme by remember { config.states.systemDialogTheme }

    val dropdownSelectButtonModifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    Column {
        DropdownSelectLine(
            title = "Language",
            items = listOf("English", "Русский"),
            selectedItem = if (language == "en") "English" else "Русский",
            onItemSelected = {
                config.setValue(LANGUAGE, if (it == "English") "en" else "ru")
            },
            modifier = dropdownSelectButtonModifier,
            description = ConfigDescriptions.LANGUAGE,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = "Theme",
            items = listOf("Light", "Dark", "Custom"),
            selectedItem = theme.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() },
            onItemSelected = {
                config.setValue(THEME, it.lowercase())
                config.updateTheme()
            },
            modifier = dropdownSelectButtonModifier,
            description = ConfigDescriptions.THEME,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = "System dialog theme",
            items = listOf("Light", "Dark"),
            selectedItem = systemDialogTheme.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() },
            onItemSelected = {
                config.setValue(SYSTEM_DIALOG_THEME, it.lowercase())
                config.setThemeOnCustom()
            },
            modifier = dropdownSelectButtonModifier,
            description = ConfigDescriptions.SYSTEM_DIALOG_THEME,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        SwitchLine(
            title = "Settings blocks and descriptions is expanded",
            description = ConfigDescriptions.IS_EXPANDED_SETTINGS,
            checked = isExpandedSettings,
            onCheckedChange = { config.setValue(IS_EXPANDED_SETTINGS, it.toString()) },
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewGeneralBlock() {
    AppTheme {
        PreviewSurface(content = { GeneralBlock() })
    }
}
