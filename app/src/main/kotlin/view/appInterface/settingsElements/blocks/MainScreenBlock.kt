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
import org.coremapx.app.theme.AppTheme
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
            title = "Main screen start height",
            valueType = Int::class,
            value = TextFieldValue("$mainScreenStartHeight"),
            onValueChange = { config.setValue("mainScreenStartHeight", it.text) },
            description =
                """
                The starting height of the application window.
                
                It is used only when the `startWindowPlacement` is in the `Floating` state.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = "Main screen start width",
            valueType = Int::class,
            value = TextFieldValue("$mainScreenStartWidth"),
            onValueChange = { config.setValue("mainScreenStartWidth", it.text) },
            description =
                """
                The starting width of the application window.
                
                It is used only when the `startWindowPlacement` is in the `Floating` state.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = "Start window placement",
            items = listOf("Maximized", "Floating", "FullScreen"),
            selectedItem = startWindowPlacement,
            onItemSelected = { config.setValue("startWindowPlacement", it) },
            modifier = dropdownSelectButtonModifier,
            description =
                """
                Maximized - The application is open to the full window, but not to the full screen. (Recommended)
                
                Floating - The application is open in the window. The minimum window size is 1280x720.
                You can set the initial dimensions with the `mainScreenStartWidth` and `mainScreenStartHeight` parameters, but their values cannot be less than the minimum.
                
                FullScreen - The application is open to full screen and occupies its entire area. (Not recommended)
                """.trimIndent(),
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
