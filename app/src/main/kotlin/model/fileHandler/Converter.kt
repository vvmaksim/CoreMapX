package model.fileHandler

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.commands.classes.Command
import model.commands.enums.CommandEntities
import model.commands.enums.CommandTypes
import model.fileHandler.serializableDataClasses.Edge
import model.fileHandler.serializableDataClasses.Graph
import model.fileHandler.serializableDataClasses.GraphData
import model.fileHandler.serializableDataClasses.GraphInfo
import model.fileHandler.serializableDataClasses.Vertex
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import java.io.File

class Converter {
    companion object {
        fun convert(
            file: File,
            to: FileExtensions,
            convertMode: ConvertModes,
        ): Result<File> {
            val validateResult = Validator.validate(file)
            if (validateResult is Result.Error) return validateResult
            return when (to) {
                FileExtensions.GRAPH -> {
                    convertAnyToIR(file, convertMode)
                }
                FileExtensions.JSON -> {
                    convertAnyToJSON(file, convertMode)
                }
            }
        }

        private fun convertAnyToIR(file: File, convertMode: ConvertModes): Result<File> =
            when (file.extension) {
                "graph" -> Result.Success(file)
                "json" -> convertJSONToIR(file, convertMode)
                else -> Result.Error(FileErrors.UnknownFileExtension())
            }

        private fun convertAnyToJSON(file: File, convertMode: ConvertModes): Result<File> =
            when (file.extension) {
                "graph" -> convertIRToJSON(file, convertMode)
                "json" -> Result.Success(file)
                else -> Result.Error(FileErrors.UnknownFileExtension())
        }

        private fun convertJSONToIR(file: File, convertMode: ConvertModes): Result<File> {
            val json = Json { ignoreUnknownKeys = true }
            val graphData: GraphData
            try {
                graphData = json.decodeFromString<GraphData>(file.readText())
            } catch (ex: Exception) {
                return Result.Error(FileErrors.ConverterError(ex.toString()))
            }

            val ir =
                StringBuilder().apply {
                    append("Info:\n")
                    append("name=${graphData.info.name}\n")
                    append("author=${graphData.info.author}\n")
                    append("isDirected=${graphData.info.isDirected}\n")
                    append("isWeighted=${graphData.info.isWeighted}\n")
                    append("Graph:\n")
                }
            graphData.graph.vertices.forEach { vertex ->
                ir.append("add vertex ${vertex.id} ${vertex.label}\n")
            }
            if (graphData.info.isWeighted) {
                graphData.graph.edges.forEach { edge ->
                    ir.append("add edge ${edge.from} ${edge.to} ${edge.weight}\n")
                }
            } else {
                graphData.graph.edges.forEach { edge ->
                    ir.append("add edge ${edge.from} ${edge.to}\n")
                }
            }
            val fileName =
                when (convertMode) {
                    ConvertModes.SAVE -> "${file.parent}/${file.nameWithoutExtension}.graph"
                    ConvertModes.LOAD -> "$baseUserDirPath/data/temp/${file.nameWithoutExtension}.graph"
                }
            val irFile = File(fileName)
            irFile.writeText(ir.toString())
            if (convertMode == ConvertModes.LOAD) irFile.deleteOnExit()
            return Result.Success(irFile)
        }

        private fun convertIRToJSON(file: File, convertMode: ConvertModes): Result<File> {
            val lines: List<String>
            try {
                lines = file.readLines().map { it.trim() }.filter { it.isNotEmpty() }
            } catch (ex: Exception) {
                return Result.Error(FileErrors.ErrorReadingFile(ex.toString()))
            }

            val info = mutableMapOf<String, String>()
            val vertices = mutableListOf<Vertex>()
            val edges = mutableListOf<Edge>()

            val infoMarkerIndex = lines.indexOf("Info:")
            val commandsMarkerIndex = lines.indexOf("Graph:")

            val infoLines: List<String> = lines.subList(infoMarkerIndex + 1, commandsMarkerIndex)
            val commandLines: List<String> = lines.subList(commandsMarkerIndex + 1, lines.size)

            for (line in infoLines) {
                val splitLine = line.split("=").map { it.trim() }
                info[splitLine[0]] = splitLine[1]
            }

            commandLines.forEach { command ->
                val commandResult = Command.create(command)
                if (commandResult is Result.Success) {
                    if (commandResult.data.type == CommandTypes.ADD) {
                        if (commandResult.data.entity == CommandEntities.VERTEX) {
                            vertices.add(
                                Vertex(
                                    id = commandResult.data.parameters["id"]?.toIntOrNull() ?: 0,
                                    label = commandResult.data.parameters["label"].toString(),
                                ),
                            )
                        } else if (commandResult.data.entity == CommandEntities.EDGE) {
                            if (info["isWeighted"] == "true") {
                                edges.add(
                                    Edge(
                                        from = commandResult.data.parameters["from"]?.toIntOrNull() ?: 0,
                                        to = commandResult.data.parameters["to"]?.toIntOrNull() ?: 0,
                                        weight = commandResult.data.parameters["weight"]?.toIntOrNull() ?: 0,
                                    ),
                                )
                            } else if (info["isWeighted"] == "false") {
                                edges.add(
                                    Edge(
                                        from = commandResult.data.parameters["from"]?.toIntOrNull() ?: 0,
                                        to = commandResult.data.parameters["to"]?.toIntOrNull() ?: 0,
                                    ),
                                )
                            }
                        }
                    }
                }
            }
            val graphData =
                GraphData(
                    GraphInfo(
                        name = info["name"],
                        author = info["author"],
                        isDirected = info["isDirected"].toBoolean(),
                        isWeighted = info["isWeighted"].toBoolean(),
                    ),
                    Graph(
                        vertices = vertices,
                        edges = edges,
                    ),
                )
            val json = Json { prettyPrint = true }
            val fileName =
                when (convertMode) {
                    ConvertModes.SAVE -> "${file.parent}/${file.nameWithoutExtension}.json"
                    ConvertModes.LOAD -> "$baseUserDirPath/data/temp/${file.nameWithoutExtension}.json"
                }
            file.deleteOnExit()
            val jsonFile = File(fileName)
            jsonFile.writeText(json.encodeToString(graphData))
            if (convertMode == ConvertModes.LOAD) { jsonFile.deleteOnExit() }
            return Result.Success(jsonFile)
        }
    }
}
