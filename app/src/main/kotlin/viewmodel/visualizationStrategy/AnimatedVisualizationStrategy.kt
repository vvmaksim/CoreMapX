package viewmodel.visualizationStrategy

import kotlinx.coroutines.CoroutineScope
import model.graph.contracts.Edge
import viewmodel.graph.VertexViewModel

interface AnimatedVisualizationStrategy<E : Comparable<E>, V : Comparable<V>> : VisualizationStrategy<E, V> {
    fun startAnimation(
        scope: CoroutineScope,
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
        edges: Collection<Edge<E, V>>?,
        iterations: Int = 500,
        onFrame: (() -> Unit)? = null,
    )

    fun stopAnimation()

    fun isRunning(): Boolean

    fun setParameters(params: AnimationParameters)

    fun getParameters(): AnimationParameters
}
