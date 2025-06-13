package model.fileHandler.converters

import extensions.toBooleanOrNull
import model.databases.sqlite.createDatabase
import model.databases.sqlite.repositories.EdgeRepository
import model.databases.sqlite.repositories.GraphRepository
import model.databases.sqlite.repositories.VertexRepository
import model.fileHandler.ConvertModes
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.userDirectory.UserDirectory.baseUserDirPath
import org.coremapx.graph.GraphDatabase
import java.io.File

class SQLToIRConverter : FileConverter() {
    override fun convert(
        file: File,
        convertMode: ConvertModes,
        graphId: Long?,
    ): Result<File> {
        if (graphId == null) return Result.Error(FileErrors.ConverterError("graphId can not be null"))

        val database: GraphDatabase = createDatabase(file.absolutePath)

        val info =
            GraphRepository(database).getGraphById(graphId)
                ?: return Result.Error(FileErrors.ConverterError("Graph info with graphId:$graphId not found"))

        val isDirected =
            info.isDirected.toBooleanOrNull()
                ?: return Result.Error(FileErrors.ConverterError("isDirected must be `1` or `0`"))
        val isWeighted =
            info.isDirected.toBooleanOrNull()
                ?: return Result.Error(FileErrors.ConverterError("isWeighted must be `1` or `0`"))

        val vertices = VertexRepository(database).getVerticesByGraph(graphId)
        val edges = EdgeRepository(database).getEdgesByGraph(graphId)

        val ir =
            StringBuilder().apply {
                append("Info:\n")
                append("name=${info.name}\n")
                append("author=${info.author}\n")
                append("isDirected=$isDirected\n")
                append("isWeighted=$isWeighted\n")
                append("Graph:\n")
            }

        vertices.forEach { vertex ->
            ir.append("add vertex ${vertex.vertex_id} ${vertex.label}\n")
        }
        if (isWeighted) {
            edges.forEach { edge ->
                ir.append("add edge ${edge.from_vertex} ${edge.to_vertex} ${edge.weight}\n")
            }
        } else {
            edges.forEach { edge ->
                ir.append("add edge ${edge.from_vertex} ${edge.to_vertex}\n")
            }
        }
        val filePath =
            when (convertMode) {
                ConvertModes.SAVE -> "${file.parent}/${file.nameWithoutExtension}.graph"
                ConvertModes.LOAD -> "$baseUserDirPath/data/temp/${file.nameWithoutExtension}.graph"
            }
        val irFile = File(filePath)
        irFile.writeText(ir.toString())
        if (convertMode == ConvertModes.LOAD) irFile.deleteOnExit()
        return Result.Success(irFile)
    }
}
