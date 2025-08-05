package model.graph.pathfinding

import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.contracts.Edge
import model.graph.contracts.Graph
import model.graph.entities.WeightedEdge
import model.result.PathfindingErrors
import model.result.Result
import org.coremapx.app.AppLogger.logDebug
import java.util.PriorityQueue

class DijkstraStrategy<E : Comparable<E>, V : Comparable<V>> : PathfindingStrategy<E, V> {
    override fun findPath(
        graph: Graph<E, V>?,
        start: V,
        end: V,
        maxPaths: Int,
    ): Result<List<List<E>>> {
        logDebug("Launched findPath() from DijkstraStrategy with startId:$start, endId:$end, maxPaths:$maxPaths")
        val validateResult = PathfindingValidator.validateParametersWithGraph(graph, start, end)
        if (validateResult is Result.Error) return validateResult
        if (graph == null) return Result.Error(PathfindingErrors.EmptyGraph())
        // The graph is checked in the validator, but here it is necessary for the Kotlin analyzer
        if (start == end) return Result.Success(listOf(emptyList()))

        val distances = mutableMapOf<V, Long>().withDefault { Long.MAX_VALUE }
        val previous = mutableMapOf<V, Pair<V, E>?>()
        val visited = mutableSetOf<V>()

        distances[start] = 0L

        val queue = PriorityQueue<Pair<V, Long>>(compareBy { it.second })
        queue.add(start to 0L)

        while (queue.isNotEmpty()) {
            val (current, currentDist) = queue.poll()
            if (current in visited) continue
            visited.add(current)

            if (current == end) break

            for (edge in graph.edges.values) {
                val (neighbor, weight) =
                    when {
                        edge.from.id == current -> edge.to.id to getEdgeWeight(edge)
                        edge.to.id == current && !isDirected(graph) -> edge.from.id to getEdgeWeight(edge)
                        else -> continue
                    }
                if (neighbor in visited) continue
                val newDist = currentDist + weight
                if (newDist < distances.getValue(neighbor)) {
                    distances[neighbor] = newDist
                    previous[neighbor] = current to edge.id
                    queue.add(neighbor to newDist)
                }
            }
        }

        val path = mutableListOf<E>()
        var current: V? = end
        while (current != null) {
            val prevEdge = previous[current] ?: break
            val (prev, edgeId) = prevEdge
            path.add(0, edgeId)
            current = prev
        }

        if (path.isEmpty() || (path.firstOrNull() == null && start != end)) {
            return Result.Error(PathfindingErrors.NoPathFound())
        }

        return Result.Success(listOf(path))
    }

    private fun getEdgeWeight(edge: Edge<E, V>): Long = if (edge is WeightedEdge<E, V>) edge.weight else 1L

    private fun isDirected(graph: Graph<E, V>): Boolean = graph is DirectedUnweightedGraph || graph is DirectedWeightedGraph
}
