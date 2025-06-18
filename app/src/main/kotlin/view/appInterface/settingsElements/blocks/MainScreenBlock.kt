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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.settingsElements.lines.DropdownSelectLine
import view.appInterface.settingsElements.lines.NumberTextFieldLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun MainScreenBlock() {
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
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = "Main screen start width",
            valueType = Int::class,
            value = TextFieldValue("$mainScreenStartWidth"),
            onValueChange = { config.setValue("mainScreenStartWidth", it.text) },
        )
        Spacer(Modifier.height(8.dp))
        DropdownSelectLine(
            title = "Start window placement",
            items = listOf("Maximized", "Floating", "FullScreen"),
            selectedItem = startWindowPlacement,
            onItemSelected = { config.setValue("startWindowPlacement", it) },
            modifier = dropdownSelectButtonModifier,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewMainScreenBlock() {
    AppTheme {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            MainScreenBlock()
        }
    }
}
