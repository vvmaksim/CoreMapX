package view.appInterface.layout

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
import androidx.compose.foundation.layout.width
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
import model.database.sqlite.createDatabase
import model.database.sqlite.repository.EdgeRepository
import model.database.sqlite.repository.GraphRepository
import model.database.sqlite.repository.VertexRepository
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.graph.GraphDatabase
import view.appInterface.button.MainMenuTextButton
import view.appInterface.button.SlideMenuButton
import view.appInterface.button.UserDirectoryButton
import view.appInterface.dialog.Analytics
import view.appInterface.dialog.NewGraph
import view.appInterface.dialog.OpenGraphErrors
import view.appInterface.dialog.OpenRepository
import view.appInterface.dialog.SaveGraphAs
import view.appInterface.dialog.Settings
import view.appInterface.dialog.UserNotification
import view.appInterface.icon.Logo
import viewmodel.MainScreenViewModel
import viewmodel.visualizationStrategy.CircularStrategy
import viewmodel.visualizationStrategy.ForceDirectedStrategy
import viewmodel.visualizationStrategy.RandomStrategy
import viewmodel.visualizationStrategy.VisualizationStrategy
import java.io.File

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainMenu(
    isMenuVisible: Boolean,
    onMenuVisibilityChange: (Boolean) -> Unit,
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier,
) {
    val animationDuration = config.states.animationDuration.value

    var showOpenGraphErrorsDialog by remember { mutableStateOf(false) }
    var showOpenRepositoryDialog by remember { mutableStateOf(false) }
    var showSaveGraphAsDialog by remember { mutableStateOf(false) }
    var showUserNotification by remember { mutableStateOf(false) }
    var showNewGraphDialog by remember { mutableStateOf(false) }
    var showAnalyticsDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var warnings by remember { mutableStateOf<List<String>>(emptyList()) }
    var userNotificationTitle by remember { mutableStateOf("") }
    var userNotificationMessage by remember { mutableStateOf("") }
    var selectedRepositoryFile by remember { mutableStateOf(File("")) }

    Box(
        modifier = modifier,
    ) {
        AnimatedVisibility(
            visible = isMenuVisible,
            enter =
                fadeIn(animationSpec = tween(animationDuration)) +
                    slideInHorizontally(
                        animationSpec =
                            tween(
                                animationDuration,
                            ),
                    ),
            exit =
                fadeOut(animationSpec = tween(animationDuration)) +
                    slideOutHorizontally(
                        animationSpec =
                            tween(
                                animationDuration,
                            ),
                    ),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colors.background)
                        .padding(8.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Logo(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.onSurface,
                        size = PrivateConfig.MainMenu.LOGO_SIZE.dp,
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "CoreMapX",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                }

                MainMenuTextButton(
                    onClick = { showNewGraphDialog = true },
                    iconVector = Icons.Filled.Add,
                    iconContentDescription = LocalizationManager.states.ui.mainMenuNewGraphIconDescription.value,
                    buttonText = LocalizationManager.states.ui.mainMenuButtonNewGraph.value,
                )

                MainMenuTextButton(
                    onClick = {
                        if (viewModel.graphManager.graphPath == null) {
                            showSaveGraphAsDialog = true
                        } else {
                            val saveResult = viewModel.graphManager.saveGraph()
                            if (saveResult is Result.Error) {
                                userNotificationMessage =
                                    LocalizationFormatter.getErrorMessage(
                                        startString = LocalizationManager.states.ui.errorBasicString.value,
                                        errorType = saveResult.error.type,
                                        errorDescription = saveResult.error.description,
                                    )

                                userNotificationTitle = LocalizationManager.states.dialogs.userNotificationSaveError.value
                                showUserNotification = true
                            }
                        }
                    },
                    iconVector = Icons.Filled.Save,
                    iconContentDescription = LocalizationManager.states.ui.mainMenuSaveIconDescription.value,
                    buttonText = LocalizationManager.states.ui.mainMenuButtonSave.value,
                    isEnabled = viewModel.graphManager.isGraphActive,
                )

                MainMenuTextButton(
                    onClick = { showSaveGraphAsDialog = true },
                    iconVector = Icons.Filled.SaveAs,
                    iconContentDescription = LocalizationManager.states.ui.mainMenuSaveAsIconDescription.value,
                    buttonText = LocalizationManager.states.ui.mainMenuButtonSaveAs.value,
                    isEnabled = viewModel.graphManager.isGraphActive,
                )

                MainMenuTextButton(
                    onClick = {
                        val loadResult = viewModel.graphManager.openGraphFile()
                        warnings =
                            when (loadResult) {
                                is Result.Success -> loadResult.data
                                is Result.Error ->
                                    listOf(
                                        LocalizationFormatter.getErrorMessage(
                                            startString = LocalizationManager.states.ui.errorBasicString.value,
                                            errorType = loadResult.error.type,
                                            errorDescription = loadResult.error.description,
                                        ),
                                    )
                            }
                        if (warnings.isNotEmpty()) showOpenGraphErrorsDialog = true
                    },
                    iconVector = Icons.Filled.FolderOpen,
                    iconContentDescription = LocalizationManager.states.ui.mainMenuOpenGraphIconDescription.value,
                    buttonText = LocalizationManager.states.ui.mainMenuButtonOpenGraph.value,
                )

                MainMenuTextButton(
                    onClick = {
                        val openResult = viewModel.graphManager.openGraphRepository()
                        when (openResult) {
                            is Result.Error ->
                                warnings =
                                    listOf(
                                        LocalizationFormatter.getErrorMessage(
                                            startString = LocalizationManager.states.ui.errorBasicString.value,
                                            errorType = openResult.error.type,
                                            errorDescription = openResult.error.description,
                                        ),
                                    )

                            is Result.Success -> {
                                selectedRepositoryFile = openResult.data
                                showOpenRepositoryDialog = true
                            }
                        }
                        if (warnings.isNotEmpty()) showOpenGraphErrorsDialog = true
                    },
                    iconVector = Icons.Filled.Storage,
                    iconContentDescription = LocalizationManager.states.ui.mainMenuOpenRepositoryIconDescription.value,
                    buttonText = LocalizationManager.states.ui.mainMenuButtonOpenRepository.value,
                )

                MainMenuTextButton(
                    onClick = { showAnalyticsDialog = true },
                    iconVector = Icons.Filled.Analytics,
                    iconContentDescription = LocalizationManager.states.ui.mainMenuAnalyticsIconDescription.value,
                    buttonText = LocalizationManager.states.ui.mainMenuButtonAnalytics.value,
                )

                MainMenuTextButton(
                    onClick = { showSettingsDialog = true },
                    iconVector = Icons.Filled.Settings,
                    iconContentDescription = LocalizationManager.states.ui.mainMenuSettingsIconDescription.value,
                    buttonText = LocalizationManager.states.ui.mainMenuButtonSettings.value,
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
            enter =
                fadeIn(animationSpec = tween(animationDuration)) +
                    slideInHorizontally(
                        animationSpec =
                            tween(
                                animationDuration,
                            ),
                    ),
            exit =
                fadeOut(animationSpec = tween(animationDuration)) +
                    slideOutHorizontally(
                        animationSpec =
                            tween(
                                animationDuration,
                            ),
                    ),
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
        val database: GraphDatabase = createDatabase(selectedRepositoryFile.absolutePath)
        val graphs = GraphRepository(database).getAllGraphs()
        OpenRepository(
            onDismiss = { showOpenRepositoryDialog = false },
            graphs = graphs,
            onGraphSelected = { graphId ->
                viewModel.graphManager.graphId = graphId
                val loadResult = viewModel.graphManager.loadGraphFromFile(selectedRepositoryFile)
                if (loadResult is Result.Error) {
                    warnings =
                        listOf(
                            LocalizationFormatter.getErrorMessage(
                                startString = LocalizationManager.states.ui.errorBasicString.value,
                                errorType = loadResult.error.type,
                                errorDescription = loadResult.error.description,
                            ),
                        )
                }
                if (warnings.isNotEmpty()) showOpenGraphErrorsDialog = true
            },
            getCountVerticesByGraph = { graphId -> VertexRepository(database).getVerticesByGraph(graphId).size.toLong() },
            getCountEdgesByGraph = { graphId -> EdgeRepository(database).getEdgesByGraph(graphId).size.toLong() },
        )
    }

    if (showSaveGraphAsDialog) {
        SaveGraphAs(
            graphName = viewModel.graphManager.graphName,
            onDismiss = { showSaveGraphAsDialog = false },
            onSave = { savedGraphDetails ->
                val saveResult =
                    viewModel.graphManager.saveGraph(
                        fileName = savedGraphDetails.fileName,
                        directoryPath = savedGraphDetails.directoryPath,
                        fileFormat = savedGraphDetails.fileFormat,
                    )
                userNotificationMessage =
                    when (saveResult) {
                        is Result.Error -> {
                            userNotificationTitle = LocalizationManager.states.dialogs.userNotificationSaveError.value
                            LocalizationFormatter.getErrorMessage(
                                startString = LocalizationManager.states.ui.errorBasicString.value,
                                errorType = saveResult.error.type,
                                errorDescription = saveResult.error.description,
                            )
                        }

                        is Result.Success -> {
                            userNotificationTitle = LocalizationManager.states.dialogs.userNotificationSaveSuccess.value
                            LocalizationFormatter.getSaveGraphSuccessMessage(
                                startString = LocalizationManager.states.dialogs.userNotificationSaveSuccessMessage.value,
                                fileName = savedGraphDetails.fileName,
                                directoryPath = savedGraphDetails.directoryPath,
                                fileFormat = savedGraphDetails.fileFormat.name,
                            )
                        }
                    }
                showUserNotification = true
            },
        )
    }

    if (showUserNotification) {
        UserNotification(
            onDismiss = { showUserNotification = false },
            title = userNotificationTitle,
            message = userNotificationMessage,
        )
    }

    if (showNewGraphDialog) {
        NewGraph(
            onDismiss = { showNewGraphDialog = false },
            onCreate = { newGraphData ->
                viewModel.graphManager.graphName = newGraphData.graphName
                viewModel.graphManager.graphAuthor = "None"
                viewModel.graphManager.graphPath = null
                viewModel.graphManager.graphFormat = null
                viewModel.graphManager.updateGraph(newGraphData.graph)
            },
        )
    }

    if (showSettingsDialog) {
        Settings(
            onDismiss = { showSettingsDialog = false },
        )
    }

    if (showAnalyticsDialog) {
        Analytics(
            onDismiss = { showAnalyticsDialog = false },
            onStrategyUpdate = { newStrategy: VisualizationStrategy ->
                viewModel.graphManager.updateLayoutStrategy(newStrategy)
            },
            selectedLayoutStrategy =
                when (viewModel.graphManager.layoutStrategy.value) {
                    is RandomStrategy -> "Random"
                    is CircularStrategy -> "Circular"
                    is ForceDirectedStrategy<*, *> -> "Force-Directed"
                    else -> "Random"
                },
        )
    }
}
