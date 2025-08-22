package viewmodel.managers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.userDirectory.config.ConfigRepository

class CanvasManager {
    val canvasLimit = ConfigRepository.states.canvasLimit.value

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
        val maxOffsetX = (canvasLimit / 2f) * (1 / _scale.value)
        val minOffsetX = -(canvasLimit / 2f) * (1 / _scale.value)
        val maxOffsetY = (canvasLimit / 2f) * (1 / _scale.value)
        val minOffsetY = -(canvasLimit / 2f) * (1 / _scale.value)
        _offsetX.value = (_offsetX.value + dx).coerceIn(minOffsetX, maxOffsetX)
        _offsetY.value = (_offsetY.value + dy).coerceIn(minOffsetY, maxOffsetY)
        logDebug("Move canvas: dx:$dx, dy:$dy, offsetX:${_offsetX.value}, offsetY:${_offsetY.value}")
    }

    fun zoomCanvas(delta: Float) {
        val zoomFactor = 0.5f
        val oldScale = _scale.value
        val newScale = (oldScale * (1f + delta * zoomFactor)).coerceIn(0.01f, 5f)

        val scaleRatio = newScale / oldScale
        _offsetX.value = _offsetX.value * scaleRatio
        _offsetY.value = _offsetY.value * scaleRatio

        _scale.value = newScale
        logDebug("Zoom canvas: delta:$delta, scale:$newScale")
    }

    fun resetCanvas() {
        _offsetX.value = 0f
        _offsetY.value = 0f
        _scale.value = 1f
        logDebug("Reset canvas: offsetX:${_offsetX.value}, offsetY:${_offsetY.value}, scale:${_scale.value}")
    }
}
