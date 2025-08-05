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
import viewmodel.visualizationStrategy.RandomStrategy
import kotlin.test.assertTrue

class RandomStrategyTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_random_strategy_test_" + System.currentTimeMillis(),
            )
        }
    }

    lateinit var graph: Graph<Long, Long>
    lateinit var graphViewModel: GraphViewModel<Long, Long>
    lateinit var strategy: RandomStrategy<Long, Long>

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
        graphViewModel =
            GraphViewModel(
                graph = graph,
                visibleStates = visibleStates,
            )
        strategy = RandomStrategy()
    }

    @Test
    fun `simply RandomStrategy test`() {
        strategy.place(
            width = width,
            height = height,
            vertices = graphViewModel.vertices,
        )
        graphViewModel.vertices.forEach { viewModel: VertexViewModel<Long> ->
            // Since RandomStrategy randomly determines vertex positions, it is possible that a vertex may end up at
            // position (0, 0), which is the default value. Just rerun the test.
            assertTrue(viewModel.x != 0.dp && viewModel.y != 0.dp)
        }
    }

    @Test
    fun `RandomStrategy test with empty vertices`() {
        strategy.place(
            width = width,
            height = height,
            vertices = null,
        )
    }
}
