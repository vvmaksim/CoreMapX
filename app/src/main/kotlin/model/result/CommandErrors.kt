package model.result

sealed class CommandErrors(
    override val type: String,
    override val description: String? = null,
) : Error {
    data class EmptyCommand(
        val message: String = "Command cannot be empty",
    ) : CommandErrors(
            type = "EmptyCommand",
            description = message,
        )

    data class UnknownType(
        val commandType: String,
    ) : CommandErrors(
            type = "UnknownType",
            description = "Unknown type for command: $commandType",
        )

    data class UnknownEntity(
        val entity: String,
    ) : CommandErrors(
            type = "UnknownEntity",
            description = "Unknown entity for command: $entity",
        )

    data class InvalidParameterFormat(
        val param: String,
    ) : CommandErrors(
            type = "InvalidParameterFormat",
            description = "Invalid parameter format: $param",
        )

    data class MissingParameters(
        val params: String,
    ) : CommandErrors(
            type = "MissingParameters",
            description = "Missing required parameters: $params",
        )

    data class InvalidParameterType(
        val param: String,
        val expectedType: String,
    ) : CommandErrors(
            type = "InvalidParameterType",
            description = "Parameter $param must be $expectedType",
        )

    data class NoGraphSelected(
        val message: String = "No graph selected, create a new graph first",
    ) : CommandErrors(
            type = "NoGraphSelected",
            description = message,
        )

    data class VertexNotFound(
        val id: String,
    ) : CommandErrors(
            type = "VertexNotFound",
            description = "Vertex not found: id=$id",
        )

    data class EdgeAdditionFailed(
        val from: String,
        val to: String,
        val details: String = "",
    ) : CommandErrors(
            type = "EdgeAdditionFailed",
            description = "Failed to add edge: from=$from, to=$to $details",
        )

    data class EdgeDoesNotExist(
        val from: String,
        val to: String,
    ) : CommandErrors(
            type = "EdgeDoesNotExist",
            description = "The edge with from:$from and to:$to does not exist",
        )

    data class UnknownGraphType(
        val graphType: String,
    ) : CommandErrors(
            type = "UnknownGraphType",
            description = "Unknown graph type: $graphType",
        )

    data class UnknownLayoutStrategy(
        val strategyAsString: String,
    ) : CommandErrors(
            type = "UnknownLayoutStrategy",
            description = "Unknown layout strategy: $strategyAsString",
        )

    data class ViewmodelNotFounded(
        val message: String = "Viewmodel not founded",
    ) : CommandErrors(
            type = "ViewmodelNotFounded",
            description = message,
        )
}
