package model.graph.pathfinding

import model.graph.concrete.DirectedUnweightedGraph
import model.graph.concrete.DirectedWeightedGraph
import model.graph.contracts.Edge
import model.graph.contracts.Graph
import model.graph.entities.WeightedEdge
import model.result.PathfindingErrors
import model.result.Result
import org.coremapx.app.AppLogger.logDebug

class BellmanFordStrategy<E : Comparable<E>, V : Comparable<V>> : PathfindingStrategy<E, V> {
    override fun findPath(
        graph: Graph<E, V>?,
        start: V,
        end: V,
        maxPaths: Int,
    ): Result<List<List<E>>> {
        logDebug("Launched findPath() from BellmanFordStrategy with startId:$start, endId:$end, maxPaths:$maxPaths")
        val validateResult = PathfindingValidator.validateParametersWithGraph(graph, start, end)
        if (validateResult is Result.Error) return validateResult
        if (graph == null) return Result.Error(PathfindingErrors.EmptyGraph())
        // The graph is checked in the validator, but here it is necessary for the Kotlin analyzer
        if (start == end) return Result.Success(listOf(emptyList()))

        val distances = mutableMapOf<V, Long>().withDefault { Long.MAX_VALUE }
        val previous = mutableMapOf<V, Pair<V, E>?>()
        distances[start] = 0L

        val vertices = graph.vertices.keys.toList()
        val edges = graph.edges.values.toList()

        for (i in 1 until vertices.size) {
            for (edge in edges) {
                val u = edge.from.id
                val v = edge.to.id
                val weight = getEdgeWeight(edge)
                if (distances.getValue(u) != Long.MAX_VALUE && distances.getValue(u) + weight < distances.getValue(v)) {
                    distances[v] = distances.getValue(u) + weight
                    previous[v] = u to edge.id
                }
                if (!isDirected(graph)) {
                    if (distances.getValue(v) != Long.MAX_VALUE && distances.getValue(v) + weight < distances.getValue(u)) {
                        distances[u] = distances.getValue(v) + weight
                        previous[u] = v to edge.id
                    }
                }
            }
        }

        for (edge in edges) {
            val u = edge.from.id
            val v = edge.to.id
            val weight = getEdgeWeight(edge)
            if (distances.getValue(u) != Long.MAX_VALUE && distances.getValue(u) + weight < distances.getValue(v)) {
                return Result.Error(PathfindingErrors.NegativeCycle())
            }
            if (!isDirected(graph)) {
                if (distances.getValue(v) != Long.MAX_VALUE && distances.getValue(v) + weight < distances.getValue(u)) {
                    return Result.Error(PathfindingErrors.NegativeCycle())
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
