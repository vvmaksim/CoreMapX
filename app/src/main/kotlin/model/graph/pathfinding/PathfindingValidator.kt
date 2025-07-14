package model.graph.pathfinding

import model.graph.contracts.Graph
import model.result.PathfindingErrors
import model.result.Result

class PathfindingValidator <E: Comparable<E>, V: Comparable<V>> {
    companion object {
        fun <E: Comparable<E>, V: Comparable<V>> validate(
            graph: Graph<E, V>?,
            start: V,
            end: V,
        ): Result<Boolean> {
            if (graph == null) return Result.Error(PathfindingErrors.EmptyGraph())
            if (graph.vertices.isEmpty() || graph.edges.isEmpty()) return Result.Error(PathfindingErrors.EmptyGraph())
            if (!graph.vertices.containsKey(start)) return Result.Error(PathfindingErrors.VertexNotFound(start as Long))
            if (!graph.vertices.containsKey(end)) return Result.Error(PathfindingErrors.VertexNotFound(end as Long))
            return Result.Success(true)
        }
    }
}
