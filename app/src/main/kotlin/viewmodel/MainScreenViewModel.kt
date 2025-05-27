package viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import model.commands.classes.Commands
import model.fileHandler.FileExtensions
import model.fileHandler.Parser
import model.graph.classes.DirectedUnweightedGraph
import model.graph.classes.DirectedWeightedGraph
import model.graph.classes.UndirectedUnweightedGraph
import model.graph.classes.UndirectedWeightedGraph
import model.graph.interfaces.Graph
import model.ir.GraphIR
import model.result.Result
import org.coremapx.app.config
import viewmodel.graph.GraphViewModel
import viewmodel.visualizationStrategies.RandomStrategy
import viewmodel.visualizationStrategies.VisualizationStrategy
import java.io.File

class MainScreenViewModel<E : Comparable<E>, V : Comparable<V>>(
    private val visualizationStrategy: VisualizationStrategy = RandomStrategy(),
) {
    val canvasLimit = config.getFloatValue("canvasLimit") ?: 0f
    val graphLayoutHeight = config.getDoubleValue("graphLayoutHeight") ?: 0.0
    val graphLayoutWidth = config.getDoubleValue("graphLayoutWidth") ?: 0.0

    private var _showVerticesLabels = mutableStateOf(false)
    val showVerticesLabels: State<Boolean>
        get() = _showVerticesLabels

    fun setShowVerticesLabels(value: Boolean) {
        _showVerticesLabels.value = value
    }

    val isGraphActive: Boolean
        get() = _graph.value != null

    private var _graph = mutableStateOf<Graph<E, V>?>(null)
    val graph: State<Graph<E, V>?>
        get() = _graph

    var graphViewModel: GraphViewModel<E, V>? = null

    var graphName: String = "None"

    var graphAuthor: String = "None"

    var graphPath: String? = null

    var graphFormat: FileExtensions? = null

    private var _offsetX = mutableStateOf(0f)
    val offsetX: State<Float>
        get() = _offsetX

    private var _offsetY = mutableStateOf(0f)
    val offsetY: State<Float>
        get() = _offsetY

    private var _scale = mutableStateOf(1f)
    val scale: State<Float>
        get() = _scale

    fun moveCanvas(
        dx: Float,
        dy: Float,
    ) {
        val compensatedDx = dx * _scale.value
        val compensatedDy = dy * _scale.value

        _offsetX.value = (_offsetX.value + compensatedDx).coerceIn(-canvasLimit, canvasLimit)
        _offsetY.value = (_offsetY.value + compensatedDy).coerceIn(-canvasLimit, canvasLimit)
    }

    fun zoomCanvas(delta: Float) {
        val zoomFactor = 0.5f
        val oldScale = _scale.value
        val newScale = (oldScale * (1f + delta * zoomFactor)).coerceIn(0.5f, 5f)

        val scaleRatio = newScale / oldScale
        _offsetX.value = _offsetX.value * scaleRatio
        _offsetY.value = _offsetY.value * scaleRatio

        _scale.value = newScale
    }

    fun resetCanvas() {
        _offsetX.value = 0f
        _offsetY.value = 0f
        _scale.value = 1f
    }

    fun resetGraphView() {
        graphViewModel?.let {
            visualizationStrategy.place(graphLayoutWidth, graphLayoutHeight, it.vertices)
        }
    }

    fun updateGraph(newGraph: Graph<E, V>) {
        _graph.value = newGraph
        graphViewModel = GraphViewModel(newGraph, _showVerticesLabels)
        visualizationStrategy.place(graphLayoutWidth, graphLayoutHeight, graphViewModel?.vertices)
    }

    fun loadGraphFromFile(file: File): Result<List<String>> {
        val parseResult = Parser.parse(file)
        val graphIR: GraphIR
        when (parseResult) {
            is Result.Error -> return parseResult
            is Result.Success -> graphIR = parseResult.data
        }
        val isDirected = graphIR.isDirected
        val isWeighted = graphIR.isWeighted
        val warnings = graphIR.warnings.toMutableList()
        val commandResults = graphIR.commands
        val newGraph: Graph<E, V> =
            when {
                isDirected && isWeighted -> DirectedWeightedGraph<V>()
                isDirected && !isWeighted -> DirectedUnweightedGraph<V>()
                !isDirected && isWeighted -> UndirectedWeightedGraph<V>()
                else -> UndirectedUnweightedGraph<V>()
            } as Graph<E, V>
        commandResults.forEach { commandResult ->
            val executeResult = Commands((commandResult as Result.Success).data, newGraph, mutableListOf()).execute()
            if (executeResult is Result.Error) return executeResult
        }
        graphName = graphIR.name
        graphAuthor = graphIR.author
        graphPath = file.absolutePath
        graphFormat =
            when(file.extension) {
                "graph" -> FileExtensions.GRAPH
                "json" -> FileExtensions.JSON
                else -> FileExtensions.GRAPH
            }
        updateGraph(newGraph)
        return Result.Success(warnings)
    }
}
