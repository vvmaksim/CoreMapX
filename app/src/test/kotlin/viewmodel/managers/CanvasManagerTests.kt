package viewmodel.managers

import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CanvasManagerTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_canvas_manager_test_" + System.currentTimeMillis(),
            )
        }
    }

    val defaultCanvasLimit = config.states.canvasLimit.value

    lateinit var canvasManager: CanvasManager

    @BeforeEach
    fun setup() {
        canvasManager = CanvasManager()
    }

    @Test
    fun `reset canvas`() {
        assertEquals(0F, canvasManager.offsetX.value)
        assertEquals(0F, canvasManager.offsetY.value)
        assertEquals(1F, canvasManager.scale.value)
        canvasManager.moveCanvas(dx = 3F, dy = 52F)
        assertEquals(3F, canvasManager.offsetX.value)
        assertEquals(52F, canvasManager.offsetY.value)
        canvasManager.zoomCanvas(delta = 2F)
        assertEquals(2F, canvasManager.scale.value)
        canvasManager.resetCanvas()
        assertEquals(0F, canvasManager.offsetX.value)
        assertEquals(0F, canvasManager.offsetY.value)
        assertEquals(1F, canvasManager.scale.value)
    }

    @Test
    fun `get canvasLimit`() {
        assertEquals(defaultCanvasLimit, canvasManager.canvasLimit)
    }
}
