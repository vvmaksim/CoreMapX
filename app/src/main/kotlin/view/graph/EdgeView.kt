package view.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.sp
import org.coremapx.app.config
import viewmodel.graph.EdgeViewModel
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun <E : Comparable<E>, V : Comparable<V>> EdgeView(
    viewModel: EdgeViewModel<E, V>,
    modifier: Modifier = Modifier,
) {
    val edgeMainColor = config.getColor("edgeMainColor")
    val edgeWidth = config.getFloatValue("edgeWidth") ?: 0f
    val edgeArrowSize = config.getFloatValue("edgeArrowSize") ?: 0f
    val edgeLabelColor = config.getColor("edgeLabelColor")
    val edgeLabelSize = (config.getIntValue("edgeLabelSize") ?: 0).sp

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val fromCenter =
                Offset(
                    viewModel.from.x.toPx() + viewModel.from.radius.toPx(),
                    viewModel.from.y.toPx() + viewModel.from.radius.toPx(),
                )
            val toCenter =
                Offset(
                    viewModel.to.x.toPx() + viewModel.to.radius.toPx(),
                    viewModel.to.y.toPx() + viewModel.to.radius.toPx(),
                )

            val dx = toCenter.x - fromCenter.x
            val dy = toCenter.y - fromCenter.y
            val dist = sqrt(dx * dx + dy * dy)
            val fromRadius = viewModel.from.radius.toPx()
            val toRadius = viewModel.to.radius.toPx()
            val unitX = if (dist != 0f) dx / dist else 0f
            val unitY = if (dist != 0f) dy / dist else 0f

            val start =
                Offset(
                    fromCenter.x + unitX * fromRadius,
                    fromCenter.y + unitY * fromRadius,
                )
            val end =
                Offset(
                    toCenter.x - unitX * toRadius,
                    toCenter.y - unitY * toRadius,
                )

            drawLine(
                start = start,
                end = end,
                color = edgeMainColor,
                strokeWidth = edgeWidth,
            )

            if (viewModel.isDirected) {
                val arrowSize = edgeArrowSize
                val angle = atan2(end.y - start.y, end.x - start.x)

                val arrowPoint1 =
                    Offset(
                        end.x - arrowSize * cos(angle - Math.PI / 6).toFloat(),
                        end.y - arrowSize * sin(angle - Math.PI / 6).toFloat(),
                    )
                val arrowPoint2 =
                    Offset(
                        end.x - arrowSize * cos(angle + Math.PI / 6).toFloat(),
                        end.y - arrowSize * sin(angle + Math.PI / 6).toFloat(),
                    )

                val path =
                    Path().apply {
                        moveTo(end.x, end.y)
                        lineTo(arrowPoint1.x, arrowPoint1.y)
                        moveTo(end.x, end.y)
                        lineTo(arrowPoint2.x, arrowPoint2.y)
                    }

                drawPath(
                    path = path,
                    color = edgeMainColor,
                    style = Stroke(width = edgeWidth),
                )
            }
        }

        viewModel.weight?.let { weight ->
            val midX = (viewModel.from.x + viewModel.to.x) / 2
            val midY = (viewModel.from.y + viewModel.to.y) / 2
            Text(
                text = weight.toString(),
                modifier = Modifier.offset(midX, midY),
                color = edgeLabelColor,
                fontSize = edgeLabelSize,
            )
        }
    }
}
