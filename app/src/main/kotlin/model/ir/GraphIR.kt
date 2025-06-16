package model.ir

import model.commands.`class`.Command
import model.result.Result

data class GraphIR(
    val name: String = "None",
    val author: String = "None",
    val isDirected: Boolean = false,
    val isWeighted: Boolean = false,
    val warnings: List<String> = emptyList(),
    val commands: List<Result<Command>>,
)
