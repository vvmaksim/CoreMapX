package view.appInterface.textField

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import extensions.border
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    leadingIcon: @Composable (() -> Unit)? = {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Text Field",
            tint =
                if (enabled) {
                    if (isError) {
                        MaterialTheme.colors.error
                    } else {
                        MaterialTheme.colors.primary
                    }
                } else {
                    MaterialTheme.colors.border
                },
        )
    },
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors =
        TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.border,
            cursorColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.onSurface,
        ),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewCustomTextFields() {
    AppTheme {
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    CustomTextField(
                        value = TextFieldValue("Test Field"),
                        onValueChange = {},
                        modifier = Modifier.padding(8.dp),
                    )
                    CustomTextField(
                        value = TextFieldValue("Test Field"),
                        onValueChange = {},
                        enabled = false,
                        modifier = Modifier.padding(8.dp),
                    )
                    CustomTextField(
                        value = TextFieldValue(""),
                        onValueChange = {},
                        placeholder = { Text("This is placeholder") },
                        modifier = Modifier.padding(8.dp),
                    )
                    CustomTextField(
                        value = TextFieldValue("Test Field"),
                        onValueChange = {},
                        isError = true,
                        modifier = Modifier.padding(8.dp),
                    )
                    CustomTextField(
                        value = TextFieldValue(""),
                        onValueChange = {},
                        placeholder = { Text("This is placeholder") },
                        modifier = Modifier.padding(8.dp),
                        leadingIcon = null,
                    )
                }
            },
        )
    }
}
