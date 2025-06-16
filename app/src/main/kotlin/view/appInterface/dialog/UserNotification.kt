package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.coremapx.app.theme.AppTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun UserNotification(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    dialogWidth: Dp = 550.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        UserNotificationContent(
            onDismiss = onDismiss,
            title = title,
            message = message,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun UserNotificationContent(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    dialogWidth: Dp = 550.dp,
) {
    Surface(
        modifier =
            Modifier
                .width(dialogWidth)
                .wrapContentHeight(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
            )

            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
            )

            Button(
                onClick = onDismiss,
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.background,
                    ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun PreviewUserNotificationContent() {
    AppTheme {
        UserNotificationContent(
            onDismiss = {},
            title = "Test Notification",
            message = "This is test notification with some text message",
            dialogWidth = 500.dp,
        )
    }
}
