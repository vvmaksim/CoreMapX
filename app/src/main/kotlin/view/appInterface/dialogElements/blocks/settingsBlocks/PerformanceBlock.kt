package view.appInterface.dialogElements.blocks.settingsBlocks

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
import org.coremapx.app.userDirectory.config.ConfigKeys.ANIMATION_DURATION
import org.coremapx.app.userDirectory.config.ConfigKeys.COMMAND_FIELD_SCROLL_DELAY
import view.appInterface.dialogElements.lines.NumberTextFieldLine
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun PerformanceBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val animationDuration by remember { config.states.animationDuration }
    val commandFieldScrollDelay by remember { config.states.commandFieldScrollDelay }

    Column {
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.performanceAnimationDuration.value,
            valueType = Int::class,
            value = TextFieldValue("$animationDuration"),
            onValueChange = { config.setValue(ANIMATION_DURATION, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionAnimationDuration.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.performanceCommandFieldScrollDelay.value,
            valueType = Int::class,
            value = TextFieldValue("$commandFieldScrollDelay"),
            onValueChange = { config.setValue(COMMAND_FIELD_SCROLL_DELAY, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionCommandFieldScrollDelay.value,
                ),
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewPerformanceBlock() {
    AppTheme {
        PreviewSurface(content = { PerformanceBlock() })
    }
}
