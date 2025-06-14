package view.appInterface

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import extensions.border
import org.coremapx.app.config

@Suppress("ktlint:standard:function-naming")
@Composable
fun CommandLine(
    modifier: Modifier = Modifier,
    outputMessages: MutableList<String> = mutableListOf(),
    commandLineBackgroundColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp,
    onCommand: (String) -> Unit = {},
) {
    var commandText by remember { mutableStateOf(TextFieldValue("")) }
    val scrollState = rememberScrollState()

    val commandFieldScrollDelay =
        config.states.commandFieldScrollDelay.value
            .toLong()
    val messageOutputHeight = config.states.messageOutputHeight.value.dp

    LaunchedEffect(outputMessages.size) {
        kotlinx.coroutines.delay(commandFieldScrollDelay)
        scrollState.animateScrollTo(scrollState.maxValue)
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

        OutlinedTextField(
            value = commandText,
            onValueChange = { newValue ->
                commandText = newValue
                if (newValue.text.endsWith("\n")) {
                    val command = newValue.text.trim()
                    if (command.isNotEmpty()) {
                        onCommand(command)
                    }
                    commandText = TextFieldValue("")
                }
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = commandLineBackgroundColor,
                        shape = MaterialTheme.shapes.medium,
                    ).border(
                        border =
                            BorderStroke(
                                width = borderWidth,
                                color = MaterialTheme.colors.border,
                            ),
                        shape = MaterialTheme.shapes.medium,
                    ),
            placeholder = {
                Text(
                    text = "Enter command",
                    style = MaterialTheme.typography.body1,
                )
            },
            shape = MaterialTheme.shapes.medium,
        )
    }
}
