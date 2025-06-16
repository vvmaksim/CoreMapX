package viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import model.command.`class`.Commands
import model.database.sqlite.createDatabase
import model.database.sqlite.repository.EdgeRepository
import model.database.sqlite.repository.GraphRepository
import model.database.sqlite.repository.VertexRepository
import model.fileHandler.ConvertModes
import model.fileHandler.FileDialogManager
import model.fileHandler.FileExtensions
import model.fileHandler.Parser
import model.fileHandler.converter.Converter
import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.dataClasses.WeightedEdge
import model.graph.interfaces.Graph
import model.ir.GraphIR
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import orgcoremapxapp.Graphs
import viewmodel.graph.GraphViewModel
import viewmodel.visualizationStrategy.RandomStrategy
import viewmodel.visualizationStrategy.VisualizationStrategy
import java.io.File

class MainScreenViewModel<E : Comparable<E>, V : Comparable<V>>(
    private val visualizationStrategy: VisualizationStrategy = RandomStrategy(),
) {
    val canvasLimit = config.states.canvasLimit.value
    val graphLayoutHeight =
        config.states.graphLayoutHeight.value
            .toDouble()
    val graphLayoutWidth =
        config.states.graphLayoutWidth.value
            .toDouble()

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

    var graphId: Long? = null

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
        val newScale = (oldScale * (1f + delta * zoomFactor)).coerceIn(0.1f, 5f)

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
        val file =
            FileDialogManager.showOpenFileDialog(directory = "$baseUserDirPath/data/graphs")
                ?: return Result.Error(FileErrors.ErrorReadingFile("You have to select one file"))
        return loadGraphFromFile(file)
    }

    fun openGraphRepository(): Result<File> {
        val repository =
            FileDialogManager.showOpenFileDialog(
                directory = "$baseUserDirPath/data/graphs",
                title = "Select graph repository",
            )
                ?: return Result.Error(FileErrors.ErrorReadingFile("You have to select repository"))
        return Result.Success(repository)
    }

    fun loadGraphFromFile(file: File): Result<List<String>> {
        val parseResult = Parser.parse(file, graphId)
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
                "db" -> FileExtensions.SQL
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
                    val convertResult = Converter.convert(tempFileIR, FileExtensions.JSON, ConvertModes.SAVE, graphId)
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
                FileExtensions.SQL -> {
                    var graphId =
                        graphId
                            ?: return Result.Error(FileErrors.ErrorSavingFile("graphId can not be null"))
                    val path = if (directoryPath == graphPath) directoryPath else "$directoryPath/$fileName.db"
                    val database = createDatabase(path)

                    val graphRepository = GraphRepository(database)
                    when (graphRepository.getAllGraphs()) {
                        emptyList<Graphs>() ->
                            graphId =
                                graphRepository.insertGraph(
                                    name = graphName,
                                    author = graphAuthor,
                                    isDirected = isDirected,
                                    isWeighted = isWeighted,
                                )
                        else ->
                            GraphRepository(database).updateGraphById(
                                graphId = graphId,
                                name = graphName,
                                author = graphAuthor,
                                isDirected = isDirected,
                                isWeighted = isWeighted,
                            )
                    }

                    val vertexRepository = VertexRepository(database)
                    val edgeRepository = EdgeRepository(database)

                    val oldVertices = vertexRepository.getVerticesByGraph(graphId)
                    val oldVerticesMap = oldVertices.associateBy { it.id }
                    val newVertices =
                        graph.value
                            ?.vertices
                            ?.values
                            ?.toList() ?: emptyList()
                    val newVerticesMap = newVertices.associateBy { it.id }

                    newVertices.forEach { newVertex ->
                        val old = oldVerticesMap[newVertex.id as Long]
                        if (old == null) {
                            vertexRepository.insertVertex(graphId, newVertex.id, newVertex.label)
                        } else if (old.label != newVertex.label) {
                            vertexRepository.updateVertexLabelByGraphAndId(
                                graphId = graphId,
                                vertexId = newVertex.id,
                                newLabel = newVertex.label,
                            )
                        }
                    }

                    oldVertices.forEach { oldVertex ->
                        if (newVerticesMap[oldVertex.vertex_id as V] == null) {
                            vertexRepository.deleteVertexByGraphAndId(graphId = graphId, vertexId = oldVertex.vertex_id)
                        }
                    }

                    val oldEdges = edgeRepository.getEdgesByGraph(graphId)
                    val oldEdgesMap = oldEdges.associateBy { Pair(it.from_vertex, it.to_vertex) }
                    val newEdges =
                        graph.value
                            ?.edges
                            ?.values
                            ?.toList() ?: emptyList()
                    val newEdgesMap = newEdges.associateBy { Pair(it.from.id, it.to.id) }

                    newEdges.forEach { newEdge ->
                        val key = Pair(newEdge.from.id as Long, newEdge.to.id as Long)
                        val old = oldEdgesMap[key]
                        val isWeightedEdge = newEdge is WeightedEdge
                        val newWeight = if (isWeightedEdge) (newEdge as WeightedEdge).weight else null
                        val oldWeight = if (old != null && isWeightedEdge) old.weight else null
                        if (old == null) {
                            if (isWeightedEdge) {
                                edgeRepository.insertEdge(graphId, newEdge.from.id as Long, newEdge.to.id as Long, newWeight)
                            } else {
                                edgeRepository.insertEdge(graphId, newEdge.from.id as Long, newEdge.to.id as Long, null)
                            }
                        } else if (isWeightedEdge && oldWeight != newWeight) {
                            edgeRepository.updateEdgeByGraphAndVertices(
                                graphId = graphId,
                                newWeight = newWeight,
                                fromVertex = newEdge.from.id as Long,
                                toVertex = newEdge.to.id as Long,
                            )
                        }
                    }

                    for (old in oldEdges) {
                        val key = Pair(old.from_vertex as V, old.to_vertex as V)
                        if (newEdgesMap[key] == null) {
                            edgeRepository.deleteEdgeByGraphAndVertices(
                                graphId = graphId,
                                fromVertex = old.from_vertex,
                                toVertex = old.to_vertex,
                            )
                        }
                    }
                    return Result.Success("Graph updated in SQL repository")
                }
            }
        } catch (ex: Exception) {
            return Result.Error(FileErrors.ErrorSavingFile(ex.toString()))
        }
    }
}
