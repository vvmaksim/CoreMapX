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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigKeys.MAIN_SCREEN_START_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.MAIN_SCREEN_START_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.START_WINDOW_PLACEMENT
import view.appInterface.preview.PreviewSurface
import view.appInterface.settingsElements.lines.DropdownSelectLine
import view.appInterface.settingsElements.lines.NumberTextFieldLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun MainScreenBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val mainScreenStartHeight by remember { config.states.mainScreenStartHeight }
    val mainScreenStartWidth by remember { config.states.mainScreenStartWidth }
    var startWindowPlacement by remember { config.states.startWindowPlacement }

    val dropdownSelectButtonModifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)

    Column {
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.mainScreenStartHeight.value,
            valueType = Int::class,
            value = TextFieldValue("$mainScreenStartHeight"),
            onValueChange = { config.setValue(MAIN_SCREEN_START_HEIGHT, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionMainScreenStartHeight.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.mainScreenStartWidth.value,
            valueType = Int::class,
            value = TextFieldValue("$mainScreenStartWidth"),
            onValueChange = { config.setValue(MAIN_SCREEN_START_WIDTH, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionMainScreenStartWidth.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = LocalizationManager.states.dialogs.mainScreenPlacement.value,
            items = listOf("Maximized", "Floating", "FullScreen"),
            selectedItem = startWindowPlacement,
            onItemSelected = { config.setValue(START_WINDOW_PLACEMENT, it) },
            modifier = dropdownSelectButtonModifier,
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionStartWindowPlacement.value,
                ),
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewMainScreenBlock() {
    AppTheme {
        PreviewSurface(content = { MainScreenBlock() })
    }
}
