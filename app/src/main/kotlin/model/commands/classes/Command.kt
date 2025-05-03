package model.commands.classes

import model.commands.enums.CommandEntities
import model.commands.enums.CommandTypes

class Command(
    command: String,
) {
    private val elements: List<String> = command.trim().split("\\s+".toRegex())
    private val keysForAddVertex = listOf("id", "label")
    private val keysForAddEdge = listOf("from", "to", "weight")
    private val keyForRmVertex = listOf("id")
    private val keysForRmEdge = listOf("from", "to")
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
        } else if (type == CommandTypes.RM) {
            getParametersForRmType(isCommandWithExplicitParameters, paramElements, params)
        }
        return params
    }

    private fun getParametersForRmType(
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
                if (key in keyForRmVertex || key in keysForRmEdge) {
                    params[key] = subitems[1]
                } else {
                    throw IllegalArgumentException("Invalid parameter $key")
                }
            }
        } else {
            if (entity == CommandEntities.VERTEX) {
                if (paramElements.size == 1) {
                    params["id"] = paramElements[0]
                } else {
                    throw IllegalArgumentException(
                        "Invalid parameter format. When removing a vertex, you must specify 1 required parameter: id",
                    )
                }
            } else if (entity == CommandEntities.EDGE) {
                when (paramElements.size) {
                    1 -> {
                        params["id"] = paramElements[0]
                    }
                    2 -> {
                        params["from"] = paramElements[0]
                        params["to"] = paramElements[1]
                    }
                    else ->
                        IllegalArgumentException(
                            "Invalid parameter format. When removing an edge, you must specify 1 or 2 required parameters: id or from and to",
                        )
                }
            }
        }
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
                checkRequiredParametersForAddVertex()
                checkOneIntParameter("id", "Vertex")
            } else if (entity == CommandEntities.EDGE) {
                checkRequiredParametersForAddEdge()
                checkTwoIntParameters("from", "to", "Edge")
                if (parameters.containsKey("weight")) {
                    checkOneIntParameter("weight", "Edge")
                }
            }
        } else if (type == CommandTypes.RM) {
            if (entity == CommandEntities.VERTEX) {
                checkRequiredParameterForRmVertex()
                checkOneIntParameter("id", "Vertex")
            } else if (entity == CommandEntities.EDGE) {
                checkRequiredParametersForRmEdge()
                if (parameters.containsKey("id")) {
                    checkOneIntParameter("id", "Edge")
                } else if (parameters.containsKey("from") && parameters.containsKey("to")) {
                    checkTwoIntParameters("from", "to", "Edge")
                } else {
                    throw IllegalArgumentException("Remove edge command must specify 'from' and 'to' or 'id'")
                }
            }

        } else if (type in allTypesWithoutParameters) {
            if (parameters.isNotEmpty()) {
                throw IllegalArgumentException("This command should have no parameters")
            }
        }
    }

    private fun checkRequiredParametersForAddVertex() {
        if (!parameters.containsKey("id") || !parameters.containsKey("label")) {
            throw IllegalArgumentException("Add vertex command must specify 'id' and 'label'")
        }
    }

    private fun checkRequiredParametersForAddEdge() {
        if (!parameters.containsKey("from") || !parameters.containsKey("to")) {
            throw IllegalArgumentException("Add edge command must specify 'from' and 'to'")
        }
    }

    private fun checkRequiredParameterForRmVertex() {
        if (!parameters.containsKey("id")) {
            throw IllegalArgumentException("Remove vertex command must specify 'id'")
        }
    }

    private fun checkRequiredParametersForRmEdge() {
        if (!((parameters.containsKey("id")) || (parameters.containsKey("from") && parameters.containsKey("to")))) {
            throw IllegalArgumentException("Remove edge command must specify 'from' and 'to' or 'id'")
        }
    }

    private fun checkOneIntParameter(param: String, entity: String) {
        val entity = entity.replaceFirstChar { it.uppercase() }
        try {
            parameters[param]?.toInt() ?: throw IllegalArgumentException("$entity `$param` cannot be null")
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("$entity `$param` must be Int type")
        }
    }

    private fun checkTwoIntParameters(firstParam: String, secondParam: String, entity: String) {
        val entity = entity.replaceFirstChar { it.uppercase() }
        try {
            parameters[firstParam]?.toInt() ?: throw IllegalArgumentException("$entity `$firstParam` cannot be null")
            parameters[secondParam]?.toInt() ?: throw IllegalArgumentException("$entity `$secondParam` cannot be null")
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("$entity `$firstParam` and `$secondParam` must be Int type")
        }
    }
}
