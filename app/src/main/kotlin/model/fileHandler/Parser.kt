package model.fileHandler

import model.commands.classes.Command
import model.ir.GraphIR
import model.result.Result
import java.io.File

class Parser {
    companion object {
        private val supportedExtensions = listOf("graph", "json")
        private val optionalProperties = listOf("name", "author")
        private val requiredBooleanProperties = listOf("isDirected", "isWeighted")

        fun parse(file: File): GraphIR {
            val fileExtension = file.extension
            if (fileExtension !in supportedExtensions) {
                throw IllegalArgumentException("File extension must be in list: $supportedExtensions")
            }
            var fileIR: File = file
            when (fileExtension) {
                "json" -> fileIR = Converter.convertJSONToIR(file)
            }

            val lines: List<String>
            try {
                lines = fileIR.readLines().map { it.trim() }.filter { it.isNotEmpty() }
            } catch (ex: Exception) {
                throw IllegalArgumentException("Error reading the file")
            }
            val infoMarkerIndex = lines.indexOf("Info:")
            val commandsMarkerIndex = lines.indexOf("Graph:")
            if (infoMarkerIndex == -1 || commandsMarkerIndex == -1 || infoMarkerIndex >= commandsMarkerIndex ) {
                throw IllegalArgumentException("Graph file is incorrect format")
            }
            val infoLines: List<String> = lines.subList(infoMarkerIndex + 1, commandsMarkerIndex)
            val commandLines: List<String> = lines.subList(commandsMarkerIndex + 1, lines.size)
            val errors = mutableListOf<String>()
            val graphInfo: Map<String, String> = parseGraphInfo(infoLines, errors)
            val commands: List<Result<Command>> = parseGraph(commandLines, errors)

            return GraphIR(
                name = graphInfo["name"] ?: "None",
                author = graphInfo["author"] ?: "None",
                isDirected = graphInfo["isDirected"]?.toBoolean() ?: false,
                isWeighted = graphInfo["isWeighted"]?.toBoolean() ?: false,
                errors = errors,
                commands = commands,
            )
        }

        private fun parseGraphInfo(lines: List<String>, errors: MutableList<String>): Map<String, String> {
            val graphInfo = mutableMapOf<String, String>()
            for (line in lines) {
                val splitLine = line.split("=").map { it.trim() }
                if (splitLine.size != 2) {
                    throw IllegalArgumentException("Incorrect graph info: $line")
                }
                if (splitLine[0] in listOf("isDirected", "isWeighted") && splitLine[1].lowercase() !in listOf("true", "false")) {
                    errors.add("Field ${splitLine[0]} must be `true` or `false` only")
                }
                graphInfo[splitLine[0]] = splitLine[1]
            }
            validateGraphInfo(graphInfo, errors)
            return graphInfo
        }

        private fun validateGraphInfo(graphInfo: Map<String, String>, errors: MutableList<String>) {
            optionalProperties.forEach { property ->
                checkOptionalProperty(property, graphInfo, errors)
            }
            requiredBooleanProperties.forEach { property ->
                checkRequiredBooleanProperty(property, graphInfo, errors)
            }
        }

        private fun checkOptionalProperty(property: String, graphInfo: Map<String, String>, errors: MutableList<String>) {
            if (graphInfo[property] == null) {
                errors.add("Optional field `$property` was missed")
                return
            }
            if ((graphInfo[property]?: "").isEmpty()) {
                errors.add("Value in optional field `$property` was missed")
                return
            }
        }

        private fun checkRequiredBooleanProperty(property: String, graphInfo: Map<String, String>, errors: MutableList<String>) {
            if (graphInfo[property] == null) {
                errors.add("Required boolean field `$property` was missed")
                return
            }
            if ((graphInfo[property]?: "").isEmpty()) {
                errors.add("Value in required boolean field `$property` was missed")
                return
            }
            if ((graphInfo[property]?: "").lowercase() !in listOf("true", "false")) {
                errors.add("Value in required boolean field `$property` incorrect")
                return
            }
        }

        private fun parseGraph(lines: List<String>, errors: MutableList<String>): List<Result<Command>> {
            val correctCommandResults = mutableListOf<Result<Command>>()
            for (line in lines) {
                val commandResult = Command.create(line)
                when (commandResult) {
                    is Result.Success -> correctCommandResults.add(commandResult)
                    is Result.Error -> errors.add("Error in line: $line. ${commandResult.error.type}.${commandResult.error.description}")
                }
            }
            return correctCommandResults
        }
    }
}
