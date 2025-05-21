package model.fileHandler.serializableDataClasses

import kotlinx.serialization.Serializable

@Serializable
data class GraphInfo(
    val name: String?,
    val author: String?,
    val isDirected: Boolean,
    val isWeighted: Boolean,
)
