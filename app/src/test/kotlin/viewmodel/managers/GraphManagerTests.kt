package viewmodel.managers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import model.fileHandler.ConvertModes
import model.fileHandler.FileExtensions
import model.fileHandler.converter.Converter
import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import viewmodel.visualizationStrategy.CircularStrategy
import viewmodel.visualizationStrategy.ForceDirectedStrategy
import viewmodel.visualizationStrategy.RandomStrategy
import viewmodel.visualizationStrategy.VisualizationStrategiesNames
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GraphManagerTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_graph_manager_test_" + System.currentTimeMillis(),
            )
        }
    }

    val defaultGraphLayoutHeight =
        config.states.graphLayoutHeight.value
            .toDouble()
    val defaultGraphLayoutWidth =
        config.states.graphLayoutWidth.value
            .toDouble()
    val defaultLayoutStrategy = RandomStrategy<Long, Long>()
    val defaultIsVerticesLabelsVisible = false
    val defaultIsVerticesIdsVisible = false
    val defaultIsEdgesWeightsVisible = true
    val defaultIsEdgesIdsVisible = false
    val unknownLayoutStrategyName = "unknownLayoutStrategyName"

    val vertex1 = Vertex(1L, "1")
    val vertex2 = Vertex(2L, "2")
    val vertex3 = Vertex(3L, "3")
    val vertex4 = Vertex(4L, "4")

    lateinit var graphManager: GraphManager<Long, Long>

    @TempDir
    lateinit var tempDir: File

    @BeforeEach
    fun setup() {
        graphManager = GraphManager()
    }

    @Test
    fun `get default isGraphActive value`() {
        assertFalse(graphManager.isGraphActive)
    }

    @Test
    fun `get isGraphActive value`() {
        val newGraph = UndirectedUnweightedGraph<Long>()
        newGraph.addVertex(Vertex(1L, "1"))
        graphManager.updateGraph(newGraph)
        assertTrue(graphManager.isGraphActive)
    }

    @Test
    fun `get default graph value`() {
        assertNull(graphManager.graph.value)
    }

    @Test
    fun `get default graphViewModel value`() {
        assertNull(graphManager.graphViewModel.value)
    }

    @Test
    fun `get default layoutStrategy value`() {
        assertEquals(defaultLayoutStrategy::class, graphManager.layoutStrategy.value::class)
    }

    @Test
    fun `set default layoutStrategy value`() {
        val newStrategy = CircularStrategy<Long, Long>()
        graphManager.updateLayoutStrategy(newStrategy)
        assertEquals(newStrategy::class, graphManager.layoutStrategy.value::class)
    }

    @Test
    fun `get graphLayoutHeight value`() {
        assertEquals(defaultGraphLayoutHeight, graphManager.graphLayoutHeight)
    }

    @Test
    fun `get graphLayoutWidth value`() {
        assertEquals(defaultGraphLayoutWidth, graphManager.graphLayoutWidth)
    }

    @Test
    fun `get default graphName value`() {
        assertEquals("None", graphManager.graphName)
    }

    @Test
    fun `set default graphName value`() {
        graphManager.graphName = "New Graph Name"
        assertEquals("New Graph Name", graphManager.graphName)
    }

    @Test
    fun `get default graphAuthor value`() {
        assertEquals("None", graphManager.graphAuthor)
    }

    @Test
    fun `set default graphAuthor value`() {
        graphManager.graphAuthor = "New Graph Author"
        assertEquals("New Graph Author", graphManager.graphAuthor)
    }

    @Test
    fun `get default graphPath value`() {
        assertNull(graphManager.graphPath)
    }

    @Test
    fun `set default graphPath value`() {
        graphManager.graphPath = "New Graph Path"
        assertEquals("New Graph Path", graphManager.graphPath)
    }

    @Test
    fun `get default graphFormat value`() {
        assertNull(graphManager.graphFormat)
    }

    @Test
    fun `set default graphFormat value`() {
        graphManager.graphFormat = FileExtensions.GRAPH
        assertEquals(FileExtensions.GRAPH, graphManager.graphFormat)
    }

    @Test
    fun `get default graphId value`() {
        assertNull(graphManager.graphId)
    }

    @Test
    fun `set default graphId value`() {
        graphManager.graphId = 1L
        assertEquals(1L, graphManager.graphId)
    }

    @Test
    fun `get default animationScope value`() {
        assertNull(graphManager.animationScope)
    }

    @Test
    fun `set default animationScope value`() {
        val scope = CoroutineScope(Dispatchers.Unconfined)
        graphManager.animationScope = scope
        assertEquals(scope, graphManager.animationScope)
    }

    @Test
    fun `check resetGraphView with ForceDirectedStrategy`() {
        val graph = UndirectedUnweightedGraph<Long>()
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addEdge(UnweightedEdge(1L, vertex1, vertex2))

        val forceDirectedStrategy = ForceDirectedStrategy<Long, Long>()
        graphManager.updateLayoutStrategy(forceDirectedStrategy)
        assertEquals(forceDirectedStrategy::class, graphManager.layoutStrategy.value::class)

        val scope = CoroutineScope(Dispatchers.Unconfined)
        graphManager.animationScope = scope
        graphManager.updateGraph(graph)

        val strategy = graphManager.layoutStrategy.value as ForceDirectedStrategy<Long, Long>
        assertTrue(strategy.isRunning())
    }

    @Test
    fun `check setIsVerticesLabelsVisible`() {
        assertEquals(defaultIsVerticesLabelsVisible, graphManager.isVerticesLabelsVisible.value)
        val newValue = !defaultIsVerticesLabelsVisible
        graphManager.setIsVerticesLabelsVisible(newValue)
        assertEquals(newValue, graphManager.isVerticesLabelsVisible.value)
    }

    @Test
    fun `check setIsVerticesIdsVisible`() {
        assertEquals(defaultIsVerticesIdsVisible, graphManager.isVerticesIdsVisible.value)
        val newValue = !defaultIsVerticesIdsVisible
        graphManager.setIsVerticesIdsVisible(newValue)
        assertEquals(newValue, graphManager.isVerticesIdsVisible.value)
    }

    @Test
    fun `check setIsEdgesWeightsVisible`() {
        assertEquals(defaultIsEdgesWeightsVisible, graphManager.isEdgesWeightsVisible.value)
        val newValue = !defaultIsEdgesWeightsVisible
        graphManager.setIsEdgesWeightsVisible(newValue)
        assertEquals(newValue, graphManager.isEdgesWeightsVisible.value)
    }

    @Test
    fun `check setIsEdgesIdsVisible`() {
        assertEquals(defaultIsEdgesIdsVisible, graphManager.isEdgesIdsVisible.value)
        val newValue = !defaultIsEdgesIdsVisible
        graphManager.setIsEdgesIdsVisible(newValue)
        assertEquals(newValue, graphManager.isEdgesIdsVisible.value)
    }

    @Test
    fun `check getLayoutStrategyByString with RandomStrategy`() {
        val resultGetRandom = graphManager.getLayoutStrategyByString(VisualizationStrategiesNames.RANDOM)
        assertNotNull(resultGetRandom)
        assertEquals(RandomStrategy<Long, Long>()::class, resultGetRandom::class)
    }

    @Test
    fun `check getLayoutStrategyByString with CircularStrategy`() {
        val resultGetCircular = graphManager.getLayoutStrategyByString(VisualizationStrategiesNames.CIRCULAR)
        assertNotNull(resultGetCircular)
        assertEquals(CircularStrategy<Long, Long>()::class, resultGetCircular::class)
    }

    @Test
    fun `check getLayoutStrategyByString with ForceDirectedStrategy`() {
        val resultGetForceDirected = graphManager.getLayoutStrategyByString(VisualizationStrategiesNames.FORCE_DIRECTED)
        assertNotNull(resultGetForceDirected)
        assertEquals(ForceDirectedStrategy<Long, Long>()::class, resultGetForceDirected::class)
    }

    @Test
    fun `check getLayoutStrategyByString with unknownLayoutStrategyName`() {
        val resultGetForceDirected = graphManager.getLayoutStrategyByString(unknownLayoutStrategyName)
        assertNull(resultGetForceDirected)
    }

    @Test
    fun `check getCurrentLayoutStrategyAsString with RandomStrategy`() {
        graphManager.updateLayoutStrategy(RandomStrategy())
        assertEquals(VisualizationStrategiesNames.RANDOM, graphManager.getCurrentLayoutStrategyAsString())
    }

    @Test
    fun `check getCurrentLayoutStrategyAsString with CircularStrategy`() {
        graphManager.updateLayoutStrategy(CircularStrategy())
        assertEquals(VisualizationStrategiesNames.CIRCULAR, graphManager.getCurrentLayoutStrategyAsString())
    }

    @Test
    fun `check getCurrentLayoutStrategyAsString with ForceDirectedStrategy`() {
        graphManager.updateLayoutStrategy(ForceDirectedStrategy())
        assertEquals(VisualizationStrategiesNames.FORCE_DIRECTED, graphManager.getCurrentLayoutStrategyAsString())
    }

    @Test
    fun `load UndirectedUnweighted graph from file`() {
        val graphName = "TestGraph"
        val graphAuthor = "TestAuthor"
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=$graphName
            author=$graphAuthor
            isDirected=false
            isWeighted=false
            Graph:
            add vertex 1 1
            add vertex 2 2
            add edge 1 2
            """.trimIndent(),
        )

        val result = graphManager.loadGraphFromFile(file)
        assertTrue(result is Result.Success)
        assertEquals(emptyList(), result.data)

        assertTrue(graphManager.isGraphActive)
        assertEquals(graphName, graphManager.graphName)
        assertEquals(graphAuthor, graphManager.graphAuthor)
        assertEquals(file.absolutePath, graphManager.graphPath)
        assertEquals(FileExtensions.GRAPH, graphManager.graphFormat)

        val loadedGraph = graphManager.graph.value
        assertNotNull(loadedGraph)
        assertEquals(UndirectedUnweightedGraph<Long>()::class, loadedGraph::class)
        assertEquals(2, loadedGraph.vertices.size)
        assertEquals(1, loadedGraph.edges.size)

        assertTrue(loadedGraph.vertices.containsKey(1L))
        assertTrue(loadedGraph.vertices.containsKey(2L))
        assertEquals("1", loadedGraph.vertices[1L]?.label)
        assertEquals("2", loadedGraph.vertices[2L]?.label)

        assertTrue(loadedGraph.edges.containsKey(0L))
    }

    @Test
    fun `load DirectedWeighted graph from file`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=TestGraph
            author=TestAuthor
            isDirected=true
            isWeighted=true
            Graph:
            """.trimIndent(),
        )
        assertTrue(graphManager.loadGraphFromFile(file) is Result.Success)
        val loadedGraph = graphManager.graph.value
        assertNotNull(loadedGraph)
        assertEquals(DirectedWeightedGraph<Long>()::class, loadedGraph::class)
    }

    @Test
    fun `load DirectedUnweighted graph from file`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=TestGraph
            author=TestAuthor
            isDirected=true
            isWeighted=false
            Graph:
            """.trimIndent(),
        )
        assertTrue(graphManager.loadGraphFromFile(file) is Result.Success)
        val loadedGraph = graphManager.graph.value
        assertNotNull(loadedGraph)
        assertEquals(DirectedUnweightedGraph<Long>()::class, loadedGraph::class)
    }

    @Test
    fun `load UndirectedWeighted graph from file`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=TestGraph
            author=TestAuthor
            isDirected=false
            isWeighted=true
            Graph:
            """.trimIndent(),
        )
        assertTrue(graphManager.loadGraphFromFile(file) is Result.Success)
        val loadedGraph = graphManager.graph.value
        assertNotNull(loadedGraph)
        assertEquals(UndirectedWeightedGraph<Long>()::class, loadedGraph::class)
    }

    @Test
    fun `load incorrect graph from file`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=TestGraph
            author=TestAuthor
            isDirected=false
            isWeighted=true
            Graph:
            add vertex someIncorrectId someLabel
            """.trimIndent(),
        )
        assertTrue(graphManager.loadGraphFromFile(file) is Result.Error)
    }

    @Test
    fun `load graph from file with json file extension`() {
        val file = File(tempDir, "test.graph")
        file.writeText(
            """
            Info:
            name=TestGraph
            author=TestAuthor
            isDirected=false
            isWeighted=true
            Graph:
            """.trimIndent(),
        )
        val jsonFileResult = Converter.convert(file, FileExtensions.JSON, ConvertModes.SAVE, null)
        assertTrue(jsonFileResult is Result.Success)
        assertTrue(graphManager.loadGraphFromFile(jsonFileResult.data) is Result.Success)
    }

    @Test
    fun `save empty graph to graph file format`() {
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save empty graph to graph file format without fileName`() {
        val saveResult =
            graphManager.saveGraph(
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save graph with fileName only`() {
        val saveResult = graphManager.saveGraph(fileName = "testGraph")
        assertTrue(saveResult is Result.Error)
        assertEquals("InvalidParameter", saveResult.error.type)
    }

    @Test
    fun `save graph without fileFormat`() {
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Error)
        assertEquals("InvalidParameter", saveResult.error.type)
    }

    @Test
    fun `save graph with incorrect fileFormat`() {
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = null,
            )
        assertTrue(saveResult is Result.Error)
        assertEquals("InvalidParameter", saveResult.error.type)
    }

    @Test
    fun `save UndirectedUnweighted graph`() {
        val newGraph = UndirectedUnweightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2)
        graphManager.updateGraph(newGraph)
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save UndirectedWeighted graph`() {
        val newGraph = UndirectedWeightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2, 52)
        graphManager.updateGraph(newGraph)
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save DirectedUnweighted graph`() {
        val newGraph = DirectedUnweightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2)
        graphManager.updateGraph(newGraph)
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save DirectedWeighted graph`() {
        val newGraph = DirectedWeightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2, 52)
        graphManager.updateGraph(newGraph)
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save DirectedWeighted graph without vertices`() {
        val newGraph = DirectedWeightedGraph<Long>()
        graphManager.updateGraph(newGraph)
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save DirectedUnweighted graph without vertices`() {
        val newGraph = DirectedUnweightedGraph<Long>()
        graphManager.updateGraph(newGraph)
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save DirectedWeighted graph without edges`() {
        val newGraph = DirectedWeightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        graphManager.updateGraph(newGraph)
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save DirectedUnweighted graph without edges`() {
        val newGraph = DirectedUnweightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        graphManager.updateGraph(newGraph)
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save graph to graph file format without directoryPath`() {
        graphManager.saveGraph(
            fileName = "test",
            fileFormat = FileExtensions.GRAPH,
            directoryPath = tempDir.absolutePath,
        )
        val newGraph = UndirectedUnweightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2)
        graphManager.updateGraph(newGraph)
        graphManager.graphPath = "${tempDir.absolutePath}/test.graph"
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.GRAPH,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save empty graph to json file format`() {
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.JSON,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save graph to json file format without directoryPath`() {
        graphManager.saveGraph(
            fileName = "test",
            fileFormat = FileExtensions.JSON,
            directoryPath = tempDir.absolutePath,
        )
        val newGraph = UndirectedUnweightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2)
        graphManager.updateGraph(newGraph)
        graphManager.graphPath = "${tempDir.absolutePath}/test.json"
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.JSON,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save empty graph to sql file format`() {
        graphManager.graphId = 1L
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.SQL,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save graph to sql file format without directoryPath`() {
        graphManager.saveGraph(
            fileName = "test",
            fileFormat = FileExtensions.SQL,
            directoryPath = tempDir.absolutePath,
        )
        val newGraph = UndirectedUnweightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2)
        graphManager.updateGraph(newGraph)
        graphManager.graphPath = "${tempDir.absolutePath}/test.db"
        graphManager.graphId = 1L
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.SQL,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `try save empty graph to sql file format with null graphId`() {
        graphManager.graphId = null
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.SQL,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Error)
        assertEquals("ErrorSavingFile", saveResult.error.type)
    }

    @Test
    fun `double save DirectedWeighted graph to sql file format`() {
        val newGraph = DirectedWeightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2, 52)
        graphManager.updateGraph(newGraph)
        graphManager.graphId = 1L
        var saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.SQL,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
        newGraph.clear()
        newGraph.addVertex(vertex3)
        newGraph.addVertex(vertex4)
        newGraph.addEdge(vertex3, vertex4, 25)
        graphManager.updateGraph(newGraph)
        graphManager.graphPath = "${tempDir.absolutePath}/testGraph.db"
        saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.SQL,
            )
        assertTrue(saveResult is Result.Success)
    }

    @Test
    fun `save DirectedUnweighted graph to sql file format`() {
        val newGraph = DirectedUnweightedGraph<Long>()
        newGraph.addVertex(vertex1)
        newGraph.addVertex(vertex2)
        newGraph.addEdge(vertex1, vertex2)
        graphManager.updateGraph(newGraph)
        graphManager.graphId = 1L
        val saveResult =
            graphManager.saveGraph(
                fileName = "testGraph",
                fileFormat = FileExtensions.SQL,
                directoryPath = tempDir.absolutePath,
            )
        assertTrue(saveResult is Result.Success)
    }
}
