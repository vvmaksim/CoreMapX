package view.interfaceElements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun CommandLine(
    modifier: Modifier = Modifier,
    outputMessages: MutableList<String> = mutableListOf(),
    onCommand: (String) -> Unit = {},
) {
    var commandText by remember { mutableStateOf(TextFieldValue("")) }
    val scrollState = rememberScrollState()
    val formattedText =
        buildAnnotatedString {
            outputMessages.forEachIndexed { index, message ->
                val parts = message.split("Command:", limit = 2)
                if (parts.size == 2) {
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("Command:")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(parts[1])
                    }
                } else {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(message)
                    }
                }
                if (index < outputMessages.size - 1) {
                    append("\n")
                }
            }
        }

    LaunchedEffect(outputMessages) {
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
                    .heightIn(max = 150.dp)
                    .padding(8.dp)
                    .verticalScroll(scrollState),
        ) {
            Text(
                text = formattedText,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
            )
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
                    .height(56.dp),
            placeholder = { Text("Enter command") },
        )
    }
}
