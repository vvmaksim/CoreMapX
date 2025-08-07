package viewmodel.visualizationStrategy

import androidx.compose.ui.unit.dp
import org.coremapx.app.AppLogger.logDebug
import viewmodel.graph.VertexViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class CircularStrategy<E : Comparable<E>, V : Comparable<V>>(
    private val radius: Double = 2000.0,
) : VisualizationStrategy<E, V> {
    override fun place(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
    ) {
        logDebug("Launched place() function for CircularStrategy")
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
