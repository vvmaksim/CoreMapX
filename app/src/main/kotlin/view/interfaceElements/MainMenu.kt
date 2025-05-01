package view.interfaceElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenu(
    isMenuVisible: Boolean,
    onMenuVisibilityChange: (Boolean) -> Unit,
    onNewGraphClick: () -> Unit,
) {
    if (isMenuVisible) {
        Column(
            modifier =
                Modifier
                    .width(200.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFF0F0F0))
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
                Text("Settings")
            }
            Spacer(Modifier.weight(1f))
            TextButton(onClick = { onMenuVisibilityChange(false) }) {
                Text("<")
            }
        }
    } else {
        Column(
            modifier =
                Modifier
                    .width(50.dp)
                    .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { onMenuVisibilityChange(true) },
            ) {
                Text(">")
            }
        }
    }
}
