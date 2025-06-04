package model.fileHandler.serializableDataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Edge(
    val from: Long,
    val to: Long,
    val weight: Long? = null,
)
