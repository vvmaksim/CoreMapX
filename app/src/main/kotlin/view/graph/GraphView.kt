package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import extensions.canvasBackground
import org.coremapx.app.userDirectory.config.ConfigRepository
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
    BoxWithConstraints {
        val density = LocalDensity.current
        val canvasWidth = with(density) { maxWidth.toPx() }
        val canvasHeight = with(density) { maxHeight.toPx() }
        val graphLayoutWidth =
            ConfigRepository.states.graphLayoutWidth.value
                .toFloat()
        val graphLayoutHeight =
            ConfigRepository.states.graphLayoutHeight.value
                .toFloat()
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
                                onPan(
                                    dragAmount.x * ConfigRepository.states.canvasDragRatio.value,
                                    dragAmount.y * ConfigRepository.states.canvasDragRatio.value,
                                )
                            },
                        )
                    },
        ) {
            val offsetXDp = with(density) { ((offsetX + (canvasWidth / 2f) - (graphLayoutWidth * scale / 2f))).toDp() }
            val offsetYDp = with(density) { ((offsetY + (canvasHeight / 2f) - (graphLayoutHeight * scale / 2f))).toDp() }
            Box(
                modifier =
                    Modifier
                        .offset(
                            x = offsetXDp,
                            y = offsetYDp,
                        ).graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            transformOrigin = TransformOrigin(0f, 0f),
                        ),
            ) {
                viewModel.edges.forEach { edgeViewModel -> EdgeView(edgeViewModel) }
                viewModel.vertices.forEach { vertexViewModel -> VertexView(vertexViewModel) }
            }
        }
    }
}
