package org.coremapx.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import mu.KotlinLogging
import org.coremapx.app.userDirectory.ConfigRepository
import org.coremapx.app.userDirectory.UserDirectory
import view.MainScreen
import viewmodel.MainScreenViewModel
import java.awt.Dimension

private val logger = KotlinLogging.logger {}

@Composable
@Preview
fun App() {
    UserDirectory.init()
    ConfigRepository()
    logger.info("Rendering App composable")
    MaterialTheme {
        MainScreen(MainScreenViewModel<Int, Int>())
    }
}

fun main() =
    application {
        logger.info("Started CoreMapX app")
        Window(
            onCloseRequest = {
                logger.info("Closed CoreMapX app")
                exitApplication()
            },
            title = "CoreMapX",
        ) {
            window.minimumSize = Dimension(1280, 720)
            App()
        }
    }
