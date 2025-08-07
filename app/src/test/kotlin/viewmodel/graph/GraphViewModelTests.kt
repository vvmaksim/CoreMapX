package viewmodel.graph

import androidx.compose.runtime.mutableStateOf
import model.dto.VisibleStates
import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import model.graph.entities.WeightedEdge
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GraphViewModelTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_graph_view_model_test_" + System.currentTimeMillis(),
            )
        }
    }

    val undirectedUnweightedGraph = UndirectedUnweightedGraph<Long>()
    val verticesLabels = mutableStateOf(true)
    val verticesIds = mutableStateOf(true)
    val edgesWeights = mutableStateOf(true)
    val edgesIds = mutableStateOf(true)
    val visibleStates =
        mutableStateOf(
            VisibleStates(
                verticesLabels = verticesLabels,
                verticesIds = verticesIds,
                edgesWeights = edgesWeights,
                edgesIds = edgesIds,
            ),
        )

    lateinit var viewModel: GraphViewModel<Long, Long>

    @BeforeEach
    fun setup() {
        viewModel =
            GraphViewModel(
                graph = undirectedUnweightedGraph,
                visibleStates = visibleStates,
            )
    }

    @Test
    fun `get empty vertices`() {
        assertTrue(viewModel.vertices.isEmpty())
    }

    @Test
    fun `get empty edges`() {
        assertTrue(viewModel.edges.isEmpty())
    }

    @Test
    fun `get 2 vertices and 1 edge from UndirectedUnweightedGraph`() {
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val edge =
            UnweightedEdge(
                id = 0L,
                from = vertex1,
                to = vertex2,
            )
        val vertexViewModel1 =
            VertexViewModel(
                vertex = vertex1,
                _labelVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        val vertexViewModel2 =
            VertexViewModel(
                vertex = vertex2,
                _labelVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        val edgeTestViewModel =
            EdgeViewModel(
                edgeId = 0L,
                from = vertexViewModel1,
                to = vertexViewModel2,
                _weightVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
                isDirected = false,
                edge = edge,
            )
        val newUndirectedUnweightedGraph = UndirectedUnweightedGraph<Long>()
        newUndirectedUnweightedGraph.addVertex(vertex1)
        newUndirectedUnweightedGraph.addVertex(vertex2)
        newUndirectedUnweightedGraph.addEdge(from = vertex1, to = vertex2)
        viewModel =
            GraphViewModel(
                graph = newUndirectedUnweightedGraph,
                visibleStates = visibleStates,
            )
        assertEquals(2, viewModel.vertices.size)
        assertEquals(1, viewModel.edges.size)
        var vertexViewModelCountExist1 = 0
        var vertexViewModelCountExist2 = 0
        viewModel.vertices.forEach { vertexViewModel: VertexViewModel<Long> ->
            if (vertexViewModel1.label == vertexViewModel.label) vertexViewModelCountExist1++
            if (vertexViewModel2.label == vertexViewModel.label) vertexViewModelCountExist2++
        }
        assertEquals(1, vertexViewModelCountExist1)
        assertEquals(1, vertexViewModelCountExist2)
        var edgeViewModelCountExist = 0
        viewModel.edges.forEach { edgeViewModel: EdgeViewModel<Long, Long> ->
            if (edgeTestViewModel.edgeId == edgeViewModel.edgeId) edgeViewModelCountExist++
        }
        assertEquals(1, edgeViewModelCountExist)
    }

    @Test
    fun `get 2 vertices and 1 edge from DirectedUnweightedGraph`() {
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val edge =
            UnweightedEdge(
                id = 0L,
                from = vertex1,
                to = vertex2,
            )
        val vertexViewModel1 =
            VertexViewModel(
                vertex = vertex1,
                _labelVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        val vertexViewModel2 =
            VertexViewModel(
                vertex = vertex2,
                _labelVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        val edgeTestViewModel =
            EdgeViewModel(
                edgeId = 0L,
                from = vertexViewModel1,
                to = vertexViewModel2,
                _weightVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
                isDirected = false,
                edge = edge,
            )
        val directedUnweightedGraph = DirectedUnweightedGraph<Long>()
        directedUnweightedGraph.addVertex(vertex1)
        directedUnweightedGraph.addVertex(vertex2)
        directedUnweightedGraph.addEdge(from = vertex1, to = vertex2)
        viewModel =
            GraphViewModel(
                graph = directedUnweightedGraph,
                visibleStates = visibleStates,
            )
        assertEquals(2, viewModel.vertices.size)
        assertEquals(1, viewModel.edges.size)
        var vertexViewModelCountExist1 = 0
        var vertexViewModelCountExist2 = 0
        viewModel.vertices.forEach { vertexViewModel: VertexViewModel<Long> ->
            if (vertexViewModel1.label == vertexViewModel.label) vertexViewModelCountExist1++
            if (vertexViewModel2.label == vertexViewModel.label) vertexViewModelCountExist2++
        }
        assertEquals(1, vertexViewModelCountExist1)
        assertEquals(1, vertexViewModelCountExist2)
        var edgeViewModelCountExist = 0
        viewModel.edges.forEach { edgeViewModel: EdgeViewModel<Long, Long> ->
            if (edgeTestViewModel.edgeId == edgeViewModel.edgeId) edgeViewModelCountExist++
        }
        assertEquals(1, edgeViewModelCountExist)
    }

    @Test
    fun `get 2 vertices and 1 edge from DirectedWeightedGraph`() {
        val vertex1 = Vertex(1L, "1")
        val vertex2 = Vertex(2L, "2")
        val edge =
            WeightedEdge(
                id = 0L,
                from = vertex1,
                to = vertex2,
                weight = 52,
            )
        val vertexViewModel1 =
            VertexViewModel(
                vertex = vertex1,
                _labelVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        val vertexViewModel2 =
            VertexViewModel(
                vertex = vertex2,
                _labelVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        val edgeTestViewModel =
            EdgeViewModel(
                edgeId = 0L,
                from = vertexViewModel1,
                to = vertexViewModel2,
                _weightVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
                isDirected = false,
                edge = edge,
            )
        val directedWeightedGraph = DirectedWeightedGraph<Long>()
        directedWeightedGraph.addVertex(vertex1)
        directedWeightedGraph.addVertex(vertex2)
        directedWeightedGraph.addEdge(from = vertex1, to = vertex2, weight = 52)
        viewModel =
            GraphViewModel(
                graph = directedWeightedGraph,
                visibleStates = visibleStates,
            )
        assertEquals(2, viewModel.vertices.size)
        assertEquals(1, viewModel.edges.size)
        var vertexViewModelCountExist1 = 0
        var vertexViewModelCountExist2 = 0
        viewModel.vertices.forEach { vertexViewModel: VertexViewModel<Long> ->
            if (vertexViewModel1.label == vertexViewModel.label) vertexViewModelCountExist1++
            if (vertexViewModel2.label == vertexViewModel.label) vertexViewModelCountExist2++
        }
        assertEquals(1, vertexViewModelCountExist1)
        assertEquals(1, vertexViewModelCountExist2)
        var edgeViewModelCountExist = 0
        viewModel.edges.forEach { edgeViewModel: EdgeViewModel<Long, Long> ->
            if (edgeTestViewModel.edgeId == edgeViewModel.edgeId) edgeViewModelCountExist++
        }
        assertEquals(1, edgeViewModelCountExist)
    }
}
