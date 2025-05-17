package view.graph

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import viewmodel.graph.GraphViewModel

@Composable
fun <E : Comparable<E>, V : Comparable<V>> GraphView(
    viewModel: GraphViewModel<E, V>,
    offsetX: Float,
    offsetY: Float,
    onPan: (Float, Float) -> Unit,
) {
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    val velocityTracker = VelocityTracker()
    val inertiaX = remember { Animatable(0f) }
    val inertiaY = remember { Animatable(0f) }
    val canvasDragSize = (config.getIntValue("canvasDragSize") ?: 0).dp
    val canvasDragRatio = config.getDoubleValue("canvasDragRatio") ?: 0.0
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Box(
            modifier =
                Modifier
                    .size(canvasDragSize, canvasDragSize)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { velocityTracker.resetTracking() },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                velocityTracker.addPosition(change.uptimeMillis, change.position)
                                dragOffset += dragAmount
                                onPan(dragAmount.x, dragAmount.y)
                            },
                        )
                    },
        )

        Box(
            modifier =
                Modifier
                    .size(canvasDragSize, canvasDragSize)
                    .offset(
                        (offsetX + (dragOffset.x * canvasDragRatio) + inertiaX.value).dp,
                        (offsetY + (dragOffset.y * canvasDragRatio) + inertiaY.value).dp,
                    ),
        ) {
            viewModel.vertices.forEach { v ->
                VertexView(v, Modifier)
            }
            viewModel.edges.forEach { e ->
                EdgeView(e, Modifier)
            }
        }
    }
}
