package model.fileHandler.converters

import model.commands.classes.Command
import model.commands.enums.CommandEntities
import model.commands.enums.CommandTypes
import model.databases.sqlite.createDatabase
import model.databases.sqlite.repositories.EdgeRepository
import model.databases.sqlite.repositories.GraphRepository
import model.databases.sqlite.repositories.VertexRepository
import model.fileHandler.ConvertModes
import model.fileHandler.serializableDataClasses.Edge
import model.fileHandler.serializableDataClasses.Vertex
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import java.io.File
import java.io.IOException

class IRToSQLConverter : FileConverter() {
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

        val filePath =
            when (convertMode) {
                ConvertModes.SAVE -> "${file.parent}/${file.nameWithoutExtension}.db"
                ConvertModes.LOAD -> "$baseUserDirPath/data/temp/${file.nameWithoutExtension}.db"
            }
        val database = createDatabase(filePath)
        val graphId =
            GraphRepository(database).insertGraph(
                name =
                    info["name"] ?: return Result.Error(FileErrors.ConverterError("`name` can not be null")),
                author =
                    info["author"] ?: return Result.Error(FileErrors.ConverterError("`author` can not be null")),
                isDirected =
                    info["isDirected"]?.toBoolean() ?: return Result.Error(FileErrors.ConverterError("`isDirected` can not be null")),
                isWeighted =
                    info["isWeighted"]?.toBoolean() ?: return Result.Error(FileErrors.ConverterError("`isWeighted` can not be null")),
            )

        val vertexRepository = VertexRepository(database)
        vertices.forEach { vertex ->
            vertexRepository.insertVertex(
                graphId = graphId,
                vertexId = vertex.id,
                label = vertex.label,
            )
        }
        val edgeRepository = EdgeRepository(database)
        edges.forEach { edge ->
            edgeRepository.insertEdge(
                graphId = graphId,
                fromVertex = edge.from,
                toVertex = edge.to,
                weight = edge.weight,
            )
        }

        file.deleteOnExit()
        return Result.Success(File(filePath))
    }
}
