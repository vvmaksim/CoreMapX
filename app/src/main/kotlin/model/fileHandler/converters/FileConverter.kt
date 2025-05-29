package model.fileHandler.converters

import model.fileHandler.ConvertModes
import model.fileHandler.FileExtensions
import model.result.Result
import java.io.File

abstract class FileConverter {
    abstract fun convert(
        file: File,
        convertMode: ConvertModes,
    ): Result<File>

    companion object {
        fun createConverter(
            from: FileExtensions,
            to: FileExtensions,
        ): FileConverter =
            when {
                from == FileExtensions.GRAPH && to == FileExtensions.JSON -> IRToJSONConverter()
                from == FileExtensions.JSON && to == FileExtensions.GRAPH -> JSONToIRConverter()
                from == to -> SameFormatConverter()
                else -> throw IllegalArgumentException("Unknown file extension")
            }
    }
}
