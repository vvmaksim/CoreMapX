package viewmodel.managers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import model.command.concrete.Commands
import model.database.sqlite.createDatabase
import model.database.sqlite.repository.EdgeRepository
import model.database.sqlite.repository.GraphRepository
import model.database.sqlite.repository.VertexRepository
import model.dto.VisibleStates
import model.fileHandler.ConvertModes
import model.fileHandler.DialogManager
import model.fileHandler.FileExtensions
import model.fileHandler.Parser
import model.fileHandler.converter.Converter
import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.contracts.Edge
import model.graph.contracts.Graph
import model.graph.entities.WeightedEdge
import model.ir.GraphIR
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.config
import org.coremapx.app.config.PrivateConfig
import orgcoremapxapp.Graphs
import viewmodel.graph.GraphViewModel
import viewmodel.graph.VertexViewModel
import viewmodel.visualizationStrategy.CircularStrategy
import viewmodel.visualizationStrategy.ForceDirectedStrategy
import viewmodel.visualizationStrategy.RandomStrategy
import viewmodel.visualizationStrategy.VisualizationStrategiesNames
import viewmodel.visualizationStrategy.VisualizationStrategy
import java.io.File
import kotlin.collections.forEach

class GraphManager<E : Comparable<E>, V : Comparable<V>> {
    val graphLayoutHeight =
        config.states.graphLayoutHeight.value
            .toDouble()
    val graphLayoutWidth =
        config.states.graphLayoutWidth.value
            .toDouble()

    private var _graph = mutableStateOf<Graph<E, V>?>(null)
    val graph: State<Graph<E, V>?>
        get() = _graph

    val isGraphActive: Boolean
        get() = _graph.value != null

    private var _graphViewModel = mutableStateOf<GraphViewModel<E, V>?>(null)
    val graphViewModel: State<GraphViewModel<E, V>?>
        get() = _graphViewModel

    private val _layoutStrategy = mutableStateOf<VisualizationStrategy<E, V>>(RandomStrategy())
    val layoutStrategy: State<VisualizationStrategy<E, V>>
        get() = _layoutStrategy

    private var _isVerticesLabelsVisible = mutableStateOf(false)
    val isVerticesLabelsVisible: State<Boolean>
        get() = _isVerticesLabelsVisible

    private var _isVerticesIdsVisible = mutableStateOf(false)
    val isVerticesIdsVisible: State<Boolean>
        get() = _isVerticesIdsVisible

    private var _isEdgesWeightsVisible = mutableStateOf(true)
    val isEdgesWeightsVisible: State<Boolean>
        get() = _isEdgesWeightsVisible

    private var _isEdgesIdsVisible = mutableStateOf(false)
    val isEdgesIdsVisible: State<Boolean>
        get() = _isEdgesIdsVisible

    private var _visibleStates =
        mutableStateOf(
            VisibleStates(
                verticesLabels = isVerticesLabelsVisible,
                verticesIds = isVerticesIdsVisible,
                edgesWeights = isEdgesWeightsVisible,
                edgesIds = isEdgesIdsVisible,
            ),
        )
    val visibleStates: State<VisibleStates>
        get() = _visibleStates

    var graphName: String = "None"

    var graphAuthor: String = "None"

    var graphPath: String? = null

    var graphFormat: FileExtensions? = null

    var graphId: Long? = null

    var animationScope: CoroutineScope? = null

    fun resetGraphView() {
        logDebug("Launched resetGraphView() function from GraphManager")
        _graphViewModel.value?.let { viewModel ->
            val strategy = layoutStrategy.value
            if (strategy is ForceDirectedStrategy<*, *>) {
                val forceStrategy = strategy as? ForceDirectedStrategy<Comparable<Any>, Comparable<Any>>
                forceStrategy?.stopAnimation()
                forceStrategy?.initializePositions(
                    width = graphLayoutWidth,
                    height = graphLayoutHeight,
                    vertices = viewModel.vertices as? Collection<VertexViewModel<Comparable<Any>>>,
                )
                forceStrategy?.startAnimation(
                    scope = animationScope ?: return,
                    width = graphLayoutWidth,
                    height = graphLayoutHeight,
                    vertices = viewModel.vertices as? Collection<VertexViewModel<Comparable<Any>>>,
                    edges =
                        graph.value
                            ?.edges
                            ?.values
                            ?.map { it as Edge<Comparable<Any>, Comparable<Any>> },
                    iterations = strategy.getParameters().iterations,
                    onFrame = { },
                )
            } else {
                layoutStrategy.value.place(graphLayoutWidth, graphLayoutHeight, viewModel.vertices)
            }
        }
    }

    fun updateLayoutStrategy(newStrategy: VisualizationStrategy<E, V>) {
        _layoutStrategy.value = newStrategy
        logDebug("Update layout strategy on ${newStrategy::class.simpleName ?: "NoStrategyName"}")
    }

