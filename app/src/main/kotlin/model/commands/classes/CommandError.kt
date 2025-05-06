package model.commands.classes

sealed class CommandError(
    val type: String,
    val description: String? = null,
    ) {
    data class EmptyCommand(val message: String = "Command cannot be empty") : CommandError(
        type = "EmptyCommand",
        description = message,
    )

    data class UnknownType(val commandType: String) : CommandError(
        type = "UnknownType",
        description = "Unknown type for command: $commandType"
    )

    data class UnknownEntity(val entity: String) : CommandError(
        type = "UnknownEntity",
        description = "Unknown entity for command: $entity"
    )

    data class InvalidParameterFormat(val param: String) : CommandError(
        type = "InvalidParameterFormat",
        description = "Invalid parameter format: $param"
    )

    data class MissingParameters(val params: String) : CommandError(
        type = "MissingParameters",
        description = "Missing required parameters: $params"
    )

    data class InvalidParameterType(val param: String, val expectedType: String) : CommandError(
        type = "InvalidParameterType",
        description = "Parameter $param must be $expectedType"
    )

    data class NoGraphSelected(val message: String = "No graph selected, create a new graph first") : CommandError(
        type = "NoGraphSelected",
        description = message
    )

    data class VertexNotFound(val id: String) : CommandError(
        type = "VertexNotFound",
        description = "Vertex not found: id=$id"
    )

    data class EdgeAdditionFailed(val from: String, val to: String, val details: String = "") : CommandError(
        type = "EdgeAdditionFailed",
        description = "Failed to add edge: from=$from, to=$to $details"
    )

    data class UnknownGraphType(val graphType: String) : CommandError(
        type = "UnknownGraphType",
        description = "Unknown graph type: $graphType"
    )
}
