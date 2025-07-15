package model.result

sealed class PathfindingErrors(
    override val type: String,
    override val description: String? = null,
) : Error {
    data class EmptyGraph(
        val message: String = "Graph is empty",
    ) : PathfindingErrors(
        type = "EmptyGraph",
        description = message,
    )

    data class VertexNotFound(
        val vertexId: Long,
    ) : PathfindingErrors(
        type = "VertexNotFound",
        description = "Vertex with id:${vertexId} not found",
    )

    data class NoPathFound(
        val message: String = "No path found",
    ) : PathfindingErrors(
        type = "NoPathFound",
        description = message,
    )

    data class NegativeCycle(
        val message: String = "Negative cycle found",
    ) : PathfindingErrors(
            type = "NegativeCycle",
            description = message,
        )
}
