package model.fileHandler.converter

import model.fileHandler.ConvertModes
import model.result.Result
import org.coremapx.app.AppLogger.logDebug
import java.io.File

class SameFormatConverter : FileConverter() {
    override fun convert(
        file: File,
        convertMode: ConvertModes,
        graphId: Long?,
    ): Result<File> {
        logDebug(
            "Launched convert() from SameFormatConverter with fileAbsolutePath:${file.absolutePath}, convertMode:${convertMode.name}, graphId:$graphId",
        )
        return Result.Success(file)
    }
}
