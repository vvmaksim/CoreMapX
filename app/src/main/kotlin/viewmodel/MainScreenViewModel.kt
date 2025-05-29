package viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import model.commands.classes.Commands
import model.fileHandler.ConvertModes
import model.fileHandler.FileDialogManager
import model.fileHandler.FileExtensions
import model.fileHandler.Parser
import model.fileHandler.converters.Converter
import model.graph.classes.DirectedUnweightedGraph
import model.graph.classes.DirectedWeightedGraph
import model.graph.classes.UndirectedUnweightedGraph
import model.graph.classes.UndirectedWeightedGraph
import model.graph.dataClasses.WeightedEdge
import model.graph.interfaces.Graph
import model.ir.GraphIR
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
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
        val scaledLimit = canvasLimit * _scale.value
        _offsetX.value = (_offsetX.value + compensatedDx).coerceIn(-scaledLimit, scaledLimit)
        _offsetY.value = (_offsetY.value + compensatedDy).coerceIn(-scaledLimit, scaledLimit)
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

    fun openGraphFile(): Result<List<String>> {
        val file = FileDialogManager.showOpenFileDialog(directory = "${baseUserDirPath}/data/graphs")
            ?: return Result.Error(FileErrors.ErrorReadingFile("You have to select one file"))
        return loadGraphFromFile(file)
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
            when (file.extension) {
                "graph" -> FileExtensions.GRAPH
                "json" -> FileExtensions.JSON
                else -> FileExtensions.GRAPH
            }
        updateGraph(newGraph)
        return Result.Success(warnings)
    }

    fun saveGraph(
        fileName: String = graphName,
        directoryPath: String? = graphPath,
        fileFormat: FileExtensions? = graphFormat,
    ): Result<String> {
        try {
            if (directoryPath == null) return Result.Error(FileErrors.InvalidParameter("directoryPath", "This parameter cannot be null"))
            if (fileFormat == null) return Result.Error(FileErrors.InvalidParameter("fileFormat", "This parameter cannot be null"))

            val isDirected =
                when (graph.value) {
                    is UndirectedWeightedGraph<V> -> false
                    is UndirectedUnweightedGraph<V> -> false
                    is DirectedWeightedGraph<V> -> true
                    is DirectedUnweightedGraph<V> -> true
                    else -> false
                }
            val isWeighted =
                when (graph.value) {
                    is UndirectedWeightedGraph<V> -> true
                    is UndirectedUnweightedGraph<V> -> false
                    is DirectedWeightedGraph<V> -> true
                    is DirectedUnweightedGraph<V> -> false
                    else -> false
                }

            val ir =
                StringBuilder().apply {
                    append("Info:\n")
                    append("name=$graphName\n")
                    append("author=$graphAuthor\n")
                    append("isDirected=$isDirected\n")
                    append("isWeighted=$isWeighted\n")
                    append("\nGraph:\n")
                    graph.value?.vertices?.forEach { _, vertex ->
                        append("add vertex ${vertex.id} ${vertex.label}\n")
                    }
                    if (isWeighted) {
                        graph.value?.edges?.forEach { _, edge ->
                            append("add edge ${edge.from.id} ${edge.to.id} ${(edge as WeightedEdge).weight}\n")
                        }
                    } else {
                        graph.value?.edges?.forEach { _, edge ->
                            append("add edge ${edge.from.id} ${edge.to.id}\n")
                        }
                    }
                }

            when (fileFormat) {
                FileExtensions.GRAPH -> {
                    val path = if (directoryPath == graphPath) directoryPath else "$directoryPath/$fileName.graph"
                    val fileIR = File(path)
                    fileIR.writeText(ir.toString())
                    return Result.Success("File was saved as GRAPH")
                }
                FileExtensions.JSON -> {
                    val tempFileIR = File("$baseUserDirPath/data/temp/$fileName.graph")
                    tempFileIR.writeText(ir.toString())
                    val convertResult = Converter.convert(tempFileIR, FileExtensions.JSON, ConvertModes.SAVE)
                    when (convertResult) {
                        is Result.Error -> return convertResult
                        is Result.Success -> {
                            val path = if (directoryPath == graphPath) directoryPath else "$directoryPath/$fileName.json"
                            val renameToResult = convertResult.data.renameTo(File(path))
                            if (!renameToResult) {
                                return Result.Error(
                                    FileErrors.ConverterError("The file could not be moved from the temp directory"),
                                )
                            }
                        }
                    }
                    return Result.Success("File was saved as JSON")
                }
            }
        } catch (ex: Exception) {
            return Result.Error(FileErrors.ErrorSavingFile(ex.toString()))
        }
    }
}
