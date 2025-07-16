package viewmodel.managers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.config

class CanvasManager {
    val canvasLimit = config.states.canvasLimit.value

    private var _offsetX = mutableStateOf(0f)
    val offsetX: State<Float>
        get() = _offsetX

    private var _offsetY = mutableStateOf(0f)
    val offsetY: State<Float>
        get() = _offsetY

    private var _scale = mutableStateOf(1f)
    val scale: State<Float>
        get() = _scale

    fun moveCanvas(
        dx: Float,
        dy: Float,
    ) {
        val compensatedDx = dx * _scale.value
        val compensatedDy = dy * _scale.value
        val scaledLimit = canvasLimit * _scale.value
        _offsetX.value = (_offsetX.value + compensatedDx).coerceIn(-scaledLimit, scaledLimit)
        _offsetY.value = (_offsetY.value + compensatedDy).coerceIn(-scaledLimit, scaledLimit)
    }

    fun zoomCanvas(delta: Float) {
        val zoomFactor = 0.5f
        val oldScale = _scale.value
        val newScale = (oldScale * (1f + delta * zoomFactor)).coerceIn(0.1f, 5f)

        val scaleRatio = newScale / oldScale
        _offsetX.value = _offsetX.value * scaleRatio
        _offsetY.value = _offsetY.value * scaleRatio

        _scale.value = newScale
    }

    fun resetCanvas() {
        _offsetX.value = 0f
        _offsetY.value = 0f
        _scale.value = 1f
    }
}
