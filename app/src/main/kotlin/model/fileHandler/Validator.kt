package model.fileHandler

import extensions.toBooleanOrNull
import kotlinx.serialization.json.Json
import model.command.`class`.Command
import model.database.sqlite.createDatabase
import model.database.sqlite.repository.EdgeRepository
import model.database.sqlite.repository.GraphRepository
import model.database.sqlite.repository.VertexRepository
import model.fileHandler.serializableDataClasses.GraphData
import model.result.FileErrors
import model.result.Result
import java.io.File

class Validator {
    companion object {
        private val optionalProperties = listOf("name", "author")
        private val requiredProperties = listOf("isDirected", "isWeighted")
        private val allBooleanProperties = listOf("isDirected", "isWeighted")

        fun validate(file: File): Result<String> =
            when (file.extension) {
                "graph" -> validateIR(file)
                "json" -> validateJSON(file)
                "db" -> validateSQLDB(file)
                else -> Result.Error(FileErrors.UnknownFileExtension())
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
            val info = mutableMapOf<String, String>()

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
                if (splitLine[1] == "") {
                    return Result.Error(FileErrors.MissingValue(splitLine[0]))
                }
                if (splitLine[0] in allBooleanProperties && splitLine[1].lowercase() !in listOf("true", "false")) {
                    return Result.Error(FileErrors.IncorrectBooleanValue(splitLine[0]))
                }
                if (info[splitLine[0]] != null) {
                    return Result.Error(FileErrors.RecurringProperties(splitLine[0]))
                }
                info[splitLine[0]] = splitLine[1]
            }

            for (command in commandLines) {
                val commandResult = Command.create(command)
                if (commandResult is Result.Error) {
                    return Result.Error(
                        FileErrors.IncorrectCommand(
                            line = command,
                            commandErrorType = commandResult.error.type,
                            commandErrorDescription = commandResult.error.description,
                        ),
                    )
                }
            }

            return Result.Success("IR is correct")
        }

        private fun validateJSON(file: File): Result<String> {
            try {
                val json = Json { ignoreUnknownKeys = true }
                json.decodeFromString<GraphData>(file.readText())
                return Result.Success("JSON is correct")
            } catch (ex: Exception) {
                return Result.Error(FileErrors.ConverterError(ex.toString()))
            }
        }

        private fun validateSQLDB(file: File): Result<String> {
            if (!file.exists() || file.length() == 0L) {
                return Result.Error(FileErrors.ErrorReadingFile("Database file does not exist or is empty"))
            }

            val database =
                try {
                    createDatabase(file.absolutePath)
                } catch (ex: Exception) {
                    return Result.Error(FileErrors.ErrorReadingFile("Cannot open database: ${ex.message}"))
                }

            try {
                GraphRepository(database).getAllGraphs()
                VertexRepository(database).getVerticesByGraph(0)
                EdgeRepository(database).getEdgesByGraph(0)
            } catch (ex: Exception) {
                return Result.Error(FileErrors.ErrorReadingFile("One or more required tables are missing: ${ex.message}"))
            }

            val graphs =
                try {
                    GraphRepository(database).getAllGraphs()
                } catch (ex: Exception) {
                    return Result.Error(FileErrors.ErrorReadingFile("Cannot read graphs: ${ex.message}"))
                }

            graphs.forEach { graph ->
                val graphId = graph.graph_id
                val vertexRepository = VertexRepository(database).getVerticesByGraph(graphId)
                val edgeRepository = EdgeRepository(database).getEdgesByGraph(graphId)

                val vertexIds = vertexRepository.map { it.vertex_id }
                if (vertexIds.size != vertexIds.toSet().size) {
                    return Result.Error(FileErrors.ErrorReadingFile("Duplicate vertex id in graph $graphId"))
                }

                val edgePairs = edgeRepository.map { Pair(it.from_vertex, it.to_vertex) }
                if (edgePairs.size != edgePairs.toSet().size) {
                    return Result.Error(FileErrors.ErrorReadingFile("Duplicate edge (from-to) in graph $graphId"))
                }

                val vertexIdSet = vertexIds.toSet()
                for (edge in edgeRepository) {
                    if (edge.from_vertex !in vertexIdSet || edge.to_vertex !in vertexIdSet) {
                        return Result.Error(
                            FileErrors.ErrorReadingFile(
                                "Edge (${edge.from_vertex}, ${edge.to_vertex}) in graph $graphId refers to non-existent vertex",
                            ),
                        )
                    }
                }

                val booleanInfo = listOf(graph.isDirected, graph.isWeighted)
                for (boolean in booleanInfo) {
                    if (boolean.toBooleanOrNull() == null) {
                        return Result.Error(FileErrors.ErrorReadingFile("Boolean field in graph $graphId is not valid: $boolean"))
                    }
                }
            }
            return Result.Success("DB is correct")
        }
    }
}
