package view.interfaceElements

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.coremapx.app.config

@Composable
fun MainMenu(
    isMenuVisible: Boolean,
    onMenuVisibilityChange: (Boolean) -> Unit,
    onNewGraphClick: () -> Unit,
    modifier: Modifier,
) {
    val animationDuration = config.getIntValue("animationDuration") ?: 0
    val mainMenuColor = config.getColor("mainMenuColor")
    val mainMenuButtonColor = config.getColor("mainMenuButtonColor")
    val mainMenuButtonTextColor = config.getColor("mainMenuButtonTextColor")

    val buttonModifier = Modifier.background(mainMenuButtonColor)

    Box(
        modifier = modifier,
    ) {
        AnimatedVisibility(
            visible = isMenuVisible,
            enter = fadeIn(animationSpec = tween(animationDuration)) + slideInHorizontally(animationSpec = tween(animationDuration)),
            exit = fadeOut(animationSpec = tween(animationDuration)) + slideOutHorizontally(animationSpec = tween(animationDuration)),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(mainMenuColor)
                        .padding(8.dp),
            ) {
                Text(text = "CoreMapX", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
                TextButton(modifier = buttonModifier, onClick = onNewGraphClick) {
                    Text(text = "New Graph", color = mainMenuButtonTextColor)
                }
                TextButton(modifier = buttonModifier, onClick = { }) {
                    Text(text = "Save Graph", color = mainMenuButtonTextColor)
                }
                TextButton(modifier = buttonModifier, onClick = { }) {
                    Text(text = "Open Graph", color = mainMenuButtonTextColor)
                }
                TextButton(modifier=buttonModifier, onClick = { }) {
                    Text(text = "Analytics", color = mainMenuButtonTextColor)
                }
                TextButton(modifier=buttonModifier, onClick = { }) {
                    Text(text = "Templates", color = mainMenuButtonTextColor)
                }
                TextButton(modifier=buttonModifier, onClick = { }) {
                    Text(text = "Settings", color = mainMenuButtonTextColor)
                }
                Spacer(Modifier.weight(1f))
                TextButton(modifier=buttonModifier, onClick = { onMenuVisibilityChange(false) }) {
                    Text(text = "<", color = mainMenuButtonTextColor)
                }
            }
        }

        AnimatedVisibility(
            visible = !isMenuVisible,
            enter = fadeIn(animationSpec = tween(animationDuration)) + slideInHorizontally(animationSpec = tween(animationDuration)),
            exit = fadeOut(animationSpec = tween(animationDuration)) + slideOutHorizontally(animationSpec = tween(animationDuration)),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    modifier = buttonModifier,
                    onClick = { onMenuVisibilityChange(true) },
                ) {
                    Text(text = ">", color = mainMenuButtonTextColor)
                }
            }
        }
    }
}
