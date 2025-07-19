package viewmodel.visualizationStrategy

import viewmodel.graph.VertexViewModel

interface VisualizationStrategy<E : Comparable<E>, V : Comparable<V>> {
    fun place(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
    )
}
