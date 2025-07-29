package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graph.entities.UnweightedEdge
import model.graph.entities.Vertex
import model.graph.entities.WeightedEdge
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import viewmodel.graph.EdgeViewModel
import viewmodel.graph.VertexViewModel
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EdgeViewModelTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_edge_view_model_test_" + System.currentTimeMillis(),
            )
        }
    }

    val defaultEdgeColor = config.states.edgeMainColor.value

    val fromVertex = Vertex(1L, "1")
    val toVertex = Vertex(2L, "2")
    val edgeId = 1L
    val unweightedEdge =
        UnweightedEdge(
            id = edgeId,
            from = fromVertex,
            to = toVertex,
        )
    val edgeWeight = 52L
    val weightedEdge =
        WeightedEdge(
            id = edgeId,
            from = fromVertex,
            to = toVertex,
            weight = edgeWeight,
        )
    val vertexIdVisible = mutableStateOf(true)
    val vertexLabelVisible = mutableStateOf(true)
    val fromVertexViewModel =
        VertexViewModel(
            vertex = fromVertex,
            _idVisible = vertexIdVisible,
            _labelVisible = vertexLabelVisible,
        )
    val toVertexViewModel =
        VertexViewModel(
            vertex = toVertex,
            _idVisible = vertexIdVisible,
            _labelVisible = vertexLabelVisible,
        )
    val edgeIdVisible = mutableStateOf(true)
    val edgeWeightVisible = mutableStateOf(true)
    val isDirected = false

    lateinit var viewModel: EdgeViewModel<Long, Long>

    @BeforeEach
    fun setup() {
        viewModel =
            EdgeViewModel(
                edgeId = edgeId,
                from = fromVertexViewModel,
                to = toVertexViewModel,
                isDirected = isDirected,
                edge = unweightedEdge,
                _weightVisible = edgeWeightVisible,
                _idVisible = edgeIdVisible,
            )
    }

    @Test
    fun `get weight in UnweightedEdge`() {
        viewModel =
            EdgeViewModel(
                edgeId = edgeId,
                from = fromVertexViewModel,
                to = toVertexViewModel,
                isDirected = isDirected,
                edge = unweightedEdge,
                _weightVisible = edgeWeightVisible,
                _idVisible = edgeIdVisible,
            )
        assertNull(viewModel.weight)
    }

    @Test
    fun `get weight in WeightedEdge`() {
        viewModel =
            EdgeViewModel(
                edgeId = edgeId,
                from = fromVertexViewModel,
                to = toVertexViewModel,
                isDirected = isDirected,
                edge = weightedEdge,
                _weightVisible = edgeWeightVisible,
                _idVisible = edgeIdVisible,
            )
        assertEquals(edgeWeight, viewModel.weight)
    }

    @Test
    fun `get default color`() {
        assertEquals(defaultEdgeColor, viewModel.color)
    }

    @Test
    fun `set color`() {
        viewModel.color = Color(0xFF525252)
        assertEquals(Color(0xFF525252), viewModel.color)
    }

    @Test
    fun `get weightVisible`() {
        assertEquals(edgeWeightVisible.value, viewModel.weightVisible)
    }

    @Test
    fun `get idVisible`() {
        assertEquals(edgeIdVisible.value, viewModel.idVisible)
    }

    @Test
    fun `get edgeId`() {
        assertEquals(edgeId, viewModel.edgeId)
    }

    @Test
    fun `get fromVertex`() {
        assertEquals(fromVertexViewModel, viewModel.from)
    }

    @Test
    fun `get toVertex`() {
        assertEquals(toVertexViewModel, viewModel.to)
    }

    @Test
    fun `get isDirected`() {
        assertEquals(isDirected, viewModel.isDirected)
    }

    @Test
    fun `check getEdgeText with weightVisible=true idVisible=true`() {
        viewModel =
            EdgeViewModel(
                edgeId = edgeId,
                from = fromVertexViewModel,
                to = toVertexViewModel,
                isDirected = isDirected,
                edge = weightedEdge,
                _weightVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        assertEquals("id: $edgeId\nweight: $edgeWeight", viewModel.getEdgeText())
    }

    @Test
    fun `check getEdgeText with weightVisible=true idVisible=false`() {
        viewModel =
            EdgeViewModel(
                edgeId = edgeId,
                from = fromVertexViewModel,
                to = toVertexViewModel,
                isDirected = isDirected,
                edge = weightedEdge,
                _weightVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(false),
            )
        assertEquals(edgeWeight.toString(), viewModel.getEdgeText())
    }

    @Test
    fun `check getEdgeText with weightVisible=false idVisible=true`() {
        viewModel =
            EdgeViewModel(
                edgeId = edgeId,
                from = fromVertexViewModel,
                to = toVertexViewModel,
                isDirected = isDirected,
                edge = weightedEdge,
                _weightVisible = mutableStateOf(false),
                _idVisible = mutableStateOf(true),
            )
        assertEquals("id: $edgeId", viewModel.getEdgeText())
    }

    @Test
    fun `check getEdgeText with weightVisible=false idVisible=false`() {
        viewModel =
            EdgeViewModel(
                edgeId = edgeId,
                from = fromVertexViewModel,
                to = toVertexViewModel,
                isDirected = isDirected,
                edge = weightedEdge,
                _weightVisible = mutableStateOf(false),
                _idVisible = mutableStateOf(false),
            )
        assertEquals("", viewModel.getEdgeText())
    }

    @Test
    fun `check getEdgeText for UnweightedEdge with weightVisible=true idVisible=true`() {
        viewModel =
            EdgeViewModel(
                edgeId = edgeId,
                from = fromVertexViewModel,
                to = toVertexViewModel,
                isDirected = isDirected,
                edge = unweightedEdge,
                _weightVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        assertEquals("id: $edgeId\nweight: ", viewModel.getEdgeText())
    }
}
