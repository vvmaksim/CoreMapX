package model.commands.classes

import model.commands.enums.CommandEntities
import model.commands.enums.CommandTypes

class Command(
    command: String,
) {
    private val elements: List<String> = command.trim().split("\\s+".toRegex())
    private val keysForAddVertex = listOf("id", "label")
    private val keysForAddEdge = listOf("from", "to", "weight")
    private val allTypesWithoutParameters = listOf(CommandTypes.HELP, CommandTypes.CLEAR)

    val type: CommandTypes
    val entity: CommandEntities
    val parameters: Map<String, String>

    init {
        if (elements.isEmpty()) throw IllegalArgumentException("Command cannot be empty")
        type = getType(elements) ?: throw IllegalArgumentException("Unknown command type")
        entity = getEntity(elements) ?: throw IllegalArgumentException("Unknown entity for command")
        parameters = getParameters(elements)
        validateParameters()
    }

    private fun getType(elements: List<String>): CommandTypes? =
        when (elements[0].lowercase()) {
            "add" -> CommandTypes.ADD
            "rm", "remove" -> CommandTypes.RM
            "graph_clear", "clear" -> CommandTypes.CLEAR
            "help" -> CommandTypes.HELP
            else -> null
        }

    private fun getEntity(elements: List<String>): CommandEntities? {
        when (elements[0].lowercase()) {
            "graph_clear" -> return CommandEntities.GRAPH
            "clear" -> return CommandEntities.COMMAND_OUTPUT
            "help" -> return CommandEntities.APP
        }
        if (elements.size <= 1) return null
        return when (elements[1].lowercase()) {
            "vertex" -> CommandEntities.VERTEX
            "edge" -> CommandEntities.EDGE
            else -> null
        }
    }

    private fun getParameters(elements: List<String>): Map<String, String> {
        if (type in allTypesWithoutParameters) return emptyMap()
        if (elements.size <= 2) return emptyMap()

        val params = mutableMapOf<String, String>()
        val paramElements = elements.drop(2)
        val isCommandWithExplicitParameters = paramElements.any { it.contains(":") }

        if (type == CommandTypes.ADD) {
            getParametersForAddType(isCommandWithExplicitParameters, paramElements, params)
        }
        return params
    }

    private fun getParametersForAddType(
        isCommandWithExplicitParameters: Boolean,
        paramElements: List<String>,
        params: MutableMap<String, String>,
    ) {
        if (isCommandWithExplicitParameters) {
            paramElements.forEach { param ->
                val subitems = param.split(":")
                if (subitems.size != 2) {
                    throw IllegalArgumentException("Invalid parameter format")
                }
                val key = subitems[0].lowercase()
                if (key in keysForAddVertex || key in keysForAddEdge) {
                    params[key] = subitems[1]
                } else {
                    throw IllegalArgumentException("Invalid parameter $key")
                }
            }
        } else {
            if (entity == CommandEntities.VERTEX) {
                if (paramElements.size == 2) {
                    params["id"] = paramElements[0]
                    params["label"] = paramElements[1]
                } else {
                    throw IllegalArgumentException(
                        "Invalid parameter format. When adding a vertex, you must specify 2 required parameters: id and label",
                    )
                }
            } else if (entity == CommandEntities.EDGE) {
                when (paramElements.size) {
                    2 -> {
                        params["from"] = paramElements[0]
                        params["to"] = paramElements[1]
                    }
                    3 -> {
                        params["from"] = paramElements[0]
                        params["to"] = paramElements[1]
                        params["weight"] = paramElements[2]
                    }
                    else ->
                        IllegalArgumentException(
                            "Invalid parameter format. When adding an edge, you must specify 2 or 3 required parameters: id and label or id, label and weight",
                        )
                }
            }
        }
    }

    private fun validateParameters() {
        if (type == CommandTypes.ADD) {
            if (entity == CommandEntities.VERTEX) {
                if (!parameters.containsKey("id") || !parameters.containsKey("label")) {
                    throw IllegalArgumentException("Add vertex command must specify 'id' and 'label'")
                }
                try {
                    parameters["id"]?.toInt() ?: throw IllegalArgumentException("Vertex id cannot be null")
                } catch (ex: NumberFormatException) {
                    throw IllegalArgumentException("Vertex id must be Int type")
                }
            } else if (entity == CommandEntities.EDGE) {
                if (!parameters.containsKey("from") || !parameters.containsKey("to")) {
                    throw IllegalArgumentException("Add edge command must specify 'from' and 'to'")
                }
                try {
                    parameters["from"]?.toInt() ?: throw IllegalArgumentException("Edge from cannot be null")
                    parameters["to"]?.toInt() ?: throw IllegalArgumentException("Edge to cannot be null")
                } catch (ex: NumberFormatException) {
                    throw IllegalArgumentException("Edge from and to must be Int type")
                }
                if (parameters.containsKey("weight")) {
                    try {
                        parameters["weight"]?.toInt() ?: throw IllegalArgumentException("Edge weight cannot be null")
                    } catch (ex: NumberFormatException) {
                        throw IllegalArgumentException("Edge weight must be Int type")
                    }
                }
            }
        } else if (type in allTypesWithoutParameters) {
            if (parameters.isNotEmpty()) {
                throw IllegalArgumentException("This command should have no parameters")
            }
        }
    }
}
