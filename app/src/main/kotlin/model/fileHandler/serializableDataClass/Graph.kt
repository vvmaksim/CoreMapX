package model.fileHandler.serializableDataClass

import kotlinx.serialization.Serializable

@Serializable
data class Graph(
    val vertices: List<Vertex>,
    val edges: List<Edge>,
)
