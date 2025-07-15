package model.graph.pathfinding

import model.graph.contracts.Graph
import model.result.Result

interface PathfindingStrategy<E : Comparable<E>, V : Comparable<V>> {
    fun findPath(
        graph: Graph<E, V>?,
        start: V,
        end: V,
        maxPaths: Int = 1,
    ): Result<List<List<E>>>
}
