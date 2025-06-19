package view.appInterface.settingsElements.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
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
                config.setValue("language", if (it == "English") "en" else "ru")
            },
            modifier = dropdownSelectButtonModifier,
            description =
                """
                You can choose one of the suggested languages for the interface or use a custom localization file.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = "Theme",
            items = listOf("Light", "Dark", "Custom"),
            selectedItem = theme.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() },
            onItemSelected = {
                config.setValue("theme", it.lowercase())
                config.updateTheme()
            },
            modifier = dropdownSelectButtonModifier,
            description =
                """
                You can change the interface colors in the color settings. Changing any of the colors will switch the theme to the `Custom` status.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = "System dialog theme",
            items = listOf("Light", "Dark"),
            selectedItem = systemDialogTheme.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() },
            onItemSelected = {
                config.setValue("systemDialogTheme", it.lowercase())
                config.setThemeOnCustom()
            },
            modifier = dropdownSelectButtonModifier,
            description =
                """
                To select a graph file to open, a window with the appropriate interface appears.
                You can change the theme of this window to one that would better suit your theme.
                When switching between ready-made themes, this parameter switches automatically.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        SwitchLine(
            title = "Settings blocks and descriptions is expanded",
            description =
                """
                If this option is active, the next time you open the settings menu, all settings blocks will be expanded and all descriptions in the settings will be shown.
                
                If the parameter is disabled, then the next time you open the settings menu, all settings blocks and all descriptions are hidden.
                """.trimIndent(),
            checked = isExpandedSettings,
            onCheckedChange = { config.setValue("isExpandedSettings", it.toString()) },
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewGeneralBlock() {
    AppTheme {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            GeneralBlock()
        }
    }
}
