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
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
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
            title = LocalizationManager.states.dialogs.generalLanguage.value,
            items = listOf("English", "Русский", "Custom"),
            selectedItem =
                when (language) {
                    "en" -> "English"
                    "ru" -> "Русский"
                    "custom" -> "Custom"
                    else -> "English"
                },
            onItemSelected = { selectedLanguage: String ->
                config.setValue(
                    key = LANGUAGE,
                    value =
                        when (selectedLanguage) {
                            "English" -> "en"
                            "Русский" -> "ru"
                            "Custom" -> "custom"
                            else -> "English"
                        },
                )
            },
            modifier = dropdownSelectButtonModifier,
            description = LocalizationManager.states.descriptions.descriptionLanguage.value,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = LocalizationManager.states.dialogs.generalTheme.value,
            items = listOf("Light", "Dark", "Custom"),
            selectedItem = theme.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() },
            onItemSelected = {
                config.setValue(THEME, it.lowercase())
                config.updateTheme()
            },
            modifier = dropdownSelectButtonModifier,
            description = LocalizationManager.states.descriptions.descriptionTheme.value,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = LocalizationManager.states.dialogs.generalSystemDialogTheme.value,
            items = listOf("Light", "Dark"),
            selectedItem = systemDialogTheme.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() },
            onItemSelected = {
                config.setValue(SYSTEM_DIALOG_THEME, it.lowercase())
                config.setThemeOnCustom()
            },
            modifier = dropdownSelectButtonModifier,
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionSystemDialogTheme.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        SwitchLine(
            title = LocalizationManager.states.dialogs.generalExpanded.value,
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionIsExpandedSettings.value,
                ),
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
