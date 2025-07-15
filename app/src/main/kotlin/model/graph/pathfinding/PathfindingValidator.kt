package model.graph.pathfinding

import model.graph.contracts.Graph
import model.result.PathfindingErrors
import model.result.Result

class PathfindingValidator <E: Comparable<E>, V: Comparable<V>> {
    companion object {
        fun <E : Comparable<E>, V : Comparable<V>> validateParametersWithGraph(
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

        fun <V : Comparable<V>> validateInputParameters(
            startId: Long?,
            endId: Long?,
            maxPaths: Int?,
            isVertexExist: (V) -> Boolean?,
            onStartError: () -> Unit,
            onEndError: () -> Unit,
            onMaxPathsError: () -> Unit,
            onSetErrorMessage: (String) -> Unit,
        ) {
            if (startId == null || endId == null) {
                if (startId == null) onStartError()
                if (endId == null) onEndError()
                onSetErrorMessage("Vertex id must be Long type")
                return
            }

            if (maxPaths == null) {
                onMaxPathsError()
                onSetErrorMessage("Max paths must be Int type")
                return
            }

            if (maxPaths < 1) {
                onMaxPathsError()
                onSetErrorMessage("Max paths must be positive integer")
                return
            }

            val startExist = isVertexExist(startId as V)
            val endExist = isVertexExist(endId as V)

            if (startExist == null || endExist == null) {
                onStartError()
                onEndError()
                onSetErrorMessage("Graph not found")
                return
            }
            if (!startExist && !endExist) {
                onStartError()
                onEndError()
                onSetErrorMessage("Vertices with $startId and $endId ids not found in graph")
                return
            }
            if (!startExist) {
                onStartError()
                onSetErrorMessage("Vertex with $startId id not found in graph")
                return
            }
            if (startExist && !endExist) {
                onEndError()
                onSetErrorMessage("Vertex with $endId id not found in graph")
                return
            }
        }
    }
}
