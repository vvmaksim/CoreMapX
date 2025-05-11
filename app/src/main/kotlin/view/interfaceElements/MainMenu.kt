package view.interfaceElements

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
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

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = mainMenuButtonColor)
    val buttonModifier = Modifier.fillMaxWidth()
    val buttonFontSize = 16.sp
    val logoFontSize = 24.sp

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
                Text(text = "CoreMapX", fontSize = logoFontSize, modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally))

                TextButton(modifier = buttonModifier, onClick = onNewGraphClick, colors = buttonColors) {
                    Text(text = "New Graph", color = mainMenuButtonTextColor, fontSize = buttonFontSize)
                }
                TextButton(modifier = buttonModifier, onClick = { }, colors = buttonColors) {
                    Text(text = "Save Graph", color = mainMenuButtonTextColor, fontSize = buttonFontSize)
                }
                TextButton(modifier = buttonModifier, onClick = { }, colors = buttonColors) {
                    Text(text = "Open Graph", color = mainMenuButtonTextColor, fontSize = buttonFontSize)
                }
                TextButton(modifier = buttonModifier, onClick = { }, colors = buttonColors) {
                    Text(text = "Analytics", color = mainMenuButtonTextColor, fontSize = buttonFontSize)
                }
                TextButton(modifier = buttonModifier, onClick = { }, colors = buttonColors) {
                    Text(text = "Templates", color = mainMenuButtonTextColor, fontSize = buttonFontSize)
                }
                TextButton(modifier = buttonModifier, onClick = { }, colors = buttonColors) {
                    Text(text = "Settings", color = mainMenuButtonTextColor, fontSize = buttonFontSize)
                }
                Spacer(Modifier.weight(1f))
                TextButton(modifier = buttonModifier, onClick = { onMenuVisibilityChange(false) }, colors = buttonColors) {
                    Text(text = "Slide Menu", color = mainMenuButtonTextColor, fontSize = buttonFontSize)
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
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextButton(onClick = { onMenuVisibilityChange(true) }, colors = buttonColors) {
                    Text(text = "Open Menu", color = mainMenuButtonTextColor, fontSize = buttonFontSize)
                }
            }
        }
    }
}
