package model.fileHandler.serializableDataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Edge(
    val from: Int,
    val to: Int,
    val weight: Int? = null,
)
