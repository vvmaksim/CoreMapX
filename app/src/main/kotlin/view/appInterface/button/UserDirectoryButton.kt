package view.appInterface.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mu.KotlinLogging
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import java.awt.Desktop
import java.io.File

private val logger = KotlinLogging.logger {}

@Suppress("ktlint:standard:function-naming")
@Composable
fun UserDirectoryButton(size: Dp = 60.dp) {
    Button(
        onClick = {
            try {
                Desktop.getDesktop().open(File(baseUserDirPath))
            } catch (ex: Exception) {
                logger.error("The user directory cannot be opened. Error: $ex")
            }
        },
        modifier = Modifier.size(size),
        elevation =
            ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp,
            ),
        colors =
            ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = Color.Transparent,
            ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Icon(
            imageVector = Icons.Default.Folder,
            contentDescription = "Open User Directory",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(size),
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun PreviewUserDirectoryButton() {
    AppTheme {
        UserDirectoryButton()
    }
}
