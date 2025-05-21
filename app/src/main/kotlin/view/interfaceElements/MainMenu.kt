package view.interfaceElements

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory
import view.interfaceElements.dialogs.OpenGraphErrors
import viewmodel.MainScreenViewModel
import java.awt.FileDialog

@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainMenu(
    isMenuVisible: Boolean,
    onMenuVisibilityChange: (Boolean) -> Unit,
    onNewGraphClick: () -> Unit,
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier,
) {
    val animationDuration = config.getIntValue("animationDuration") ?: 0
    val mainMenuColor = config.getColor("mainMenuColor")
    val mainMenuButtonColor = config.getColor("mainMenuButtonColor")
    val mainMenuButtonTextColor = config.getColor("mainMenuButtonTextColor")

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = mainMenuButtonColor)
    val buttonModifier = Modifier.fillMaxWidth()
    val buttonFontSize = (config.getIntValue("mainMenuButtonsFontSize") ?: 0).sp
    val logoFontSize = (config.getIntValue("mainMenuLogoFontSize") ?: 0).sp

    var showOpenGraphErrorsDialog by remember { mutableStateOf(false) }
    var warnings by remember { mutableStateOf<List<String>>(emptyList()) }

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
                TextButton(
                    modifier = buttonModifier,
                    onClick = {
                        val fileDialog = FileDialog(java.awt.Frame(), "Select graph file", FileDialog.LOAD)
                        fileDialog.directory = UserDirectory.baseUserDirPath + "/data/graphs"
                        fileDialog.isVisible = true
                        val file = fileDialog.files.firstOrNull()
                        if (file != null) {
                            val loadResult = viewModel.loadGraphFromFile(file)
                            warnings = when (loadResult) {
                                is Result.Success -> {
                                    loadResult.data
                                }
                                is Result.Error -> {
                                    listOf("Error: ${loadResult.error.type}.${loadResult.error.description}")
                                }
                            }
                            if (warnings.isNotEmpty()) {
                                showOpenGraphErrorsDialog = true
                            }
                        }
                    },
                    colors = buttonColors
                ) {
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

    if (showOpenGraphErrorsDialog) {
        OpenGraphErrors(
            onDismiss = { showOpenGraphErrorsDialog = false },
            warnings = warnings
        )
    }
}
