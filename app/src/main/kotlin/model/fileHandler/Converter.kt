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
        ): Result<File> {
            val validateResult = Validator.validate(file)
            if (validateResult is Result.Error) return validateResult
            return when (to) {
                FileExtensions.GRAPH -> {
                    convertAnyToIR(file)
                }
                FileExtensions.JSON -> {
                    convertAnyToJSON(file)
                }
            }
        }

        private fun convertAnyToIR(file: File): Result<File> =
            when (file.extension) {
                "graph" -> Result.Success(file)
                "json" -> convertJSONToIR(file)
                else -> Result.Error(FileErrors.UnknownFileExtension())
            }

        // TODO - Необходимо написать тело функции
        private fun convertAnyToJSON(file: File): Result<File> = Result.Success(file)

        private fun convertJSONToIR(file: File): Result<File> {
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
            val irFile =
                File("$baseUserDirPath/data/temp/${file.nameWithoutExtension}.graph").apply {
                    writeText(ir.toString())
                    deleteOnExit()
                    // TODO - Сделать возможность менять в конфиге необходимость удаления после конвертирования
                }
            return Result.Success(irFile)
        }

        private fun convertIRToJSON(file: File): Result<File> {
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
                            if (info["isWeight"] == "true") {
                                edges.add(
                                    Edge(
                                        from = commandResult.data.parameters["from"]?.toIntOrNull() ?: 0,
                                        to = commandResult.data.parameters["to"]?.toIntOrNull() ?: 0,
                                        weight = commandResult.data.parameters["weight"]?.toIntOrNull() ?: 0,
                                    ),
                                )
                            } else if (info["isWeight"] == "false") {
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
            val jsonFile =
                File("$baseUserDirPath/data/temp/${file.nameWithoutExtension}.json").apply {
                    writeText(json.encodeToString(graphData))
                    deleteOnExit()
                    // TODO - Сделать возможность менять в конфиге необходимость удаления после конвертирования
                }
            return Result.Success(jsonFile)
        }
    }
}
