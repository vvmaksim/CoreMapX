package view.appInterface.workspace

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import viewmodel.MainScreenViewModel
import viewmodel.visualizationStrategy.AnimatedVisualizationStrategy
import viewmodel.visualizationStrategy.AnimationParameters

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> ForceDirectedMenu(
    viewModel: MainScreenViewModel<E, V>,
    modifier: Modifier = Modifier,
) {
    val animatedStrategy = viewModel.graphManager.layoutStrategy.value as? AnimatedVisualizationStrategy<E, V>
    animatedStrategy?.let { animatedStrategy: AnimatedVisualizationStrategy<E, V> ->
        val params = animatedStrategy.getParameters()
        var iterations by remember { mutableStateOf(params.iterations.toFloat()) }
        var area by remember { mutableStateOf(params.area.toFloat()) }
        var gravity by remember { mutableStateOf(params.gravity.toFloat()) }
        var speed by remember { mutableStateOf(params.speed.toFloat()) }
        val isRunning = animatedStrategy.isRunning()

        Card(
            modifier = modifier,
            elevation = 16.dp,
            shape = MaterialTheme.shapes.large,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = LocalizationManager.states.ui.forceDirectedMenuTitle.value,
                    style = MaterialTheme.typography.h6,
                )
                Row {
                    Button(onClick = {
                        animatedStrategy.setParameters(
                            AnimationParameters(
                                iterations = iterations.toInt(),
                                area = area.toDouble(),
                                gravity = gravity.toDouble(),
                                speed = speed.toDouble(),
                            ),
                        )
                        viewModel.graphManager.resetGraphView()
                    }) {
                        Text(LocalizationManager.states.ui.forceDirectedMenuApply.value)
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        if (isRunning) {
                            animatedStrategy.stopAnimation()
                        } else {
                            viewModel.graphManager.resetGraphView()
                        }
                    }) {
                        Text(
                            text =
                                if (isRunning) {
                                    LocalizationManager.states.ui.forceDirectedMenuStop.value
                                } else {
                                    LocalizationManager.states.ui.forceDirectedMenuStart.value
                                },
                        )
                    }
                }
                Text(
                    text =
                        LocalizationFormatter.getStringWithOneNumber(
                            startString = LocalizationManager.states.ui.forceDirectedMenuIterations.value,
                            number = iterations.toLong(),
                        ),
                )
                Slider(
                    value = iterations,
                    onValueChange = { iterations = it },
                    valueRange = PrivateConfig.LayoutStrategies.ForceDirected.iterationsRange,
                )
                Text(
                    text =
                        LocalizationFormatter.getStringWithOneNumber(
                            startString = LocalizationManager.states.ui.forceDirectedMenuArea.value,
                            number = area.toLong(),
                        ),
                )
                Slider(
                    value = area,
                    onValueChange = { area = it },
                    valueRange = PrivateConfig.LayoutStrategies.ForceDirected.areaRange,
                )
                Text(
                    text =
                        LocalizationFormatter.getStringWithOneNumber(
                            startString = LocalizationManager.states.ui.forceDirectedMenuGravity.value,
                            number = gravity,
                        ),
                )
                Slider(
                    value = gravity,
                    onValueChange = { gravity = it },
                    valueRange = PrivateConfig.LayoutStrategies.ForceDirected.gravityRange,
                )
                Text(
                    text =
                        LocalizationFormatter.getStringWithOneNumber(
                            startString = LocalizationManager.states.ui.forceDirectedMenuSpeed.value,
                            number = speed,
                        ),
                )
                Slider(
                    value = speed,
                    onValueChange = { speed = it },
                    valueRange = PrivateConfig.LayoutStrategies.ForceDirected.speedRange,
                )
            }
        }
    }
}
