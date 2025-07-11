package view.appInterface.settingsElements.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigKeys.TITLE_BAR_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.TITLE_BAR_ICON_SIZE
import view.appInterface.preview.PreviewSurface
import view.appInterface.settingsElements.lines.NumberTextFieldLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun TitleBarBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val titleBarHeight by remember { config.states.titleBarHeight }
    val titleBarIconSize by remember { config.states.titleBarIconSize }

    Column {
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.titleBarHeight.value,
            valueType = Int::class,
            value = TextFieldValue("$titleBarHeight"),
            onValueChange = { config.setValue(TITLE_BAR_HEIGHT, it.text) },
            description = LocalizationFormatter.getStringWithLineBreak(
                startString = LocalizationManager.states.descriptions.descriptionTitleBarHeight.value,
            ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.titleBarIconSize.value,
            valueType = Int::class,
            value = TextFieldValue("$titleBarIconSize"),
            onValueChange = { config.setValue(TITLE_BAR_ICON_SIZE, it.text) },
            description = LocalizationFormatter.getStringWithLineBreak(
                startString = LocalizationManager.states.descriptions.descriptionTitleBarIconSize.value,
            ),
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewTitleBarBlock() {
    AppTheme {
        PreviewSurface(content = { TitleBarBlock() })
    }
}
