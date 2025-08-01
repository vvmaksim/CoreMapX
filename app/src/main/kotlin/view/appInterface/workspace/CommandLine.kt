package view.appInterface.workspace

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface
import view.appInterface.textField.CustomTextField

@Suppress("ktlint:standard:function-naming")
@Composable
fun CommandLine(
    modifier: Modifier = Modifier,
    outputMessages: MutableList<String> = mutableListOf(),
    commandLineBackgroundColor: Color = Color.Transparent,
    borderShape: CornerBasedShape = MaterialTheme.shapes.medium,
    placeholderText: String = LocalizationManager.states.anyTextStates.enterCommand.value,
    placeholderTextStyle: TextStyle = MaterialTheme.typography.body1,
    onCommand: (String) -> Unit = {},
    commandText: TextFieldValue,
    onCommandTextChange: (TextFieldValue) -> Unit,
    userCommands: MutableList<String>,
    commandHistoryIndex: Int,
    onCommandHistoryIndexChange: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    val commandFieldScrollDelay =
        config.states.commandFieldScrollDelay.value
            .toLong()
    val messageOutputHeight = config.states.messageOutputHeight.value.dp

    LaunchedEffect(outputMessages.size) {
        delay(commandFieldScrollDelay)
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    val onPreviewKeyEvent: (KeyEvent) -> Boolean = { event ->
        if (event.type == KeyEventType.KeyDown) {
            when (event.key) {
                Key.Enter -> {
                    val command = commandText.text.trim()
                    if (command.isNotEmpty()) {
                        onCommand(command)
                        onCommandTextChange(TextFieldValue(""))
                        onCommandHistoryIndexChange(-1)
                    }
                    true
                }
                Key.DirectionUp -> {
                    if (userCommands.isNotEmpty()) {
                        val newIndex =
                            if (commandHistoryIndex < userCommands.size - 1) commandHistoryIndex + 1 else userCommands.size - 1
                        onCommandHistoryIndexChange(newIndex)
                        val cmd = userCommands[userCommands.size - 1 - newIndex]
                        onCommandTextChange(TextFieldValue(cmd, selection = TextRange(cmd.length)))
                    }
                    true
                }
                Key.DirectionDown -> {
                    if (userCommands.isNotEmpty()) {
                        val newIndex =
                            if (commandHistoryIndex > 0) commandHistoryIndex - 1 else -1
                        onCommandHistoryIndexChange(newIndex)
                        if (newIndex == -1) {
                            onCommandTextChange(TextFieldValue(""))
                        } else {
                            val cmd = userCommands[userCommands.size - 1 - newIndex]
                            onCommandTextChange(TextFieldValue(cmd, selection = TextRange(cmd.length)))
                        }
                    }
                    true
                }
                else -> false
            }
        } else {
            false
        }
    }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = messageOutputHeight)
                    .padding(8.dp)
                    .verticalScroll(scrollState),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart),
            ) {
                outputMessages.forEach { message ->
                    val parts = message.split("Error:", limit = 2)
                    val annotatedText =
                        buildAnnotatedString {
                            if (parts.size == 2) {
                                withStyle(style = SpanStyle(color = MaterialTheme.colors.error)) {
                                    append("Error:")
                                }
                                withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface)) {
                                    append(parts[1])
                                }
                            } else {
                                withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface)) {
                                    append(message)
                                }
                            }
                        }
                    Text(
                        text = annotatedText,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
        CustomTextField(
            value = commandText,
            onValueChange = {
                onCommandTextChange(it)
                onCommandHistoryIndexChange(-1)
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = commandLineBackgroundColor,
                        shape = borderShape,
                    ).onPreviewKeyEvent(onPreviewKeyEvent),
            placeholder = {
                Text(
                    text = placeholderText,
                    style = placeholderTextStyle,
                )
            },
            leadingIcon = null,
            singleLine = false,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewCommandLine() {
    var commandText by remember { mutableStateOf(TextFieldValue("")) }
    AppTheme {
        PreviewSurface(
            content = {
                CommandLine(
                    outputMessages =
                        mutableListOf(
                            "123",
                            "52",
                            "Some text",
                        ),
                    placeholderText = "Some placeholder text",
                    onCommand = {},
                    commandText = commandText,
                    onCommandTextChange = {},
                    userCommands = mutableListOf(),
                    commandHistoryIndex = -1,
                    onCommandHistoryIndexChange = {},
                )
            },
        )
    }
}
