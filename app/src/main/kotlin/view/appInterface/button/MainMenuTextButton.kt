package view.appInterface.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.coremapx.app.theme.AppTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun MainMenuTextButton(
    onClick: () -> Unit,
    iconVector: ImageVector,
    iconContentDescription: String,
    buttonText: String,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        colors =
            ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                disabledBackgroundColor = MaterialTheme.colors.background,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.width(16.dp))
            Icon(
                imageVector = iconVector,
                contentDescription = iconContentDescription,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = buttonText,
                style = MaterialTheme.typography.button,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewEnabledMainMenuTextButton() {
    AppTheme {
        MainMenuTextButton(
            onClick = {},
            iconVector = Icons.Filled.Add,
            iconContentDescription = "Test",
            buttonText = "Test Button",
            isEnabled = true,
            modifier = Modifier.width(250.dp),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewUnenabledMainMenuTextButton() {
    AppTheme {
        MainMenuTextButton(
            onClick = {},
            iconVector = Icons.Filled.Add,
            iconContentDescription = "Test",
            buttonText = "Test Button",
            isEnabled = false,
            modifier = Modifier.width(250.dp),
        )
    }
}
