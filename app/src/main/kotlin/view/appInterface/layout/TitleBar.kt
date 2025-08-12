package view.appInterface.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SaveAs
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
import view.appInterface.dialog.NewGraph
import view.appInterface.dialog.OpenGraphErrors
import view.appInterface.dialog.OpenRepository
import view.appInterface.dialog.SaveGraphAs
import view.appInterface.dialog.UserNotification
import viewmodel.MainScreenViewModel
import java.io.File

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> TitleBar(
    onClose: () -> Unit,
    onMinimize: () -> Unit,
    onMaximize: () -> Unit,
    isMaximized: Boolean,
    viewModel: MainScreenViewModel<E, V>,
) {
    val titleBarHeight = config.states.titleBarHeight.value.dp
    val titleBarIconSize = config.states.titleBarIconSize.value.dp

    var showOpenGraphErrorsDialog by remember { mutableStateOf(false) }
    var showMenuButtons by remember { mutableStateOf(false) }
    var showFileMenu by remember { mutableStateOf(false) }
    var showSaveAsDialog by remember { mutableStateOf(false) }
    var showNewGraphDialog by remember { mutableStateOf(false) }
    var showOpenRepositoryDialog by remember { mutableStateOf(false) }
    var showUserNotification by remember { mutableStateOf(false) }
    var warnings by remember { mutableStateOf<List<String>>(emptyList()) }
    var userNotificationTitle by remember { mutableStateOf("") }
    var userNotificationMessage by remember { mutableStateOf("") }
    var selectedRepositoryFile by remember { mutableStateOf(File("")) }

    var showOpenSubMenu by remember { mutableStateOf(false) }

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
                .height(titleBarHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showMenuButtons) {
                IconButton(onClick = { showMenuButtons = false }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = LocalizationManager.states.ui.titleBarMenuIconDescription.value,
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.size(titleBarIconSize),
                    )
                }

                TextButton(onClick = { showFileMenu = true }) {
                    Text(
                        text = LocalizationManager.states.ui.titleBarFileButton.value,
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface,
                    )
                }
                DropdownMenu(
                    expanded = showFileMenu,
                    onDismissRequest = { showFileMenu = false },
                    modifier = Modifier.background(color = MaterialTheme.colors.background),
                ) {
                    DropdownMenuItem(
                        onClick = {
                            showFileMenu = false
                            showNewGraphDialog = true
                        },
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = LocalizationManager.states.ui.titleBarFileNewIconDescription.value,
                                tint = MaterialTheme.colors.primary,
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = LocalizationManager.states.ui.titleBarFileNewButton.value,
                                style = MaterialTheme.typography.button,
                            )
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            showOpenSubMenu = true
                        },
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.FolderOpen,
                                contentDescription = LocalizationManager.states.ui.titleBarFileOpenIconDescription.value,
                                tint = MaterialTheme.colors.primary,
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = LocalizationManager.states.ui.titleBarFileOpenButton.value,
                                style = MaterialTheme.typography.button,
                            )
                        }
                        DropdownMenu(
                            expanded = showOpenSubMenu,
                            onDismissRequest = { showOpenSubMenu = false },
                            modifier = Modifier.background(color = MaterialTheme.colors.background),
                        ) {
                            DropdownMenuItem(
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
                                    showOpenSubMenu = false
                                    showFileMenu = false
                                },
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Description,
                                        contentDescription = LocalizationManager.states.ui.titleBarFileOpenFileIconDescription.value,
                                        tint = MaterialTheme.colors.primary,
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = LocalizationManager.states.ui.titleBarFileOpenFileButton.value,
                                        style = MaterialTheme.typography.button,
                                    )
                                }
                            }
                            DropdownMenuItem(
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
                                            if (openResult.data == null) return@DropdownMenuItem
                                            selectedRepositoryFile = openResult.data
                                            showOpenRepositoryDialog = true
                                        }
                                    }
                                    if (warnings.isNotEmpty()) showOpenGraphErrorsDialog = true
                                    showOpenSubMenu = false
                                    showFileMenu = false
                                },
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Storage,
                                        contentDescription = LocalizationManager.states.ui.titleBarFileOpenRepositoryIconDescription.value,
                                        tint = MaterialTheme.colors.primary,
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = LocalizationManager.states.ui.titleBarFileOpenRepositoryButton.value,
                                        style = MaterialTheme.typography.button,
                                    )
                                }
                            }
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            if (viewModel.graphManager.graphPath == null) {
                                showSaveAsDialog = true
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
                            showFileMenu = false
                        },
                        enabled = viewModel.graphManager.isGraphActive,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Save,
                                contentDescription = LocalizationManager.states.ui.titleBarFileSaveIconDescription.value,
                                tint =
                                    if (viewModel.graphManager.isGraphActive) {
                                        MaterialTheme.colors.primary
                                    } else {
                                        MaterialTheme.colors.onSurface.copy(
                                            alpha = PrivateConfig.View.DISABLED_ALPHA,
                                        )
                                    },
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = LocalizationManager.states.ui.titleBarFileSaveButton.value,
                                style = MaterialTheme.typography.button,
                                color =
                                    if (viewModel.graphManager.isGraphActive) {
                                        MaterialTheme.colors.onSurface
                                    } else {
                                        MaterialTheme.colors.onSurface
                                            .copy(
                                                alpha = PrivateConfig.View.DISABLED_ALPHA,
                                            )
                                    },
                            )
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            showFileMenu = false
                            showSaveAsDialog = true
                        },
                        enabled = viewModel.graphManager.isGraphActive,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.SaveAs,
                                contentDescription = LocalizationManager.states.ui.titleBarFileSaveAsIconDescription.value,
                                tint =
                                    if (viewModel.graphManager.isGraphActive) {
                                        MaterialTheme.colors.primary
                                    } else {
                                        MaterialTheme.colors.onSurface.copy(
                                            alpha = PrivateConfig.View.DISABLED_ALPHA,
                                        )
                                    },
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = LocalizationManager.states.ui.titleBarFileSaveAsButton.value,
                                style = MaterialTheme.typography.button,
                                color =
                                    if (viewModel.graphManager.isGraphActive) {
                                        MaterialTheme.colors.onSurface
                                    } else {
                                        MaterialTheme.colors.onSurface
                                            .copy(
                                                alpha = PrivateConfig.View.DISABLED_ALPHA,
                                            )
                                    },
                            )
                        }
                    }
                }
                TextButton(onClick = { }) {
                    Text(
                        text = LocalizationManager.states.ui.titleBarSettingsButton.value,
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface,
                    )
                }
                TextButton(onClick = { }) {
                    Text(
                        text = LocalizationManager.states.ui.titleBarHelpButton.value,
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface,
                    )
                }
            } else {
                IconButton(onClick = { showMenuButtons = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = LocalizationManager.states.ui.titleBarMenuIconDescription.value,
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.size(titleBarIconSize),
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(end = 4.dp)) {
            IconButton(onClick = onMinimize) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = LocalizationManager.states.ui.titleBarMinimizeIconDescription.value,
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(titleBarIconSize),
                )
            }
            IconButton(onClick = onMaximize) {
                Icon(
                    imageVector = if (isMaximized) Icons.Filled.FullscreenExit else Icons.Filled.Fullscreen,
                    contentDescription =
                        if (isMaximized) {
                            LocalizationManager.states.ui.titleBarRecoverIconDescription.value
                        } else {
                            LocalizationManager.states.ui.titleBarMaximizeIconDescription.value
                        },
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(titleBarIconSize),
                )
            }
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = LocalizationManager.states.ui.titleBarCloseIconDescription.value,
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(titleBarIconSize),
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

    if (showSaveAsDialog) {
        SaveGraphAs(
            graphName = viewModel.graphManager.graphName,
            onDismiss = { showSaveAsDialog = false },
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
                viewModel.graphManager.graphId = 0
                viewModel.graphManager.updateGraph(newGraphData.graph)
            },
        )
    }
}
