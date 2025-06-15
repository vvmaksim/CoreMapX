package model.dto

import model.fileHandler.FileExtensions

data class SavedGraphDetails(
    val fileName: String,
    val directoryPath: String,
    val fileFormat: FileExtensions,
)
