package viewmodel.visualizationStrategy

import androidx.compose.ui.unit.dp
import viewmodel.graph.VertexViewModel
import kotlin.random.Random

class RandomStrategy<E : Comparable<E>, V : Comparable<V>> : VisualizationStrategy<E, V> {
    override fun place(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
    ) {
        val padding = 50.0
        val minX = padding
        val maxX = width - padding
        val minY = padding
        val maxY = height - padding

        vertices?.forEach { vertex ->
            vertex.x = Random.nextDouble(minX, maxX).dp
            vertex.y = Random.nextDouble(minY, maxY).dp
        }
    }
}
