package view.appInterface.textField

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.coremapx.app.theme.AppTheme
import view.appInterface.dialogElements.description.DescriptionIconButton
import view.appInterface.dialogElements.description.DescriptionText
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun CustomTextFieldLine(
    title: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    description: String? = null,
    placeholder: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    textFieldWidth: Dp = 190.dp,
    endSpacerWidth: Dp = if (description == null) 24.dp else 0.dp,
    singleLine: Boolean = true,
    isError: Boolean = false,
    isExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(isExpanded) }

    Column {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(8.dp))
            CustomTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.width(textFieldWidth),
                placeholder = placeholder,
                singleLine = singleLine,
                isError = isError,
            )
            if (description != null) {
                DescriptionIconButton(
                    onClick = { expanded = !expanded },
                    isExpanded = expanded,
                )
            }
            Spacer(modifier = Modifier.padding(endSpacerWidth))
        }
        if (expanded && description != null) {
            Spacer(modifier = Modifier.height(8.dp))
            DescriptionText(description = description)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewCustomTextFieldLines() {
    AppTheme {
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    CustomTextFieldLine(
                        value = TextFieldValue("Test Field"),
                        onValueChange = {},
                        modifier = Modifier.padding(8.dp),
                        title = "Some title",
                        description = "Some description",
                    )
                    CustomTextFieldLine(
                        value = TextFieldValue("Test Field"),
                        onValueChange = {},
                        modifier = Modifier.padding(8.dp),
                        title = "Some title",
                        description = "Some description",
                        isError = true,
                    )
                    CustomTextFieldLine(
                        value = TextFieldValue(""),
                        placeholder = { Text("Enter id") },
                        onValueChange = {},
                        modifier = Modifier.padding(8.dp),
                        title = "Some title",
                        description = "Some description",
                        isExpanded = true,
                    )
                    CustomTextFieldLine(
                        value = TextFieldValue(""),
                        onValueChange = {},
                        modifier = Modifier.padding(8.dp),
                        title = "Some title",
                    )
                    CustomTextFieldLine(
                        value = TextFieldValue(""),
                        onValueChange = {},
                        modifier = Modifier.padding(8.dp),
                        title = "Some title",
                        endSpacerWidth = 0.dp,
                    )
                }
            },
        )
    }
}
