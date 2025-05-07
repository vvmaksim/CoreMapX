package viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graphs.interfaces.Graph
import viewmodel.graph.GraphViewModel
import viewmodel.visualizationStrategies.RandomStrategy
import viewmodel.visualizationStrategies.VisualizationStrategy

class MainScreenViewModel<E : Comparable<E>, V : Comparable<V>>(
    private val visualizationStrategy: VisualizationStrategy = RandomStrategy(),
) {
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
        _offsetX.value = (_offsetX.value + dx).coerceIn(-2000f, 2000f)
        _offsetY.value = (_offsetY.value + dy).coerceIn(-2000f, 2000f)
    }

    fun resetCanvas() {
        _offsetX.value = 0f
        _offsetY.value = 0f
    }

    fun resetGraphView() {
        graphViewModel?.let {
            visualizationStrategy.place(1280.0, 720.0, it.vertices)
            it.vertices.forEach { vertex -> vertex.color = Color.Gray }
        }
    }

    fun updateGraph(newGraph: Graph<E, V>) {
        _graph = newGraph
        graphViewModel = GraphViewModel(newGraph, _showVerticesLabels)
        visualizationStrategy.place(1280.0, 720.0, graphViewModel?.vertices)
    }
}
