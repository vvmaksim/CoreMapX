package viewmodel.visualizationStrategy

import viewmodel.graph.VertexViewModel

interface VisualizationStrategy {
    fun <V : Comparable<V>> place(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
    )
}
