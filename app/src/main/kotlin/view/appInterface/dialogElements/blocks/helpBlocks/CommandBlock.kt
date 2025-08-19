package view.appInterface.dialogElements.blocks.helpBlocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun CommandBlock() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            text = LocalizationManager.states.dialogs.commandGeneralInformationTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandGeneralInformationText1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandAttention1Point1.value,
                ),
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandExample1Point1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "add vertex 1 A;\n" +
                    "add vertex id:2 label:B;\n" +
                    "add vertex label:C id:3;\n" +
                    "add edge 1 2 52;\n" +
                    "add edge from:2 to:3 weight:5;\n" +
                    "add edge weight:25 to:1 from:3",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandAttention1Point2.value,
                ),
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.commandAddTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandAddText1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.commandRmTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandAttention1Point3.value,
                ),
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandRmText1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandAttention1Point4.value,
                ),
            color = config.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "add vertex id:1 label:1;\n" +
                    "add vertex id:2 label:2;\n" +
                    "add edge from:1 to:2",
        )
        Spacer(Modifier.height(8.dp))
        CodeBlock("rm edge from:2 to:1")
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.commandSetTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.commandSetText1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        Text(LocalizationManager.states.dialogs.commandSetText2.value)
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "set strategy Random",
        )
        Spacer(Modifier.height(8.dp))
        Text(LocalizationManager.states.dialogs.commandSetText3.value)
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "set strategy Circular",
        )
        Spacer(Modifier.height(8.dp))
        Text(LocalizationManager.states.dialogs.commandSetText4.value)
        Spacer(Modifier.height(8.dp))
        CodeBlock(
            text =
                "set strategy Force-Directed",
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.commandClearTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(LocalizationManager.states.dialogs.commandClearText1.value)
        Spacer(Modifier.height(8.dp))
        CodeBlock("clear")
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.commandGraphClearTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(LocalizationManager.states.dialogs.commandGraphClearText1.value)
        Spacer(Modifier.height(8.dp))
        CodeBlock("graph_clear")
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.commandHelpTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(LocalizationManager.states.dialogs.commandHelpText1.value)
        Spacer(Modifier.height(8.dp))
        CodeBlock("help")
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewCommandBlock() {
    AppTheme {
        PreviewSurface(content = { CommandBlock() })
    }
}
