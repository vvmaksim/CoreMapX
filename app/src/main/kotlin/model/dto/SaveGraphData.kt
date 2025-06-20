package model.dto

import model.fileHandler.FileExtensions

data class SaveGraphData(
    val fileName: String,
    val directoryPath: String,
    val fileFormat: FileExtensions,
)
