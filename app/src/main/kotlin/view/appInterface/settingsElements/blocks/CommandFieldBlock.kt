package view.appInterface.settingsElements.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.settingsElements.lines.NumberTextFieldLine
import view.appInterface.settingsElements.lines.SwitchLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun CommandFieldBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val messageOutputHeight by remember { config.states.messageOutputHeight }
    val maxCountMessages by remember { config.states.maxCountMessages }
    val commandFieldWidth by remember { config.states.commandFieldWidth }
    val isTransparentCommandLine by remember { config.states.isTransparentCommandLine }

    Column {
        NumberTextFieldLine(
            title = "Message output height",
            valueType = Int::class,
            value = TextFieldValue("$messageOutputHeight"),
            onValueChange = { config.setValue("messageOutputHeight", it.text) },
            description =
                """
                Maximum lifting height for command line output messages.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = "Max count messages",
            valueType = Int::class,
            value = TextFieldValue("$maxCountMessages"),
            onValueChange = {
                config.setValue("maxCountMessages", it.text)
            },
            description =
                """
                The maximum number of output messages that are remembered.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = "Command field width",
            valueType = Int::class,
            value = TextFieldValue("$commandFieldWidth"),
            onValueChange = { config.setValue("commandFieldWidth", it.text) },
            description =
                """
                The height of the command line.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        SwitchLine(
            title = "Command line is transparent",
            description =
                """
                This parameter is responsible for the transparency of the command line background.

                If it is enabled, the background of the text field will be transparent.
                If it is turned off, the background of the text field will take on the color specified in the corresponding parameter in the color settings.
                """.trimIndent(),
            checked = isTransparentCommandLine,
            onCheckedChange = { config.setValue("isTransparentCommandLine", it.toString()) },
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewCommandFieldBlock() {
    AppTheme {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            CommandFieldBlock()
        }
    }
}
