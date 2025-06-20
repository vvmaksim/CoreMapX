package model.fileHandler.converter

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.command.concrete.Command
import model.command.enums.CommandEntities
import model.command.enums.CommandTypes
import model.fileHandler.ConvertModes
import model.fileHandler.serializableEntities.Edge
import model.fileHandler.serializableEntities.Graph
import model.fileHandler.serializableEntities.GraphData
import model.fileHandler.serializableEntities.GraphInfo
import model.fileHandler.serializableEntities.Vertex
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.config.PrivateConfig
import java.io.File
import java.io.IOException

class IRToJSONConverter : FileConverter() {
    override fun convert(
        file: File,
        convertMode: ConvertModes,
        graphId: Long?,
    ): Result<File> {
        val lines: List<String>
        try {
            lines = file.readLines().map { it.trim() }.filter { it.isNotEmpty() }
        } catch (ex: IOException) {
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
                                id = commandResult.data.parameters["id"]?.toLongOrNull() ?: 0L,
                                label = commandResult.data.parameters["label"].toString(),
                            ),
                        )
                    } else if (commandResult.data.entity == CommandEntities.EDGE) {
                        if (info["isWeighted"] == "true") {
                            edges.add(
                                Edge(
                                    from = commandResult.data.parameters["from"]?.toLongOrNull() ?: 0L,
                                    to = commandResult.data.parameters["to"]?.toLongOrNull() ?: 0L,
                                    weight = commandResult.data.parameters["weight"]?.toLongOrNull() ?: 0L,
                                ),
                            )
                        } else if (info["isWeighted"] == "false") {
                            edges.add(
                                Edge(
                                    from = commandResult.data.parameters["from"]?.toLongOrNull() ?: 0L,
                                    to = commandResult.data.parameters["to"]?.toLongOrNull() ?: 0L,
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
        val filePath =
            when (convertMode) {
                ConvertModes.SAVE -> "${file.parent}/${file.nameWithoutExtension}.json"
                ConvertModes.LOAD -> "${PrivateConfig.UserDirectory.TEMP_DIR_PATH}/${file.nameWithoutExtension}.json"
            }
        file.deleteOnExit()
        val jsonFile = File(filePath)
        jsonFile.writeText(json.encodeToString(graphData))
        if (convertMode == ConvertModes.LOAD) {
            jsonFile.deleteOnExit()
        }
        return Result.Success(jsonFile)
    }
}
