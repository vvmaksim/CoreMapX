package model.fileHandler.converter

import kotlinx.serialization.json.Json
import model.fileHandler.ConvertModes
import model.fileHandler.serializableEntities.GraphData
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.config.PrivateConfig
import java.io.File

class JSONToIRConverter : FileConverter() {
    override fun convert(
        file: File,
        convertMode: ConvertModes,
        graphId: Long?,
    ): Result<File> {
        logDebug(
            "Launched convert() from JSONToIRConverter with fileAbsolutePath:${file.absolutePath}, " +
                "convertMode:${convertMode.name}, graphId:$graphId",
        )
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
        val filePath =
            when (convertMode) {
                ConvertModes.SAVE -> "${file.parent}/${file.nameWithoutExtension}.graph"
                ConvertModes.LOAD -> "${PrivateConfig.UserDirectory.TEMP_DIR_PATH}/${file.nameWithoutExtension}.graph"
            }
        val irFile = File(filePath)
        irFile.writeText(ir.toString())
        if (convertMode == ConvertModes.LOAD) irFile.deleteOnExit()
        return Result.Success(irFile)
    }
}
