package viewmodel.visualizationStrategy

import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import model.graph.contracts.Edge
import org.coremapx.app.AppLogger.logDebug
import viewmodel.graph.VertexViewModel
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.random.Random

class ForceDirectedStrategy<E : Comparable<E>, V : Comparable<V>>(
    private var params: AnimationParameters = AnimationParameters(),
) : AnimatedVisualizationStrategy<E, V> {
    var edges: Collection<Edge<E, V>>? = null
    private var positions: MutableMap<VertexViewModel<V>, Pair<Double, Double>> = mutableMapOf()
    private var displacementMap: MutableMap<VertexViewModel<V>, Pair<Double, Double>> = mutableMapOf()
    private var vertexIdMap: Map<String, VertexViewModel<V>> = mapOf()
    private var n: Int = 0
    private var k: Double = 0.0
    private var animationJob: Job? = null
    private var isAnimating = false

    override fun place(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
    ) {
        logDebug("Launched place() function for ForceDirectedStrategy")
        initializePositions(width, height, vertices)
    }

    fun initializePositions(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
    ) {
        logDebug("Launched initializePositions() function for ForceDirectedStrategy")
        if (vertices == null || vertices.size <= 1) return
        n = vertices.size
        k = sqrt(params.area / n)
        positions =
            vertices
                .associateWith {
                    Pair(
                        Random.nextDouble(0.0, width),
                        Random.nextDouble(0.0, height),
                    )
                }.toMutableMap()
        displacementMap = vertices.associateWith { Pair(0.0, 0.0) }.toMutableMap()
        vertexIdMap = vertices.associateBy { it.label }
        for (vertex in vertices) {
            val pos = positions[vertex]
            if (pos != null) {
                vertex.x = pos.first.dp
                vertex.y = pos.second.dp
            }
        }
    }

    override fun startAnimation(
        scope: CoroutineScope,
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
        edges: Collection<Edge<E, V>>?,
        iterations: Int,
        onFrame: (() -> Unit)?,
    ) {
        logDebug("Launched startAnimation() function for ForceDirectedStrategy")
        stopAnimation()
        isAnimating = true
        if (vertices == null || edges == null) return
        if (positions.isEmpty()) {
            initializePositions(width, height, vertices)
        }
        animationJob =
            scope.launch {
                repeat(iterations) {
                    if (!isActive || !isAnimating) return@launch
                    nextIteration(width, height, vertices, edges)
                    onFrame?.invoke()
                    delay(16)
                }
                isAnimating = false
            }
    }

    override fun stopAnimation() {
        logDebug("Launched stopAnimation() function for ForceDirectedStrategy")
        isAnimating = false
        animationJob?.cancel()
        animationJob = null
    }

    override fun isRunning(): Boolean = isAnimating

    override fun setParameters(params: AnimationParameters) {
        this.params = params
        logDebug(
            "Set parameters in ForceDirectedStrategy on: iterations:${params.iterations}, gravity:${params.gravity}, " +
                "area:${params.area}, speed:${params.speed}",
        )
    }

    override fun getParameters(): AnimationParameters = params.copy()

    fun nextIteration(
        width: Double,
        height: Double,
        vertices: Collection<VertexViewModel<V>>?,
        edges: Collection<Edge<E, V>>?,
    ) {
        if (vertices == null || vertices.size <= 1 || edges == null) return
        for (vertex in vertices) {
            displacementMap[vertex] = Pair(0.0, 0.0)
            for (other in vertices) {
                if (vertex === other) continue
                val posV = positions[vertex]
                val posU = positions[other]
                if (posV == null || posU == null) continue
                var dx = posV.first - posU.first
                var dy = posV.second - posU.second
                var dist = hypot(dx, dy)
                if (dist < 0.01) {
                    dist = 0.01
                    dx = Random.nextDouble(-1.0, 1.0)
                    dy = Random.nextDouble(-1.0, 1.0)
                }
                val force = k * k / dist
                val disp = displacementMap[vertex]
                if (disp != null) {
                    displacementMap[vertex] =
                        Pair(
                            disp.first + dx / dist * force,
                            disp.second + dy / dist * force,
                        )
                }
            }
        }
        for (edge in edges) {
            val fromLabel = edge.from.label
            val toLabel = edge.to.label
            val fromVertex = vertexIdMap[fromLabel]
            val toVertex = vertexIdMap[toLabel]
            if (fromVertex == null || toVertex == null) continue
            val posV = positions[fromVertex]
            val posU = positions[toVertex]
            if (posV == null || posU == null) continue
            val dx = posV.first - posU.first
            val dy = posV.second - posU.second
            var dist = hypot(dx, dy)
            if (dist < 0.01) dist = 0.01
            val force = dist * dist / k
            val fx = dx / dist * force
            val fy = dy / dist * force
            val dispV = displacementMap[fromVertex]
            val dispU = displacementMap[toVertex]
            if (dispV != null) {
                displacementMap[fromVertex] = Pair(dispV.first - fx, dispV.second - fy)
            }
            if (dispU != null) {
                displacementMap[toVertex] = Pair(dispU.first + fx, dispU.second + fy)
            }
        }
        for (vertex in vertices) {
            val disp = displacementMap[vertex]
            val pos = positions[vertex]
            if (disp == null || pos == null) continue
            val dist = hypot(disp.first, disp.second)
            var dx = 0.0
            var dy = 0.0
            if (dist > 0) {
                val limitedDist = min(params.speed * dist, k)
                dx = disp.first / dist * limitedDist
                dy = disp.second / dist * limitedDist
            }
            var x = pos.first + dx + (width / 2 - pos.first) * params.gravity
            var y = pos.second + dy + (height / 2 - pos.second) * params.gravity
            x = min(width, max(0.0, x))
            y = min(height, max(0.0, y))
            positions[vertex] = Pair(x, y)
            vertex.x = x.dp
            vertex.y = y.dp
        }
    }
}
