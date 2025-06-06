package view.interfaceElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.result.Result
import org.coremapx.app.config
import view.interfaceElements.buttons.MainMenuTextButton
import view.interfaceElements.buttons.SlideMenuButton
import view.interfaceElements.buttons.UserDirectoryButton
import view.interfaceElements.dialogs.NewGraph
import view.interfaceElements.dialogs.OpenGraphErrors
import view.interfaceElements.dialogs.OpenRepository
import view.interfaceElements.dialogs.SaveGraphAs
import view.interfaceElements.dialogs.UserNotification
import viewmodel.MainScreenViewModel
import java.io.File

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainMenu(
    isMenuVisible: Boolean,
    onMenuVisibilityChange: (Boolean) -> Unit,
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier,
) {
    val animationDuration = config.getIntValue("animationDuration") ?: 0

    var showOpenGraphErrorsDialog by remember { mutableStateOf(false) }
    var showOpenRepositoryDialog by remember { mutableStateOf(false) }
    var showSaveGraphAsDialog by remember { mutableStateOf(false) }
    var showUserNotification by remember { mutableStateOf(false) }
    var showNewGraphDialog by remember { mutableStateOf(false) }
    var warnings by remember { mutableStateOf<List<String>>(emptyList()) }
    var saveError by remember { mutableStateOf("") }
    var selectedRepositoryFile by remember { mutableStateOf(File("")) }

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
                        .background(MaterialTheme.colors.background)
                        .padding(8.dp),
            ) {
                Text(
                    text = "CoreMapX",
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    modifier =
                        Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally),
                )

                MainMenuTextButton(
                    onClick = { showNewGraphDialog = true },
                    iconVector = Icons.Filled.Add,
                    iconContentDescription = "New Graph Icon",
                    buttonText = "New Graph",
                )

                MainMenuTextButton(
                    onClick = {
                        if (viewModel.graphPath == null) {
                            showSaveGraphAsDialog = true
                        } else {
                            val saveResult = viewModel.saveGraph()
                            if (saveResult is Result.Error) {
                                saveError = "Error: ${saveResult.error.type}.${saveResult.error.description}"
                                showUserNotification = true
                            }
                        }
                    },
                    iconVector = Icons.Filled.Save,
                    iconContentDescription = "Save Graph Icon",
                    buttonText = "Save Graph",
                    isEnabled = viewModel.isGraphActive,
                )

                MainMenuTextButton(
                    onClick = { showSaveGraphAsDialog = true },
                    iconVector = Icons.Filled.SaveAs,
                    iconContentDescription = "Save Graph As Icon",
                    buttonText = "Save Graph As",
                    isEnabled = viewModel.isGraphActive,
                )

                MainMenuTextButton(
                    onClick = {
                        val loadResult = viewModel.openGraphFile()
                        warnings =
                            when (loadResult) {
                                is Result.Success -> loadResult.data
                                is Result.Error -> listOf("Error: ${loadResult.error.type}.${loadResult.error.description}")
                            }
                        if (warnings.isNotEmpty()) showOpenGraphErrorsDialog = true
                    },
                    iconVector = Icons.Filled.FolderOpen,
                    iconContentDescription = "Open Graph Icon",
                    buttonText = "Open Graph",
                )

                MainMenuTextButton(
                    onClick = {
                        val openResult = viewModel.openGraphRepository()
                        when (openResult) {
                            is Result.Error -> warnings = listOf("Error: ${openResult.error.type}.${openResult.error.description}")
                            is Result.Success -> {
                                selectedRepositoryFile = openResult.data
                                showOpenRepositoryDialog = true
                            }
                        }
                        if (warnings.isNotEmpty()) showOpenGraphErrorsDialog = true
                    },
                    iconVector = Icons.Filled.Storage,
                    iconContentDescription = "Open Repository Icon",
                    buttonText = "Open Repository",
                )

                MainMenuTextButton(
                    onClick = { },
                    iconVector = Icons.Filled.Analytics,
                    iconContentDescription = "Analytics Icon",
                    buttonText = "Analytics",
                )

                MainMenuTextButton(
                    onClick = { },
                    iconVector = Icons.Filled.Settings,
                    iconContentDescription = "Settings Icon",
                    buttonText = "Settings",
                )

                Spacer(Modifier.weight(1f))
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            ),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SlideMenuButton(onClick = { onMenuVisibilityChange(false) })
                    UserDirectoryButton()
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
                        .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SlideMenuButton(
                    onClick = { onMenuVisibilityChange(true) },
                    isReversed = true,
                )
            }
        }
    }

    if (showOpenGraphErrorsDialog) {
        OpenGraphErrors(
            onDismiss = { showOpenGraphErrorsDialog = false },
            warnings = warnings,
        )
    }

    if (showOpenRepositoryDialog) {
        OpenRepository(
            onDismiss = { showOpenRepositoryDialog = false },
            file = selectedRepositoryFile,
            onGraphSelected = { graphId ->
                viewModel.graphId = graphId
                val loadResult = viewModel.loadGraphFromFile(selectedRepositoryFile)
                if (loadResult is Result.Error) warnings = listOf("Error: ${loadResult.error.type}.${loadResult.error.description}")
                if (warnings.isNotEmpty()) showOpenGraphErrorsDialog = true
            },
        )
    }

    if (showSaveGraphAsDialog) {
        SaveGraphAs(
            onDismiss = { showSaveGraphAsDialog = false },
            viewModel = viewModel,
        )
    }

    if (showUserNotification) {
        UserNotification(
            onDismiss = { showUserNotification = false },
            title = "Save Error",
            message = saveError,
        )
    }

    if (showNewGraphDialog) {
        NewGraph(
            onDismiss = { showNewGraphDialog = false },
            viewModel = viewModel,
        )
    }
}
