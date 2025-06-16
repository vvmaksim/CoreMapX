package model.fileHandler.serializableEntities

import kotlinx.serialization.Serializable

@Serializable
data class GraphData(
    val info: GraphInfo,
    val graph: Graph,
)
