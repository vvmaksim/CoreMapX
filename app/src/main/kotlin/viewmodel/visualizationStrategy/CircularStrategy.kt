package viewmodel.visualizationStrategy

import androidx.compose.ui.unit.dp
import viewmodel.graph.VertexViewModel
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

class CircularStrategy(
    private val radius: Double = 2000.0
) : VisualizationStrategy {
    override fun <V : Comparable<V>> place(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?
    ) {
        val n = vertices?.size ?: return
        if (n == 0) return

        val centerX = width / 2
        val centerY = height / 2

        vertices.forEachIndexed { index, vertex ->
            val angle = 2 * PI * index / n
            vertex.x = (centerX + radius * cos(angle)).dp
            vertex.y = (centerY + radius * sin(angle)).dp
        }
    }
}
