package model.fileHandler.serializableDataClasses
import kotlinx.serialization.Serializable

@Serializable
data class Vertex(
    val id: Long,
    val label: String,
)
