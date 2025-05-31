package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.coremapx.app.config
import viewmodel.graph.VertexViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <V : Comparable<V>> VertexView(
    viewModel: VertexViewModel<V>,
    modifier: Modifier = Modifier,
    hoveredBorderColor: Color = Color.Gray,
) {
    val vertexLabelColor = config.getColor("vertexLabelColor")
    val vertexLabelSize = (config.getIntValue("vertexLabelSize") ?: 0).sp
    var isHovered by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(viewModel.radius * 2, viewModel.radius * 2)
            .offset(viewModel.x, viewModel.y)
            .background(
                color = viewModel.color,
                shape = CircleShape,
            )
            .drawBehind {
                if (isHovered) {
                    drawCircle(
                        color = hoveredBorderColor,
                        style = Stroke(width = 3f),
                        radius = viewModel.radius.toPx()
                    )
                }
            }
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .pointerInput(viewModel) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    viewModel.onDrag(dragAmount)
                }
            }
    ) {
        if (viewModel.labelVisible) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(0.dp, -viewModel.radius - 10.dp),
                text = viewModel.label,
                color = vertexLabelColor,
                fontSize = vertexLabelSize,
            )
        }
    }
}
