package model.fileHandler

import kotlinx.serialization.json.Json
import model.commands.classes.Command
import model.fileHandler.serializableDataClasses.GraphData
import model.result.Result
import model.result.FileErrors
import java.io.File

class Validator {
    companion object {
        private val optionalProperties = listOf("name", "author")
        private val requiredProperties = listOf("isDirected", "isWeighted")
        private val allBooleanProperties = listOf("isDirected", "isWeighted")

        fun validate(file: File): Result<String> {
            return when (file.extension) {
                "graph" -> validateIR(file)
                "json" -> validateJSON(file)
                else -> Result.Error(FileErrors.UnknownFileExtension())
            }
        }

        private fun validateIR(file: File): Result<String> {
            val lines: List<String>
            try {
                lines = file.readLines().map { it.trim() }.filter { it.isNotEmpty() }
            } catch (ex: Exception) {
                return Result.Error(FileErrors.ErrorReadingFile(ex.toString()))
            }
            val infoMarkerIndex = lines.indexOf("Info:")
            val commandsMarkerIndex = lines.indexOf("Graph:")

            if (infoMarkerIndex == -1) return Result.Error(FileErrors.NotFoundInfoMarker())
            if (commandsMarkerIndex == -1) return Result.Error(FileErrors.NotFoundGraphMarker())
            if (infoMarkerIndex >= commandsMarkerIndex) return Result.Error(FileErrors.BrokenOrderMarkers())

            val infoLines: List<String> = lines.subList(infoMarkerIndex + 1, commandsMarkerIndex)
            val commandLines: List<String> = lines.subList(commandsMarkerIndex + 1, lines.size)

            for (line in infoLines) {
                val splitLine = line.split("=").map { it.trim() }
                if (splitLine.size == 1) {
                    return Result.Error(FileErrors.IncorrectLine(line))
                }
                if (splitLine[0] !in optionalProperties && splitLine[0] !in requiredProperties) {
                    return Result.Error(FileErrors.UnknownProperty(splitLine[0]))
                }
                if (splitLine.size > 2) {
                    return Result.Error(FileErrors.IncorrectInfoProperty(line))
                }
                if (splitLine[0] == "" && splitLine[1] == "") {
                    return Result.Error(FileErrors.LonelyEqualitySymbol(line))
                }
                if (splitLine[0] == "") {
                    return Result.Error(FileErrors.MissingProperty(line))
                }
                if (splitLine[1] == "") {
                    return Result.Error(FileErrors.MissingValue(splitLine[0]))
                }
                if (splitLine[0] in allBooleanProperties && splitLine[1].lowercase() !in listOf("true", "false")) {
                    return Result.Error(FileErrors.IncorrectBooleanValue(splitLine[0]))
                }
            }

            for (command in commandLines) {
                val commandResult = Command.create(command)
                if (commandResult is Result.Error) {
                    return Result.Error(
                        FileErrors.IncorrectCommand(
                            line = command,
                            commandErrorType = commandResult.error.type,
                            commandErrorDescription = commandResult.error.description,
                        )
                    )
                }
            }

            return Result.Success("IR is correct")
        }

        private fun validateJSON(file: File): Result<String> {
            try {
                val json = Json { ignoreUnknownKeys = true }
                val graphData = json.decodeFromString<GraphData>(file.readText())
                return Result.Success("JSON is correct")
            } catch (ex: Exception) {
                return Result.Error(FileErrors.ConverterError(ex.toString()))
            }
        }
    }
}
