package view.appInterface.buttons

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun MainMenuTextButton(
    onClick: () -> Unit,
    iconVector: ImageVector,
    iconContentDescription: String,
    buttonText: String,
    isEnabled: Boolean = true,
) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
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
