package view.interfaceElements.buttons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.coremapx.app.config

@Composable
fun SlideMenuButton(
    onClick: () -> Unit,
    isReversed: Boolean = false
) {
    val mainMenuButtonColor = config.getColor("mainMenuButtonColor")
    val mainMenuButtonTextColor = config.getColor("mainMenuButtonTextColor")

    Button(
        onClick = onClick,
        modifier = Modifier.size(60.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = mainMenuButtonColor,
            contentColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Slide Menu",
            tint = mainMenuButtonTextColor,
            modifier = Modifier
                .size(60.dp)
                .graphicsLayer {
                    rotationZ = if (isReversed) 180f else 0f
                }
        )
    }
}