    fun updateGraph(newGraph: Graph<E, V>) {
        logDebug("Launched updateGraph() function from GraphManager")
        _graph.value = newGraph
        updateGraphViewModel(
            newViewModel =
                GraphViewModel(
                    graph = newGraph,
                    visibleStates = visibleStates,
                ),
        )
    }

    fun updateGraphViewModel(newViewModel: GraphViewModel<E, V>) {
        _graphViewModel.value = newViewModel
        resetGraphView()
    }

    fun setIsVerticesLabelsVisible(value: Boolean) {
        _isVerticesLabelsVisible.value = value
        logDebug("Set isVerticesLabelsVisible on $value")
    }

    fun setIsVerticesIdsVisible(value: Boolean) {
        _isVerticesIdsVisible.value = value
        logDebug("Set isVerticesIdsVisible on $value")
    }

    fun setIsEdgesWeightsVisible(value: Boolean) {
        _isEdgesWeightsVisible.value = value
        logDebug("Set isEdgesWeightsVisible on $value")
    }

    fun setIsEdgesIdsVisible(value: Boolean) {
        _isEdgesIdsVisible.value = value
        logDebug("Set isEdgesIdsVisible on $value")
    }

    fun initGraphParameters(newGraphName: String) {
        graphName = newGraphName
        graphAuthor = "None"
        graphPath = null
        graphFormat = null
        graphId = 0
    }

    fun getLayoutStrategyByString(strategy: String): VisualizationStrategy<E, V>? =
        when (strategy.lowercase()) {
            VisualizationStrategiesNames.RANDOM.lowercase() -> RandomStrategy()
            VisualizationStrategiesNames.CIRCULAR.lowercase() -> CircularStrategy()
            VisualizationStrategiesNames.FORCE_DIRECTED.lowercase() -> ForceDirectedStrategy()
            else -> null
        }

    fun getCurrentLayoutStrategyAsString(): String =
        when (_layoutStrategy.value) {
            is RandomStrategy<E, V> -> VisualizationStrategiesNames.RANDOM
            is CircularStrategy<E, V> -> VisualizationStrategiesNames.CIRCULAR
            is ForceDirectedStrategy<E, V> -> VisualizationStrategiesNames.FORCE_DIRECTED
            else -> VisualizationStrategiesNames.RANDOM
        }

    fun openGraphFile(): Result<List<String>> {
        logDebug("Open file dialog for graph file")
        val file =
            DialogManager.showOpenFileDialog(directory = PrivateConfig.UserDirectory.GRAPHS_DIR_PATH)
                ?: return Result.Success(emptyList())
        return loadGraphFromFile(file)
    }

    fun openGraphRepository(): Result<File?> {
        logDebug("Open file dialog for graph repository")
        val repository =
            DialogManager.showOpenFileDialog(
                directory = PrivateConfig.UserDirectory.GRAPHS_DIR_PATH,
                title = "Select graph repository",
            )
        return Result.Success(repository)
    }

    fun loadGraphFromFile(file: File): Result<List<String>> {
        logDebug("Load graph from file or directory from absolutePath:${file.absolutePath}")
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
            val executeResult =
                Commands(
                    command = (commandResult as Result.Success).data,
                    graph = newGraph,
                    outputMessages = mutableListOf(),
                ).execute()
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
        directoryPath: String? = null,
        fileFormat: FileExtensions? = graphFormat,
    ): Result<String> {
        logDebug(
            "Save graph as fileFormat:${fileFormat?.name ?: "NoNameFileFormat"} with fileName:$fileName to directoryPath:${directoryPath ?: "Unknown directoryPath"}",
        )
        try {
            val graphPathCopy = graphPath
            if (directoryPath == null &&
                graphPathCopy == null
            ) {
                return Result.Error(
                    FileErrors.InvalidParameter("directoryPath", "This parameter cannot be null when graphPath is null"),
                )
            }
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
                    val path = if (directoryPath != null) "$directoryPath/$fileName.graph" else graphPathCopy ?: "unknownSituation"
                    val fileIR = File(path)
                    fileIR.writeText(ir.toString())
                    return Result.Success("File was saved as GRAPH")
                }
                FileExtensions.JSON -> {
                    val tempFileIR = File("${PrivateConfig.UserDirectory.TEMP_DIR_PATH}/$fileName.graph")
                    tempFileIR.writeText(ir.toString())
                    val convertResult = Converter.convert(tempFileIR, FileExtensions.JSON, ConvertModes.SAVE, graphId)
                    when (convertResult) {
                        is Result.Error -> return convertResult
                        is Result.Success -> {
                            val path = if (directoryPath != null) "$directoryPath/$fileName.json" else graphPathCopy ?: "unknownSituation"
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
                    val path = if (directoryPath != null) "$directoryPath/$fileName.db" else graphPathCopy ?: "unknownSituation"
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
