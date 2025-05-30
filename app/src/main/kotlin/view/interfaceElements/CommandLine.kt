package view.interfaceElements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.coremapx.app.config

@Composable
fun CommandLine(
    modifier: Modifier = Modifier,
    outputMessages: MutableList<String> = mutableListOf(),
    commandLineBackgroundColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color.Gray,
    roundedCornerShapeSize: Dp = 8.dp,
    onCommand: (String) -> Unit = {},
) {
    var commandText by remember { mutableStateOf(TextFieldValue("")) }
    val scrollState = rememberScrollState()

    val commandFieldScrollDelay = config.getLongValue("commandFieldScrollDelay") ?: 0L
    val messageOutputHeight = (config.getIntValue("messageOutputHeight") ?: 0).dp
    val commandFieldHeight = (config.getIntValue("commandFieldHeight") ?: 0).dp

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
                                withStyle(style = SpanStyle(color = Color.Red)) {
                                    append("Error:")
                                }
                                withStyle(style = SpanStyle(color = Color.Black)) {
                                    append(parts[1])
                                }
                            } else {
                                withStyle(style = SpanStyle(color = Color.Black)) {
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
                    .height(commandFieldHeight)
                    .background(commandLineBackgroundColor, RoundedCornerShape(roundedCornerShapeSize))
                    .border(BorderStroke(width = borderWidth, color = borderColor), RoundedCornerShape(roundedCornerShapeSize)),
            placeholder = { Text("Enter command") },
            shape = RoundedCornerShape(roundedCornerShapeSize),
        )
    }
}
