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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenu(
    isMenuVisible: Boolean,
    onMenuVisibilityChange: (Boolean) -> Unit,
    onNewGraphClick: () -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        AnimatedVisibility(
            visible = isMenuVisible,
            enter = fadeIn(animationSpec = tween(300)) + slideInHorizontally(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + slideOutHorizontally(animationSpec = tween(300)),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color(0xFFE6E6FA))
                        .padding(8.dp),
            ) {
                Text("CoreMapX", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
                TextButton(onClick = onNewGraphClick) {
                    Text("New Graph")
                }
                TextButton(onClick = { }) {
                    Text("Save Graph")
                }
                TextButton(onClick = { }) {
                    Text("Open Graph")
                }
                TextButton(onClick = { }) {
                    Text("Analytics")
                }
                TextButton(onClick = { }) {
                    Text("Templates")
                }
                TextButton(onClick = { }) {
                    Text("Settings")
                }
                Spacer(Modifier.weight(1f))
                TextButton(onClick = { onMenuVisibilityChange(false) }) {
                    Text("<")
                }
            }
        }

        AnimatedVisibility(
            visible = !isMenuVisible,
            enter = fadeIn(animationSpec = tween(300)) + slideInHorizontally(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + slideOutHorizontally(animationSpec = tween(300)),
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
                    onClick = { onMenuVisibilityChange(true) },
                ) {
                    Text(">")
                }
            }
        }
    }
}
