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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.settingsElements.lines.DropdownSelectLine
import view.appInterface.settingsElements.lines.NumberTextFieldLine
import java.util.Locale.getDefault

@Suppress("ktlint:standard:function-naming")
@Composable
fun CommandFieldBlock() {
    val messageOutputHeight by remember { config.states.messageOutputHeight }
    val maxCountMessages by remember { config.states.maxCountMessages }
    val commandFieldWidth by remember { config.states.commandFieldWidth }
    val isTransparentCommandLine by remember { config.states.isTransparentCommandLine }

    val dropdownSelectButtonModifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    Column {
        NumberTextFieldLine(
            title = "Message output height",
            valueType = Int::class,
            value = TextFieldValue("$messageOutputHeight"),
            onValueChange = {
                config.setValue("messageOutputHeight", it.text)
            },
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = "Max count messages",
            valueType = Int::class,
            value = TextFieldValue("$maxCountMessages"),
            onValueChange = {
                config.setValue("maxCountMessages", it.text)
            },
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = "Command field width",
            valueType = Int::class,
            value = TextFieldValue("$commandFieldWidth"),
            onValueChange = {
                config.setValue("commandFieldWidth", it.text)
            },
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = "Command line is transparent",
            items = listOf("True", "False"),
            selectedItem =
                isTransparentCommandLine.toString().replaceFirstChar {
                    if (it.isLowerCase()) {
                        it.titlecase(
                            getDefault(),
                        )
                    } else {
                        it.toString()
                    }
                },
            onItemSelected = {
                config.setValue("isTransparentCommandLine", it.lowercase())
            },
            modifier = dropdownSelectButtonModifier,
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
