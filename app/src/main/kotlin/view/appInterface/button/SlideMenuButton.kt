package view.appInterface.button

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun SlideMenuButton(
    onClick: () -> Unit,
    isReversed: Boolean = false,
    size: Dp = 60.dp,
) {
    Button(
        onClick = {
            logDebug("Click on SlideMenuButton")
            onClick
        },
        modifier = Modifier.size(size),
        elevation =
            ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 0.dp,
            ),
        colors =
            ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = Color.Transparent,
            ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = LocalizationManager.states.anyIconDescriptionsStates.slideMenu.value,
            tint = MaterialTheme.colors.primary,
            modifier =
                Modifier
                    .size(size)
                    .graphicsLayer {
                        rotationZ = if (isReversed) 180f else 0f
                    },
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewSlideMenuButton() {
    AppTheme {
        SlideMenuButton(
            onClick = {},
        )
    }
}
