package model.fileHandler.converters

import model.fileHandler.ConvertModes
import model.result.Result
import java.io.File

class SameFormatConverter : FileConverter() {
    override fun convert(
        file: File,
        convertMode: ConvertModes,
    ): Result<File> = Result.Success(file)
}
