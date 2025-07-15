package model.graph.pathfinding

import model.graph.concrete.UndirectedUnweightedGraph
import model.graph.concrete.UndirectedWeightedGraph
import model.graph.contracts.Graph
import model.result.PathfindingErrors
import model.result.Result

class BFSStrategy <E: Comparable<E>, V: Comparable<V>>: PathfindingStrategy<E, V> {
    override fun findPath(
        graph: Graph<E, V>?,
        start: V,
        end: V,
        maxPaths: Int,
    ): Result<List<List<E>>> {
        val validateResult = PathfindingValidator.validate(graph, start, end)
        if (validateResult is Result.Error) return validateResult
        if (graph == null) return Result.Error(PathfindingErrors.EmptyGraph())
        // The graph is checked in the validator, but here it is necessary for the Kotlin analyzer
        if (start == end) return Result.Success(listOf(emptyList()))

        val result = mutableListOf<List<E>>()
        val queue = ArrayDeque<Pair<V, List<E>>>()
        val visited = mutableSetOf<V>()
        queue.add(start to emptyList())

        while(queue.isNotEmpty() && result.size < maxPaths) {
            val (current, path) = queue.removeFirst()
            if (current == end) {
                result.add(path)
                continue
            }
            visited.add(current)
            for (edge in graph.edges.values) {
                if (edge.from.id == current && edge.to.id !in visited) {
                    queue.add(edge.to.id to path + edge.id)
                }
                if (graph is UndirectedUnweightedGraph<*> || graph is UndirectedWeightedGraph<*>) {
                    if (edge.to.id == current && edge.from.id !in visited) {
                        queue.add(edge.from.id to path + edge.id)
                    }
                }
            }
        }
        if (result.isEmpty()) {
            return Result.Error(PathfindingErrors.NoPathFound())
        }
        return Result.Success(result)
    }
}
