package model.command.concrete

import model.command.enums.CommandEntities
import model.command.enums.CommandTypes
import model.result.CommandErrors
import model.result.Result

class Command private constructor(
    val type: CommandTypes,
    val entity: CommandEntities,
    val parameters: Map<String, String>,
) {
    companion object {
        private val keysForAddVertex = listOf("id", "label")
        private val keysForAddEdge = listOf("from", "to", "weight")
        private val keyForRmVertex = listOf("id")
        private val keyForSetStrategy = listOf("strategy")
        private val keysForRmEdge = listOf("from", "to")
        private val allTypesWithoutParameters = listOf(CommandTypes.HELP, CommandTypes.CLEAR)

        fun create(command: String): Result<Command> {
            if (command.isEmpty()) {
                return Result.Error(CommandErrors.EmptyCommand())
            }
            val elements: List<String> = command.trim().split("\\s+".toRegex())
            val commandType = elements.getOrNull(0) ?: ""
            val commandEntities = elements.getOrNull(1) ?: ""
            val type = getType(elements) ?: return Result.Error(CommandErrors.UnknownType(commandType))
            val entity = getEntity(elements) ?: return Result.Error(CommandErrors.UnknownEntity(commandEntities))
            val parameters = getParameters(elements, type, entity)
            return validateParameters(type, entity, parameters)
        }

        private fun getType(elements: List<String>): CommandTypes? =
            when (elements[0].lowercase()) {
                "add" -> CommandTypes.ADD
                "rm", "remove" -> CommandTypes.RM
                "graph_clear", "clear" -> CommandTypes.CLEAR
                "help" -> CommandTypes.HELP
                "set" -> CommandTypes.SET
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
                "strategy" -> CommandEntities.LAYOUT_STRATEGY
                else -> null
            }
        }

        private fun getParameters(
            elements: List<String>,
            type: CommandTypes,
            entity: CommandEntities,
        ): Map<String, String> {
            if (type in allTypesWithoutParameters) return emptyMap()
            if (elements.size <= 2) return emptyMap()

            val params = mutableMapOf<String, String>()
            val paramElements = elements.drop(2)
            val isCommandWithExplicitParameters = paramElements.any { it.contains(":") }

            when (type) {
                CommandTypes.ADD -> {
                    getParametersForAddType(isCommandWithExplicitParameters, paramElements, params, entity)
                }
                CommandTypes.RM -> {
                    getParametersForRmType(isCommandWithExplicitParameters, paramElements, params, entity)
                }
                CommandTypes.SET -> {
                    getParametersForSetType(isCommandWithExplicitParameters, paramElements, params, entity)
                }
                // No parameters
                CommandTypes.CLEAR -> {}
                CommandTypes.HELP -> {}
            }
            return params
        }

        private fun getParametersForSetType(
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
                    if (key in keyForSetStrategy) {
                        params[key] = subitems[1]
                    }
                }
            } else {
                if (entity == CommandEntities.LAYOUT_STRATEGY) {
                    if (paramElements.size == 1) {
                        params["strategy"] = paramElements[0]
                    }
                }
            }
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

        private fun validateParameters(
            type: CommandTypes,
            entity: CommandEntities,
            parameters: Map<String, String>,
        ): Result<Command> {
            if (type == CommandTypes.ADD) {
                if (entity == CommandEntities.VERTEX) {
                    if (!parameters.containsKey("id") || !parameters.containsKey("label")) {
                        return Result.Error(CommandErrors.MissingParameters("Add vertex command must specify 'id' and 'label'"))
                    }
                    parameters["id"]?.toLongOrNull() ?: return Result.Error(CommandErrors.InvalidParameterType("id", "Long"))
                } else if (entity == CommandEntities.EDGE) {
                    if (!parameters.containsKey("from") || !parameters.containsKey("to")) {
                        return Result.Error(CommandErrors.MissingParameters("Add edge command must specify 'from' and 'to'"))
                    }
                    parameters["from"]?.toLongOrNull() ?: return Result.Error(CommandErrors.InvalidParameterType("from", "Long"))
                    parameters["to"]?.toLongOrNull() ?: return Result.Error(CommandErrors.InvalidParameterType("to", "Long"))
                    if (parameters.containsKey("weight")) {
                        parameters["weight"]?.toLongOrNull() ?: return Result.Error(CommandErrors.InvalidParameterType("weight", "Long"))
                    }
                }
            } else if (type == CommandTypes.RM) {
                if (entity == CommandEntities.VERTEX) {
                    if (!parameters.containsKey("id")) {
                        return Result.Error(CommandErrors.MissingParameters("Remove vertex command must specify 'id'"))
                    }
                    parameters["id"]?.toLongOrNull() ?: return Result.Error(CommandErrors.InvalidParameterType("id", "Long"))
                } else if (entity == CommandEntities.EDGE) {
                    val allTogether = parameters.containsKey("id") && parameters.containsKey("from") && parameters.containsKey("to")
                    if (!(parameters.containsKey("id") || (parameters.containsKey("from") && parameters.containsKey("to"))) &&
                        !allTogether
                    ) {
                        return Result.Error(CommandErrors.MissingParameters("Remove edge command must specify 'from' and 'to' or 'id'"))
                    }
                    if (parameters.containsKey("id")) {
                        parameters["id"]?.toLongOrNull() ?: return Result.Error(CommandErrors.InvalidParameterType("id", "Long"))
                    } else if (parameters.containsKey("from") && parameters.containsKey("to")) {
                        parameters["from"]?.toLongOrNull() ?: return Result.Error(CommandErrors.InvalidParameterType("from", "Long"))
                        parameters["to"]?.toLongOrNull() ?: return Result.Error(CommandErrors.InvalidParameterType("to", "Long"))
                    }
                }
            } else if (type == CommandTypes.SET) {
                if (entity == CommandEntities.LAYOUT_STRATEGY) {
                    if (!parameters.containsKey("strategy")) {
                        return Result.Error(CommandErrors.MissingParameters("Set layout strategy command must specify 'strategy'"))
                    }
                }
            } else if (type in allTypesWithoutParameters) {
                if (parameters.isNotEmpty()) {
                    return Result.Error(CommandErrors.InvalidParameterFormat("This command should have no parameters"))
                }
            }
            return Result.Success(Command(type, entity, parameters))
        }
    }
}
