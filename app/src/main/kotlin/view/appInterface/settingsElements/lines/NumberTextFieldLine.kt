package view.appInterface.settingsElements.lines

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import extensions.border
import extensions.success
import model.result.Result
import org.coremapx.app.theme.AppTheme
import view.appInterface.textField.CustomTextField
import kotlin.reflect.KClass

private fun validateStringAsNumberTypes(
    text: String,
    valueType: KClass<*>,
): Boolean =
    when (valueType) {
        Int::class -> text.toIntOrNull() != null
        Long::class -> text.toLongOrNull() != null
        Float::class -> text.toFloatOrNull() != null
        Double::class -> text.toDoubleOrNull() != null
        else -> true
    }

@Suppress("ktlint:standard:function-naming")
@Composable
fun NumberTextFieldLine(
    title: String,
    valueType: KClass<*>,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Result<Boolean>?,
    modifier: Modifier = Modifier,
    numberTextFieldWidth: Dp = 190.dp,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions? = null,
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
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.border,
            cursorColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primary,
            unfocusedLabelColor = MaterialTheme.colors.onSurface,
        ),
) {
    val showSuccessTime = 2000L
    var showError by remember { mutableStateOf(false) }
    var currentValue by remember { mutableStateOf(value) }
    var localErrorText by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(isError) {
        showError = isError
        if (!isError) localErrorText = null
    }

    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            kotlinx.coroutines.delay(showSuccessTime)
            showSuccess = false
        }
    }
    val customColors =
        if (showSuccess) {
            TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.success,
                unfocusedBorderColor = MaterialTheme.colors.success,
                cursorColor = MaterialTheme.colors.primary,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.onSurface,
            )
        } else {
            colors
        }

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
        Column {
            CustomTextField(
                value = currentValue,
                onValueChange = { newValue ->
                    currentValue = newValue
                    showError = false
                    localErrorText = null
                },
                modifier = Modifier.width(numberTextFieldWidth),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = textStyle,
                label = label,
                placeholder = placeholder,
                singleLine = singleLine,
                trailingIcon = trailingIcon,
                isError = showError,
                visualTransformation = visualTransformation,
                keyboardOptions =
                    keyboardOptions.copy(
                        imeAction = ImeAction.Done,
                    ),
                keyboardActions =
                    keyboardActions
                        ?: KeyboardActions(
                            onDone = {
                                val trimmedText = currentValue.text.trim()
                                if (!validateStringAsNumberTypes(trimmedText, valueType)) {
                                    showError = true
                                    localErrorText = "Value must be ${valueType.simpleName} type"
                                    return@KeyboardActions
                                }
                                val result = onValueChange(currentValue.copy(text = trimmedText))
                                if (result is Result.Error) {
                                    showError = true
                                    localErrorText = result.error.description ?: "Save value error"
                                    showSuccess = false
                                } else {
                                    showError = false
                                    localErrorText = null
                                    showSuccess = true
                                }
                            },
                        ),
                leadingIcon = leadingIcon,
                maxLines = maxLines,
                minLines = minLines,
                interactionSource = interactionSource,
                shape = shape,
                colors = customColors,
            )

            if (showSuccess) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Success",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.success,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    softWrap = true,
                )
            }

            if (showError) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = localErrorText ?: errorText ?: "Value is not valid. This field must be ${valueType.simpleName} type.",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.width(numberTextFieldWidth),
                    softWrap = true,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewNumberTextFieldLine() {
    AppTheme {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                NumberTextFieldLine(
                    title = "Some Title 1",
                    valueType = Int::class,
                    value = TextFieldValue("200"),
                    onValueChange = { Result.Success(true) },
                    modifier = Modifier.padding(8.dp),
                )
                NumberTextFieldLine(
                    title = "Some Title 2",
                    valueType = Float::class,
                    value = TextFieldValue("Test Field"),
                    onValueChange = { Result.Success(true) },
                    enabled = false,
                    modifier = Modifier.padding(8.dp),
                )
                NumberTextFieldLine(
                    title = "Some Title 3",
                    valueType = Double::class,
                    value = TextFieldValue(""),
                    onValueChange = { Result.Success(true) },
                    placeholder = { Text("This is placeholder") },
                    modifier = Modifier.padding(8.dp),
                )
                NumberTextFieldLine(
                    title = "Some Title 4",
                    valueType = Int::class,
                    value = TextFieldValue("Test Field"),
                    onValueChange = { Result.Success(true) },
                    isError = true,
                    errorText = "Custom error message",
                    modifier = Modifier.padding(8.dp),
                )
                NumberTextFieldLine(
                    title = "Some Title 5",
                    valueType = Int::class,
                    value = TextFieldValue("Test Field"),
                    onValueChange = { Result.Success(true) },
                    isError = true,
                    errorText = null,
                    modifier = Modifier.padding(8.dp),
                )
                NumberTextFieldLine(
                    title = "Some Title 6",
                    valueType = Float::class,
                    value = TextFieldValue(""),
                    onValueChange = { Result.Success(true) },
                    placeholder = { Text("This is placeholder") },
                    modifier = Modifier.padding(8.dp),
                    leadingIcon = null,
                )
            }
        }
    }
}
