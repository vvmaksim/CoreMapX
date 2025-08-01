package view.appInterface.layout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import viewmodel.MainScreenViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> WorkSpace(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier,
) {
    if (viewModel.graphManager.isGraphActive) {
        MainWorkArea(
            viewModel = viewModel,
            modifier = modifier,
        )
    } else {
        Spacer(modifier = modifier)
    }
}
