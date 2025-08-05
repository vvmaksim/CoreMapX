package org.coremapx.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import org.coremapx.app.AppLogger.logInfo
import org.coremapx.app.theme.AppTheme
import view.MainScreen
import viewmodel.MainScreenViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun <E : Comparable<E>, V : Comparable<V>> App(viewModel: MainScreenViewModel<E, V>) {
    logInfo("Rendering App composable")
    AppTheme {
        MainScreen(viewModel)
    }
}
