package model.result

sealed class FileErrors(
    override val type: String,
    override val description: String? = null,
) : Error {
    data class UnknownFileExtension(
        val message: String = "Unknown file extension",
    ) : FileErrors(
            type = "UnknownFileExtension",
            description = message,
        )

    data class ErrorReadingFile(
        val exceptionMessage: String,
    ) : FileErrors(
            type = "ErrorReadingFile",
            description = "Error reading the file. Ex: $exceptionMessage",
        )

    data class ErrorSavingFile(
        val exceptionMessage: String,
    ) : FileErrors(
            type = "ErrorSavingFile",
            description = "Error saving the file. Ex: $exceptionMessage",
        )

    data class InvalidParameter(
        val parameter: String,
        val errorDescription: String,
    ) : FileErrors(
            type = "InvalidParameter",
            description = "Invalid parameter $parameter. $errorDescription",
        )

    data class NotFoundInfoMarker(
        val message: String = "The information marker `Info:` was not found",
    ) : FileErrors(
            type = "NotFoundInfoMarker",
            description = message,
        )

    data class NotFoundGraphMarker(
        val message: String = "The graph marker `Graph:` was not found",
    ) : FileErrors(
            type = "NotFoundGraphMarker",
            description = message,
        )

    data class BrokenOrderMarkers(
        val message: String = "The order of markers is broken",
    ) : FileErrors(
            type = "BrokenOrderMarkers",
            description = message,
        )

    data class IncorrectInfoProperty(
        val line: String,
    ) : FileErrors(
            type = "IncorrectInfoProperty",
            description = @Suppress("ktlint:standard:max-line-length")
            "Incorrect line: $line. Info property must be in format: `propertyName=value`. It is forbidden to use the `=` symbol in the value",
        )

    data class IncorrectBooleanValue(
        val propertyName: String,
    ) : FileErrors(
            type = "IncorrectBooleanValue",
            description = "Property $propertyName must be `true` or `false` only",
        )

    data class MissingValue(
        val propertyName: String,
    ) : FileErrors(
            type = "MissingValue",
            description = "Missing value in the property $propertyName",
        )

    data class IncorrectLine(
        val line: String,
    ) : FileErrors(
            type = "IncorrectLine",
            description = "Incorrect line: $line",
        )

    data class UnknownProperty(
        val propertyName: String,
    ) : FileErrors(
            type = "UnknownProperty",
            description = "Unknown property: $propertyName",
        )

    data class ConverterError(
        val error: String,
    ) : FileErrors(
            type = "ConverterError",
            description = error,
        )

    data class IncorrectCommand(
        val line: String,
        val commandErrorType: String,
        val commandErrorDescription: String?,
    ) : FileErrors(
            type = "IncorrectCommand",
            description = "Error in line: $line. $commandErrorType.$commandErrorDescription",
        )

    data class RecurringProperties(
        val propertyName: String,
    ) : FileErrors(
            type = "RecurringProperties",
            description = "The property $propertyName must be defined only once",
        )
}
