package viewmodel.visualizationStrategies

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import model.dto.VisibleStates
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.contracts.Graph
import model.graph.entities.Vertex
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import viewmodel.graph.GraphViewModel
import viewmodel.graph.VertexViewModel
import viewmodel.visualizationStrategy.CircularStrategy
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.test.assertTrue

class CircularStrategyTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_circular_strategy_test_" + System.currentTimeMillis(),
            )
        }
    }

    lateinit var graph: Graph<Long, Long>
    lateinit var graphViewModel: GraphViewModel<Long, Long>
    lateinit var strategy: CircularStrategy<Long, Long>

    val width = 100_000.toDouble()
    val height = 100_000.toDouble()
    val radius = 2000.0
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
        graphViewModel =
            GraphViewModel(
                graph = graph,
                visibleStates = visibleStates,
            )
        strategy = CircularStrategy()
    }

    @Test
    fun `CircularStrategy places vertices in a circle`() {
        strategy = CircularStrategy(radius = radius)
        strategy.place(
            width = width,
            height = height,
            vertices = graphViewModel.vertices,
        )

        val centerX = width / 2
        val centerY = height / 2

        graphViewModel.vertices.forEachIndexed { index, vertex ->
            val expectedX = (centerX + radius * cos(2 * PI * index / 6)).dp
            val expectedY = (centerY + radius * sin(2 * PI * index / 6)).dp

            assertTrue(
                abs(vertex.x.value - expectedX.value) < 0.001,
                "Vertex ${index + 1} x coordinate should be approximately $expectedX, but was ${vertex.x}",
            )
            assertTrue(
                abs(vertex.y.value - expectedY.value) < 0.001,
                "Vertex ${index + 1} y coordinate should be approximately $expectedY, but was ${vertex.y}",
            )
        }
    }

    @Test
    fun `CircularStrategy test with empty vertices`() {
        strategy.place(
            width = width,
            height = height,
            vertices = null,
        )
    }

    @Test
    fun `CircularStrategy test with empty vertices collection`() {
        val emptyVertices = emptyList<VertexViewModel<Long>>()
        strategy.place(
            width = width,
            height = height,
            vertices = emptyVertices,
        )
    }
}
