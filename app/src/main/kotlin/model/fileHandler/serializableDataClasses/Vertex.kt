package model.fileHandler.serializableDataClasses
import kotlinx.serialization.Serializable

@Serializable
data class Vertex(
    val id: Int,
    val label: String,
)
