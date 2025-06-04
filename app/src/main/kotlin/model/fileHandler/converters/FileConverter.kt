package model.fileHandler.converters

import model.fileHandler.ConvertModes
import model.fileHandler.FileExtensions
import model.result.Result
import java.io.File

abstract class FileConverter {
    abstract fun convert(
        file: File,
        convertMode: ConvertModes,
        graphId: Long?,
    ): Result<File>

    companion object {
        fun createConverter(
            from: FileExtensions,
            to: FileExtensions,
        ): FileConverter =
            when {
                from == FileExtensions.GRAPH && to == FileExtensions.JSON -> IRToJSONConverter()
                from == FileExtensions.GRAPH && to == FileExtensions.SQL -> IRToSQLConverter()
                from == FileExtensions.JSON && to == FileExtensions.GRAPH -> JSONToIRConverter()
                from == FileExtensions.SQL && to == FileExtensions.GRAPH -> SQLToIRConverter()
                from == to -> SameFormatConverter()
                else -> throw IllegalArgumentException("Unknown file extension")
            }
    }
}
