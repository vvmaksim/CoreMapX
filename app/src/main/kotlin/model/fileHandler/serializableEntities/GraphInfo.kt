package model.fileHandler.serializableEntities

import kotlinx.serialization.Serializable

@Serializable
data class GraphInfo(
    val name: String? = null,
    val author: String? = null,
    val isDirected: Boolean,
    val isWeighted: Boolean,
)
