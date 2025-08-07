package model.fileHandler

import model.command.concrete.Command
import model.fileHandler.converter.Converter
import model.ir.GraphIR
import model.result.FileErrors
import model.result.Result
import org.coremapx.app.AppLogger.logDebug
import java.io.File

class Parser {
    companion object {
        private val optionalProperties = listOf("name", "author")

        fun parse(
            file: File,
            graphId: Long?,
        ): Result<GraphIR> {
            logDebug("Launched parse() from Parser with fileAbsolutePath:${file.absolutePath}, graphId:$graphId")
            var fileIR: File
            val converterResult =
                Converter.convert(
                    file = file,
                    to = FileExtensions.GRAPH,
                    mode = ConvertModes.LOAD,
                    graphId = graphId,
                )
            when (converterResult) {
                is Result.Error -> return converterResult
                is Result.Success -> fileIR = converterResult.data
            }

            val lines: List<String>
            try {
                lines = fileIR.readLines().map { it.trim() }.filter { it.isNotEmpty() }
            } catch (ex: Exception) {
                return Result.Error(FileErrors.ErrorReadingFile(ex.toString()))
            }
            val infoMarkerIndex = lines.indexOf("Info:")
            val commandsMarkerIndex = lines.indexOf("Graph:")
            val infoLines: List<String> = lines.subList(infoMarkerIndex + 1, commandsMarkerIndex)
            val commandLines: List<String> = lines.subList(commandsMarkerIndex + 1, lines.size)
            val warnings = mutableListOf<String>()
            val graphInfo: Map<String, String> = parseGraphInfo(infoLines, warnings)
            val commands: List<Result<Command>> = parseGraph(commandLines)

            return Result.Success(
                GraphIR(
                    name = graphInfo["name"] ?: "None",
                    author = graphInfo["author"] ?: "None",
                    isDirected = graphInfo["isDirected"]?.toBoolean() ?: false,
                    isWeighted = graphInfo["isWeighted"]?.toBoolean() ?: false,
                    warnings = warnings,
                    commands = commands,
                ),
            )
        }

        private fun parseGraphInfo(
            lines: List<String>,
            warnings: MutableList<String>,
        ): Map<String, String> {
            val graphInfo = mutableMapOf<String, String>()
            for (line in lines) {
                val splitLine = line.split("=").map { it.trim() }
                graphInfo[splitLine[0]] = splitLine[1]
            }
            checkOnWarnings(graphInfo, warnings)
            return graphInfo
        }

        private fun checkOnWarnings(
            graphInfo: Map<String, String>,
            warnings: MutableList<String>,
        ) {
            optionalProperties.forEach { property ->
                if (graphInfo[property] == null) warnings.add("Optional field `$property` was missed")
            }
        }

        private fun parseGraph(lines: List<String>): List<Result<Command>> {
            val correctCommandResults = mutableListOf<Result<Command>>()
            for (line in lines) {
                val commandResult = Command.create(line)
                if (commandResult is Result.Success) correctCommandResults.add(commandResult)
            }
            return correctCommandResults
        }
    }
}
