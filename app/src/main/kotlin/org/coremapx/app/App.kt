package org.coremapx.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import mu.KotlinLogging
import org.coremapx.app.theme.AppTheme
import view.MainScreen
import viewmodel.MainScreenViewModel

private val logger = KotlinLogging.logger {}

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun <E : Comparable<E>, V : Comparable<V>> App(viewModel: MainScreenViewModel<E, V>) {
    logger.info("Rendering App composable")
    AppTheme {
        MainScreen(viewModel)
    }
}
