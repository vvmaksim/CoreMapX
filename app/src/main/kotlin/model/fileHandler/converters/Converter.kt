package model.fileHandler.converters

import model.fileHandler.ConvertModes
import model.fileHandler.FileExtensions
import model.fileHandler.Validator
import model.result.FileErrors
import model.result.Result
import java.io.File

class Converter {
    companion object {
        fun convert(
            file: File,
            to: FileExtensions,
            mode: ConvertModes,
            graphId: Long?,
        ): Result<File> {
            val validateResult = Validator.Companion.validate(file)
            if (validateResult is Result.Error) return validateResult

            val from =
                when (file.extension) {
                    "graph" -> FileExtensions.GRAPH
                    "json" -> FileExtensions.JSON
                    "db" -> FileExtensions.SQL
                    else -> {
                        return Result.Error(FileErrors.UnknownFileExtension())
                    }
                }
            try {
                val converter = FileConverter.createConverter(from, to)
                return converter.convert(file, mode, graphId)
            } catch (ex: IllegalArgumentException) {
                return Result.Error(FileErrors.UnknownFileExtension())
            }
        }
    }
}
