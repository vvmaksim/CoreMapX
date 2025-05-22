package view.graph

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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import viewmodel.graph.GraphViewModel

@Composable
fun <E : Comparable<E>, V : Comparable<V>> GraphView(
    viewModel: GraphViewModel<E, V>,
    offsetX: Float,
    offsetY: Float,
    scale: Float,
    onPan: (Float, Float) -> Unit,
    onZoom: (Float) -> Unit,
) {
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    val canvasDragSize = (config.getIntValue("canvasDragSize") ?: 0).dp
    val canvasDragRatio = config.getDoubleValue("canvasDragRatio") ?: 0.0
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            if (event.type == PointerEventType.Scroll) {
                                val scrollAmount = event.changes.first().scrollDelta
                                onZoom(-scrollAmount.y * 0.1f)
                            }
                        }
                    }
                },
    ) {
        Box(
            modifier =
                Modifier
                    .size(canvasDragSize, canvasDragSize)
                    .offset(
                        (offsetX + (dragOffset.x * canvasDragRatio)).dp,
                        (offsetY + (dragOffset.y * canvasDragRatio)).dp,
                    ).graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        transformOrigin = TransformOrigin(0.5f, 0.5f),
                    ).pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffset += dragAmount
                                onPan(dragAmount.x, dragAmount.y)
                            },
                        )
                    },
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
