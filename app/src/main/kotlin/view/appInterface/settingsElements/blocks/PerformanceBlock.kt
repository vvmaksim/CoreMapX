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

@Suppress("ktlint:standard:function-naming")
@Composable
fun PerformanceBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val animationDuration by remember { config.states.animationDuration }
    val commandFieldScrollDelay by remember { config.states.commandFieldScrollDelay }

    Column {
        NumberTextFieldLine(
            title = "Animation duration",
            valueType = Int::class,
            value = TextFieldValue("$animationDuration"),
            onValueChange = { config.setValue("animationDuration", it.text) },
            description =
                """
                This parameter is used to specify the speed of animations, for example, the speed of minimizing the side menu, etc.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = "Command field scroll delay",
            valueType = Int::class,
            value = TextFieldValue("$commandFieldScrollDelay"),
            onValueChange = { config.setValue("commandFieldScrollDelay", it.text) },
            description =
                """
                A certain amount of time passes when sending a command on the command line.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewPerformanceBlock() {
    AppTheme {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            PerformanceBlock()
        }
    }
}
