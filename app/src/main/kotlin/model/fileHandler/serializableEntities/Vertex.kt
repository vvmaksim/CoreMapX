package model.fileHandler.serializableEntities
import kotlinx.serialization.Serializable

@Serializable
data class Vertex(
    val id: Long,
    val label: String,
)
