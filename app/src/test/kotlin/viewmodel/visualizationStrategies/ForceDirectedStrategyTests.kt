package viewmodel.visualizationStrategies

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import model.dto.VisibleStates
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.contracts.Graph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import viewmodel.graph.GraphViewModel
import viewmodel.graph.VertexViewModel
import viewmodel.visualizationStrategy.ForceDirectedStrategy
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ForceDirectedStrategyTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_force_directed_strategy_test_" + System.currentTimeMillis(),
            )
        }
    }

    lateinit var graph: Graph<Long, Long>
    lateinit var graphViewModel: GraphViewModel<Long, Long>
    lateinit var strategy: ForceDirectedStrategy<Long, Long>
    lateinit var scope: CoroutineScope

    val width = 100_000.toDouble()
    val height = 100_000.toDouble()
    val vertex1 = Vertex(1L, "1")
    val vertex2 = Vertex(2L, "2")
    val vertex3 = Vertex(3L, "3")
    val vertex4 = Vertex(4L, "4")
    val vertex5 = Vertex(5L, "5")
    val vertex6 = Vertex(6L, "6")

    val visibleStates =
        mutableStateOf(
            VisibleStates(
                verticesLabels = mutableStateOf(true),
                verticesIds = mutableStateOf(true),
                edgesWeights = mutableStateOf(true),
                edgesIds = mutableStateOf(true),
            ),
        )

    @BeforeEach
    fun setup() {
        graph = UndirectedUnweightedGraph()
        graph.addVertex(vertex1)
        graph.addVertex(vertex2)
        graph.addVertex(vertex3)
        graph.addVertex(vertex4)
        graph.addVertex(vertex5)
        graph.addVertex(vertex6)

        graph.addEdge(UnweightedEdge(1L, vertex1, vertex2))
        graph.addEdge(UnweightedEdge(2L, vertex2, vertex3))
        graph.addEdge(UnweightedEdge(3L, vertex3, vertex4))
        graph.addEdge(UnweightedEdge(4L, vertex4, vertex5))
        graph.addEdge(UnweightedEdge(5L, vertex5, vertex6))

        graphViewModel =
            GraphViewModel(
                graph = graph,
                visibleStates = visibleStates,
            )
        strategy = ForceDirectedStrategy()
        scope = CoroutineScope(Dispatchers.Unconfined)
    }

    @AfterEach
    fun stopAnimation() {
        strategy.stopAnimation()
    }

    @Test
    fun `ForceDirectedStrategy initialize positions correctly`() {
        strategy.place(
            width = width,
            height = height,
            vertices = graphViewModel.vertices,
        )
        graphViewModel.vertices.forEach { viewModel: VertexViewModel<Long> ->
            assertTrue(viewModel.x.value >= 0 && viewModel.x.value <= width)
            assertTrue(viewModel.y.value >= 0 && viewModel.y.value <= height)
        }
    }

    @Test
    fun `ForceDirectedStrategy test with empty vertices`() {
        strategy.place(
            width = width,
            height = height,
            vertices = null,
        )
    }

    @Test
    fun `ForceDirectedStrategy test with empty vertices collection`() {
        val emptyVertices = emptyList<VertexViewModel<Long>>()
        strategy.place(
            width = width,
            height = height,
            vertices = emptyVertices,
        )
    }

    @Test
    fun `ForceDirectedStrategy animation starts and stops correctly`() {
        assertFalse(strategy.isRunning())
        strategy.startAnimation(
            scope = scope,
            width = width,
            height = height,
            vertices = graphViewModel.vertices,
            edges = graph.edges.values,
            iterations = 10,
        )
        assertTrue(strategy.isRunning())
        strategy.stopAnimation()
        assertFalse(strategy.isRunning())
    }

    @Test
    fun `ForceDirectedStrategy parameters can be set and retrieved`() {
        val newParams =
            strategy.getParameters().copy(
                iterations = 1000,
                area = 5000000.0,
                gravity = 0.05,
                speed = 0.2,
            )
        strategy.setParameters(newParams)
        val retrievedParams = strategy.getParameters()
        assertEquals(newParams.iterations, retrievedParams.iterations)
        assertEquals(newParams.area, retrievedParams.area, 0.001)
        assertEquals(newParams.gravity, retrievedParams.gravity, 0.001)
        assertEquals(newParams.speed, retrievedParams.speed, 0.001)
    }

    @Test
    fun `ForceDirectedStrategy nextIteration works correctly`() {
        strategy.place(
            width = width,
            height = height,
            vertices = graphViewModel.vertices,
        )
        val initialPositions = graphViewModel.vertices.map { it.x.value to it.y.value }
        strategy.nextIteration(
            width = width,
            height = height,
            vertices = graphViewModel.vertices,
            edges = graph.edges.values,
        )
        val finalPositions = graphViewModel.vertices.map { it.x.value to it.y.value }
        val positionsChanged =
            initialPositions.zip(finalPositions).any { (initial, final) ->
                initial.first != final.first || initial.second != final.second
            }
    }
}
