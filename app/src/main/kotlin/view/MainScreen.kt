package view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import view.interfaceElements.MainMenu
import view.interfaceElements.WorkSpace
import viewmodel.MainScreenViewModel

@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainScreen(viewModel: MainScreenViewModel<E, V>) {
    var isMenuVisible by remember { mutableStateOf(true) }

    val mainMenuWidth = (config.getIntValue("mainMenuWidth") ?: 0).dp

    Box(modifier = Modifier.fillMaxSize()) {
        WorkSpace(
            viewModel,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(start = mainMenuWidth),
        )

        MainMenu(
            isMenuVisible = isMenuVisible,
            onMenuVisibilityChange = { isMenuVisible = it },
            viewModel = viewModel,
            modifier =
                Modifier
                    .fillMaxHeight()
                    .width(mainMenuWidth)
                    .align(Alignment.CenterStart),
        )
    }
}
