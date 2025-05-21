package model.result

sealed class FileErrors(
    override val type: String,
    override val description: String? = null,
) : Error {
    data class UnknownFileExtension(
        val message: String = "Unknown file extension",
    ) : CommandError(
        type = "UnknownFileExtension",
        description = message,
    )

    data class ErrorReadingFile(
        val exceptionMessage: String,
    ) : CommandError(
        type = "ErrorReadingFile",
        description = "Error reading the file. Ex: $exceptionMessage",
    )

    data class NotFoundInfoMarker(
        val message: String = "The information marker `Info:` was not found",
    ) : CommandError(
        type = "NotFoundInfoMarker",
        description = message,
    )

    data class NotFoundGraphMarker(
        val message: String = "The graph marker `Graph:` was not found",
    ) : CommandError(
        type = "NotFoundGraphMarker",
        description = message,
    )

    data class BrokenOrderMarkers(
        val message: String = "The order of markers is broken",
    ) : CommandError(
        type = "BrokenOrderMarkers",
        description = message,
    )

    data class IncorrectInfoProperty(
        val line: String,
    ) : CommandError(
        type = "IncorrectInfoProperty",
        description = "Incorrect line: $line. Info property must be in format: `propertyName=value`. It is forbidden to use the `=` symbol in the value",
    )

    data class IncorrectBooleanValue(
        val propertyName: String,
    ) : CommandError(
        type = "IncorrectBooleanValue",
        description = "Property $propertyName must be `true` or `false` only",
    )

    data class MissingValue(
        val propertyName: String,
    ) : CommandError(
        type = "MissingValue",
        description = "Missing value in the property $propertyName",
    )

    data class MissingProperty(
        val line: String,
    ) : CommandError(
        type = "MissingProperty",
        description = "Missing property in line: $line",
    )

    data class LonelyEqualitySymbol(
        val line: String,
    ) : CommandError(
        type = "lonelyEqualitySymbol",
        description = "Incorrect line: $line.The `=` symbol cannot be applied without the name of the property and the value",
    )

    data class IncorrectLine(
        val line: String,
    ) : CommandError(
        type = "IncorrectLine",
        description = "Incorrect line: $line",
    )

    data class UnknownProperty(
        val propertyName: String,
    ) : CommandError(
        type = "UnknownProperty",
        description = "Unknown property: $propertyName",
    )

    data class ConverterError(
        val error: String,
    ) : CommandError(
        type = "ConverterError",
        description = error,
    )

    data class IncorrectCommand(
        val line: String,
        val commandErrorType: String,
        val commandErrorDescription: String?,
    ) : CommandError(
        type = "IncorrectCommand",
        description = "Error in line: $line. ${commandErrorType}.${commandErrorDescription}",
    )
}
