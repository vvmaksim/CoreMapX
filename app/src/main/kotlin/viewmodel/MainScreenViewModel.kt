package viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import model.graphs.interfaces.Graph
import org.coremapx.app.config
import viewmodel.graph.GraphViewModel
import viewmodel.visualizationStrategies.RandomStrategy
import viewmodel.visualizationStrategies.VisualizationStrategy

class MainScreenViewModel<E : Comparable<E>, V : Comparable<V>>(
    private val visualizationStrategy: VisualizationStrategy = RandomStrategy(),
) {
    val canvasLimit = config.getFloatValue("canvasLimit") ?: 0f
    val screenWidth = config.getDoubleValue("mainScreenStartWidth") ?: 0.0
    val screenHeight = config.getDoubleValue("mainScreenStartHeight") ?: 0.0

    private var _showVerticesLabels = mutableStateOf(false)
    val showVerticesLabels: State<Boolean>
        get() = _showVerticesLabels

    fun setShowVerticesLabels(value: Boolean) {
        _showVerticesLabels.value = value
    }

    val isGraphActive: Boolean
        get() = _graph != null

    private var _graph: Graph<E, V>? = null
    val graph: Graph<E, V>?
        get() = _graph

    var graphViewModel: GraphViewModel<E, V>? = null

    private var _offsetX = mutableStateOf(0f)
    val offsetX: State<Float>
        get() = _offsetX

    private var _offsetY = mutableStateOf(0f)
    val offsetY: State<Float>
        get() = _offsetY

    fun moveCanvas(
        dx: Float,
        dy: Float,
    ) {
        _offsetX.value = (_offsetX.value + dx).coerceIn(-canvasLimit, canvasLimit)
        _offsetY.value = (_offsetY.value + dy).coerceIn(-canvasLimit, canvasLimit)
    }

    fun resetCanvas() {
        _offsetX.value = 0f
        _offsetY.value = 0f
    }

    fun resetGraphView() {
        graphViewModel?.let {
            visualizationStrategy.place(screenWidth, screenHeight, it.vertices)
        }
    }

    fun updateGraph(newGraph: Graph<E, V>) {
        _graph = newGraph
        graphViewModel = GraphViewModel(newGraph, _showVerticesLabels)
        visualizationStrategy.place(screenWidth, screenHeight, graphViewModel?.vertices)
    }
}
