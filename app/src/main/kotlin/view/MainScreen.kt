package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import extensions.canvasBackground
import org.coremapx.app.config
import view.interfaceElements.MainMenu
import view.interfaceElements.WorkSpace
import viewmodel.MainScreenViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> MainScreen(viewModel: MainScreenViewModel<E, V>) {
    var isMenuVisible by remember { mutableStateOf(true) }

    val mainMenuWidth = config.states.mainMenuWidth.value.dp

    Box(modifier = Modifier.fillMaxSize()) {
        WorkSpace(
            viewModel,
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.canvasBackground)
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
