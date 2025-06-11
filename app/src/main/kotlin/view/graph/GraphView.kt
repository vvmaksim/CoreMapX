package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import extensions.canvasBackground
import org.coremapx.app.config
import viewmodel.graph.GraphViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun <E : Comparable<E>, V : Comparable<V>> GraphView(
    viewModel: GraphViewModel<E, V>,
    offsetX: Float,
    offsetY: Float,
    scale: Float,
    onPan: (Float, Float) -> Unit,
    onZoom: (Float) -> Unit,
) {
    val canvasDragRatio = config.states.canvasDragRatio.value

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.canvasBackground)
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
                }.pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            onPan(dragAmount.x * canvasDragRatio, dragAmount.y * canvasDragRatio)
                        },
                    )
                },
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .offset(offsetX.dp, offsetY.dp)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        transformOrigin = TransformOrigin(0.5f, 0.5f),
                    ),
        ) {
            viewModel.edges.forEach { edgeViewModel -> EdgeView(edgeViewModel) }
            viewModel.vertices.forEach { vertexViewModel -> VertexView(vertexViewModel) }
        }
    }
}
