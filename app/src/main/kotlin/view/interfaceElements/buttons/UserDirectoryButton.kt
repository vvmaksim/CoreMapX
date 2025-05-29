package view.interfaceElements.buttons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mu.KotlinLogging
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import java.awt.Desktop
import java.io.File

private val logger = KotlinLogging.logger {}

@Composable
fun UserDirectoryButton() {
    val mainMenuButtonColor = config.getColor("mainMenuButtonColor")
    val mainMenuButtonTextColor = config.getColor("mainMenuButtonTextColor")
    
    Button(
        onClick = {
            try {
                Desktop.getDesktop().open(File(baseUserDirPath))
            } catch (ex: Exception) {
                logger.error("The user directory cannot be opened. Error: $ex")
            }
        },
        modifier = Modifier.size(60.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = mainMenuButtonColor,
            contentColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Folder,
            contentDescription = "Open User Directory",
            tint = mainMenuButtonTextColor,
            modifier = Modifier.size(60.dp)
        )
    }
}
