package view.appInterface.dialogElements.blocks.settingsBlocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigKeys.ANIMATION_DURATION
import org.coremapx.app.userDirectory.config.ConfigRepository
import view.appInterface.dialogElements.lines.NumberTextFieldLine
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun PerformanceBlock(isExpandedSettings: Boolean = ConfigRepository.states.isExpandedSettings.value) {
    val animationDuration by remember { ConfigRepository.states.animationDuration }

    Column {
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.performanceAnimationDuration.value,
            valueType = Int::class,
            value = TextFieldValue("$animationDuration"),
            onValueChange = { ConfigRepository.setValue(ANIMATION_DURATION, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionAnimationDuration.value,
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
