package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.graph.entities.Vertex
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import viewmodel.graph.VertexViewModel
import kotlin.test.assertEquals

class VertexViewModelTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_vertex_view_model_test_" + System.currentTimeMillis(),
            )
        }
    }

    val defaultColor = config.states.vertexMainColor.value
    val defaultRadius = config.states.vertexRadius.value.dp
    val testLabel = "1"
    val testId = 1L
    val testIsLabelVisible = true
    val testIsIdVisible = true

    lateinit var viewModel: VertexViewModel<Long>

    @BeforeEach
    fun setup() {
        viewModel =
            VertexViewModel(
                vertex = Vertex(testId, testLabel),
                _labelVisible = mutableStateOf(testIsLabelVisible),
                _idVisible = mutableStateOf(testIsIdVisible),
            )
    }

    @Test
    fun `check default x and y position`() {
        assertEquals(0.dp, viewModel.x)
        assertEquals(0.dp, viewModel.y)
    }

    @Test
    fun `check onDrag`() {
        assertEquals(0.dp, viewModel.x)
        assertEquals(0.dp, viewModel.y)
        viewModel.onDrag(Offset(x = 52F, y = 25F))
        assertEquals(52.dp, viewModel.x)
        assertEquals(25.dp, viewModel.y)
    }

    @Test
    fun `set position`() {
        viewModel.x = 52.dp
        viewModel.y = 52.dp
        assertEquals(52.dp, viewModel.x)
        assertEquals(52.dp, viewModel.y)
    }

    @Test
    fun `check default color`() {
        assertEquals(defaultColor, viewModel.color)
    }

    @Test
    fun `set color`() {
        viewModel.color = Color(0xFF525252)
        assertEquals(Color(0xFF525252), viewModel.color)
    }

    @Test
    fun `check default radius`() {
        assertEquals(defaultRadius, viewModel.radius)
    }

    @Test
    fun `get label`() {
        assertEquals(testLabel, viewModel.label)
    }

    @Test
    fun `get labelVisible`() {
        assertEquals(testIsLabelVisible, viewModel.labelVisible)
    }

    @Test
    fun `get idVisible`() {
        assertEquals(testIsIdVisible, viewModel.idVisible)
    }

    @Test
    fun `check getVertexText with labelVisible=true idVisible=true`() {
        viewModel =
            VertexViewModel(
                vertex = Vertex(testId, testLabel),
                _labelVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(true),
            )
        assertEquals("id: $testId\nlabel: $testLabel", viewModel.getVertexText())
    }

    @Test
    fun `check getVertexText with labelVisible=true idVisible=false`() {
        viewModel =
            VertexViewModel(
                vertex = Vertex(testId, testLabel),
                _labelVisible = mutableStateOf(true),
                _idVisible = mutableStateOf(false),
            )
        assertEquals(testLabel, viewModel.getVertexText())
    }

    @Test
    fun `check getVertexText with labelVisible=false idVisible=true`() {
        viewModel =
            VertexViewModel(
                vertex = Vertex(testId, testLabel),
                _labelVisible = mutableStateOf(false),
                _idVisible = mutableStateOf(true),
            )
        assertEquals("id: $testId", viewModel.getVertexText())
    }

    @Test
    fun `check getVertexText with labelVisible=false idVisible=false`() {
        viewModel =
            VertexViewModel(
                vertex = Vertex(testId, testLabel),
                _labelVisible = mutableStateOf(false),
                _idVisible = mutableStateOf(false),
            )
        assertEquals("", viewModel.getVertexText())
    }
}
