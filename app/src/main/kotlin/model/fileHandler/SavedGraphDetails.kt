package model.fileHandler

data class SavedGraphDetails(
    val fileName: String,
    val directoryPath: String,
    val fileFormat: FileExtensions,
)
