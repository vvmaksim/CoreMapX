package model.commands.classes

import model.commands.enums.CommandEntities
import model.commands.enums.CommandTypes

class Command private constructor(
    val type: CommandTypes,
    val entity: CommandEntities,
    val parameters: Map<String, String>
) {
    companion object {
        private val keysForAddVertex = listOf("id", "label")
        private val keysForAddEdge = listOf("from", "to", "weight")
        private val keyForRmVertex = listOf("id")
        private val keysForRmEdge = listOf("from", "to")
        private val allTypesWithoutParameters = listOf(CommandTypes.HELP, CommandTypes.CLEAR)

        fun create(command: String): Result<Command> {
            if (command.isEmpty()) {
                return Result.Error(CommandError.EmptyCommand())
            }
            val elements: List<String> = command.trim().split("\\s+".toRegex())
            val commandType = elements.getOrNull(0) ?: ""
            val commandEntities = elements.getOrNull(1) ?: ""
            val type = getType(elements) ?: return Result.Error(CommandError.UnknownType(commandType))
            val entity = getEntity(elements) ?: return Result.Error(CommandError.UnknownEntity(commandEntities))
            val parameters = getParameters(elements, type, entity)
            return validateParameters(type, entity, parameters)
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

        private fun getParameters(elements: List<String>, type: CommandTypes, entity: CommandEntities): Map<String, String> {
            if (type in allTypesWithoutParameters) return emptyMap()
            if (elements.size <= 2) return emptyMap()

            val params = mutableMapOf<String, String>()
            val paramElements = elements.drop(2)
            val isCommandWithExplicitParameters = paramElements.any { it.contains(":") }

            if (type == CommandTypes.ADD) {
                getParametersForAddType(isCommandWithExplicitParameters, paramElements, params, entity)
            } else if (type == CommandTypes.RM) {
                getParametersForRmType(isCommandWithExplicitParameters, paramElements, params, entity)
            }
            return params
        }

        private fun getParametersForRmType(
            isCommandWithExplicitParameters: Boolean,
            paramElements: List<String>,
            params: MutableMap<String, String>,
            entity: CommandEntities,
        ) {
            if (isCommandWithExplicitParameters) {
                paramElements.forEach { param ->
                    val subitems = param.split(":")
                    if (subitems.size != 2) {
                        return
                    }
                    val key = subitems[0].lowercase()
                    if (key in keyForRmVertex || key in keysForRmEdge) {
                        params[key] = subitems[1]
                    }
                }
            } else {
                if (entity == CommandEntities.VERTEX) {
                    if (paramElements.size == 1) {
                        params["id"] = paramElements[0]
                    }
                } else if (entity == CommandEntities.EDGE) {
                    when (paramElements.size) {
                        1 -> params["id"] = paramElements[0]
                        2 -> {
                            params["from"] = paramElements[0]
                            params["to"] = paramElements[1]
                        }
                    }
                }
            }
        }

        private fun getParametersForAddType(
            isCommandWithExplicitParameters: Boolean,
            paramElements: List<String>,
            params: MutableMap<String, String>,
            entity: CommandEntities,
        ) {
            if (isCommandWithExplicitParameters) {
                paramElements.forEach { param ->
                    val subitems = param.split(":")
                    if (subitems.size != 2) {
                        return
                    }
                    val key = subitems[0].lowercase()
                    if (key in keysForAddVertex || key in keysForAddEdge) {
                        params[key] = subitems[1]
                    }
                }
            } else {
                if (entity == CommandEntities.VERTEX) {
                    if (paramElements.size == 2) {
                        params["id"] = paramElements[0]
                        params["label"] = paramElements[1]
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
                    }
                }
            }
        }

        private fun validateParameters(type: CommandTypes, entity: CommandEntities, parameters: Map<String, String>): Result<Command> {
            if (type == CommandTypes.ADD) {
                if (entity == CommandEntities.VERTEX) {
                    if (!parameters.containsKey("id") || !parameters.containsKey("label")) {
                        return Result.Error(CommandError.MissingParameters("Add vertex command must specify 'id' and 'label'"))
                    }
                    parameters["id"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("id", "Int"))
                } else if (entity == CommandEntities.EDGE) {
                    if (!parameters.containsKey("from") || !parameters.containsKey("to")) {
                        return Result.Error(CommandError.MissingParameters("Add edge command must specify 'from' and 'to'"))
                    }
                    parameters["from"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("from", "Int"))
                    parameters["to"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("to", "Int"))
                    if (parameters.containsKey("weight")) {
                        parameters["weight"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("weight", "Int"))
                    }
                }
            } else if (type == CommandTypes.RM) {
                if (entity == CommandEntities.VERTEX) {
                    if (!parameters.containsKey("id")) {
                        return Result.Error(CommandError.MissingParameters("Remove vertex command must specify 'id'"))
                    }
                    parameters["id"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("id", "Int"))
                } else if (entity == CommandEntities.EDGE) {
                    val allTogether = parameters.containsKey("id") && parameters.containsKey("from") && parameters.containsKey("to")
                    if (!(parameters.containsKey("id") || (parameters.containsKey("from") && parameters.containsKey("to"))) && !allTogether) {
                        return Result.Error(CommandError.MissingParameters("Remove edge command must specify 'from' and 'to' or 'id'"))
                    }
                    if (parameters.containsKey("id")) {
                        parameters["id"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("id", "Int"))
                    } else if (parameters.containsKey("from") && parameters.containsKey("to")) {
                        parameters["from"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("from", "Int"))
                        parameters["to"]?.toIntOrNull() ?: return Result.Error(CommandError.InvalidParameterType("to", "Int"))
                    }
                }
            } else if (type in allTypesWithoutParameters) {
                if (parameters.isNotEmpty()) {
                    return Result.Error(CommandError.InvalidParameterFormat("This command should have no parameters"))
                }
            }
            return Result.Success(Command(type, entity, parameters))
        }
    }
}
