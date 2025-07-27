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
import org.coremapx.app.userDirectory.config.ConfigKeys.COMMAND_FIELD_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.IS_TRANSPARENT_COMMAND_LINE
import org.coremapx.app.userDirectory.config.ConfigKeys.MAX_COUNT_MESSAGES
import org.coremapx.app.userDirectory.config.ConfigKeys.MAX_COUNT_USER_COMMANDS
import org.coremapx.app.userDirectory.config.ConfigKeys.MESSAGE_OUTPUT_HEIGHT
import view.appInterface.preview.PreviewSurface
import view.appInterface.settingsElements.lines.NumberTextFieldLine
import view.appInterface.settingsElements.lines.SwitchLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun CommandFieldBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val messageOutputHeight by remember { config.states.messageOutputHeight }
    val maxCountMessages by remember { config.states.maxCountMessages }
    val maxCountUserCommands by remember { config.states.maxCountUserCommands }
    val commandFieldWidth by remember { config.states.commandFieldWidth }
    val isTransparentCommandLine by remember { config.states.isTransparentCommandLine }

    Column {
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.commandFieldMessageOutputHeight.value,
            valueType = Int::class,
            value = TextFieldValue("$messageOutputHeight"),
            onValueChange = { config.setValue(MESSAGE_OUTPUT_HEIGHT, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionMessageOutputHeight.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.commandFieldMaxCountMessages.value,
            valueType = Int::class,
            value = TextFieldValue("$maxCountMessages"),
            onValueChange = {
                config.setValue(MAX_COUNT_MESSAGES, it.text)
            },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionMaxCountMessages.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.commandFieldMaxCountUserCommands.value,
            valueType = Int::class,
            value = TextFieldValue("$maxCountUserCommands"),
            onValueChange = {
                config.setValue(MAX_COUNT_USER_COMMANDS, it.text)
            },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionMaxCountUserCommands.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.commandFieldWidth.value,
            valueType = Int::class,
            value = TextFieldValue("$commandFieldWidth"),
            onValueChange = { config.setValue(COMMAND_FIELD_WIDTH, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionCommandFieldWidth.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        SwitchLine(
            title = LocalizationManager.states.dialogs.commandFieldIsTransparent.value,
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionIsTransparentCommandLine.value,
                ),
            checked = isTransparentCommandLine,
            onCheckedChange = { config.setValue(IS_TRANSPARENT_COMMAND_LINE, it.toString()) },
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewCommandFieldBlock() {
    AppTheme {
        PreviewSurface(content = { CommandFieldBlock() })
    }
}
