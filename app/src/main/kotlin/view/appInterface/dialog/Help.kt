package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.dialogElements.blocks.BaseBlock

@Suppress("ktlint:standard:function-naming")
@Composable
fun Help(
    onDismiss: () -> Unit,
    dialogWidth: Dp = 800.dp,
    dialogHeight: Dp = 1000.dp,
    isExpandedSettings: Boolean = config.states.isExpandedSettings.value,
) {
    Dialog(onDismissRequest = onDismiss) {
        HelpContent(
            onDismiss = onDismiss,
            dialogWidth = dialogWidth,
            dialogHeight = dialogHeight,
            isExpandedSettings = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun HelpContent(
    onDismiss: () -> Unit,
    dialogWidth: Dp = 800.dp,
    dialogHeight: Dp = 1000.dp,
    isExpandedSettings: Boolean = config.states.isExpandedSettings.value,
) {
    var isCommandExpanded by remember { mutableStateOf(isExpandedSettings) }

    Surface(
        modifier =
            Modifier
                .width(dialogWidth)
                .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DialogHeader(
                title = "Справка",
                onButtonClick = onDismiss,
                subtitle = "Здесь вы можете узнать различные особенности реализации",
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier =
                    Modifier
                        .heightIn(max = dialogHeight)
                        .verticalScroll(rememberScrollState()),
            ) {
                BaseBlock(
                    title = "Команды",
                    content = { }, // Пока пусто=)
                    isExpanded = isCommandExpanded,
                    onClick = { isCommandExpanded = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewHelp() {
    AppTheme {
        HelpContent(
            onDismiss = {},
            dialogWidth = 800.dp,
            dialogHeight = 1000.dp,
            isExpandedSettings = true,
        )
    }
}
