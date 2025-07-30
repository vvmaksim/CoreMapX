package viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import model.fileHandler.FileExtensions
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.entities.Vertex
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import viewmodel.managers.GraphManager
import viewmodel.visualizationStrategy.CircularStrategy
import viewmodel.visualizationStrategy.ForceDirectedStrategy
import viewmodel.visualizationStrategy.RandomStrategy
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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

    lateinit var graphManager: GraphManager<Long, Long>

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
        graphManager.updateLayoutStrategy(ForceDirectedStrategy())
        graphManager.resetGraphView()
    }
}
