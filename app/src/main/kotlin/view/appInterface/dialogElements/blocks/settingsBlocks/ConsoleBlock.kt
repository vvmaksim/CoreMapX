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
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigKeys.CONSOLE_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.CONSOLE_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.IS_TRANSPARENT_CONSOLE
import org.coremapx.app.userDirectory.config.ConfigKeys.MAX_COUNT_MESSAGES
import org.coremapx.app.userDirectory.config.ConfigKeys.MAX_COUNT_USER_COMMANDS
import org.coremapx.app.userDirectory.config.ConfigRepository
import view.appInterface.dialogElements.lines.NumberTextFieldLine
import view.appInterface.dialogElements.lines.SwitchLine
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun ConsoleBlock(isExpandedSettings: Boolean = ConfigRepository.states.isExpandedSettings.value) {
    val consoleHeight by remember { ConfigRepository.states.consoleHeight }
    val consoleWidth by remember { ConfigRepository.states.consoleWidth }
    val maxCountMessages by remember { ConfigRepository.states.maxCountMessages }
    val maxCountUserCommands by remember { ConfigRepository.states.maxCountUserCommands }
    val isTransparentConsoleBlock by remember { ConfigRepository.states.isTransparentConsoleBlock }

    Column {
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.consoleHeight.value,
            valueType = Int::class,
            value = TextFieldValue("$consoleHeight"),
            onValueChange = { ConfigRepository.setValue(CONSOLE_HEIGHT, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionConsoleHeight.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.consoleWidth.value,
            valueType = Int::class,
            value = TextFieldValue("$consoleWidth"),
            onValueChange = { ConfigRepository.setValue(CONSOLE_WIDTH, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionConsoleWidth.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.consoleMaxCountMessages.value,
            valueType = Int::class,
            value = TextFieldValue("$maxCountMessages"),
            onValueChange = {
                ConfigRepository.setValue(MAX_COUNT_MESSAGES, it.text)
            },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionMaxCountMessages.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.consoleMaxCountUserCommands.value,
            valueType = Int::class,
            value = TextFieldValue("$maxCountUserCommands"),
            onValueChange = {
                ConfigRepository.setValue(MAX_COUNT_USER_COMMANDS, it.text)
            },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionMaxCountUserCommands.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        SwitchLine(
            title = LocalizationManager.states.dialogs.consoleIsTransparent.value,
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionIsTransparentConsole.value,
                ),
            checked = isTransparentConsoleBlock,
            onCheckedChange = { ConfigRepository.setValue(IS_TRANSPARENT_CONSOLE, it.toString()) },
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewConsoleBlock() {
    AppTheme {
        PreviewSurface(content = { ConsoleBlock() })
    }
}
