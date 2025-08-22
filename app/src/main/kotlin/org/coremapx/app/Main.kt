package org.coremapx.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.zIndex
import org.coremapx.app.AppLogger.logAppShutdown
import org.coremapx.app.AppLogger.logAppStartup
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigRepository
import view.appInterface.layout.TitleBar
import viewmodel.MainScreenViewModel
import java.awt.Dimension

fun main() =
    application {
        logAppStartup()
        val startScreenWidth = ConfigRepository.states.mainScreenStartWidth.value
        val startScreenHeight = ConfigRepository.states.mainScreenStartHeight.value
        val windowState = rememberWindowState(width = startScreenWidth.dp, height = startScreenHeight.dp)
        val startWindowPlacement = ConfigRepository.states.startWindowPlacement.value
        val viewModel = MainScreenViewModel<Long, Long>()
        when (startWindowPlacement) {
            "FullScreen" -> windowState.placement = WindowPlacement.Fullscreen
            "Floating" -> windowState.placement = WindowPlacement.Floating
            "Maximized" -> windowState.placement = WindowPlacement.Maximized
        }
        val language by ConfigRepository.states.language
        LaunchedEffect(language) {
            LocalizationManager.updateLanguage(language)
        }
        Window(
            onCloseRequest = {
                logAppShutdown()
                exitApplication()
            },
            title = "CoreMapX",
            undecorated = true,
            state = windowState,
        ) {
            val titleBarHeight = ConfigRepository.states.titleBarHeight.value.dp
            var isMaximized by remember { mutableStateOf(windowState.placement == WindowPlacement.Maximized) }
            window.minimumSize = Dimension(startScreenWidth, startScreenHeight)

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(top = titleBarHeight),
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        App(viewModel)
                    }
                }

                WindowDraggableArea(modifier = Modifier.zIndex(1f)) {
                    AppTheme {
                        TitleBar(
                            onClose = {
                                logAppShutdown()
                                exitApplication()
                            },
                            onMinimize = {
                                logDebug("Click on minimize button")
                                windowState.isMinimized = true
                            },
                            onMaximize = {
                                isMaximized = !isMaximized
                                windowState.placement =
                                    if (isMaximized) {
                                        logDebug("Click on maximize button")
                                        WindowPlacement.Maximized
                                    } else {
                                        logDebug("Click on floating button")
                                        WindowPlacement.Floating
                                    }
                            },
                            isMaximized = isMaximized,
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }
