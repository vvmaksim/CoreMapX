package org.coremapx.app.localization.objects

import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.TemplateKeys.DIRECTORY_PATH
import org.coremapx.app.localization.objects.TemplateKeys.ERROR_DESCRIPTION
import org.coremapx.app.localization.objects.TemplateKeys.ERROR_TYPE
import org.coremapx.app.localization.objects.TemplateKeys.FILE_FORMAT
import org.coremapx.app.localization.objects.TemplateKeys.FILE_NAME
import org.coremapx.app.localization.objects.TemplateKeys.NEXT_LINE
import org.coremapx.app.localization.objects.TemplateKeys.NUMBER

object LocalizationFormatter {
    fun getErrorMessage(
        startString: String,
        errorType: String,
        errorDescription: String?,
    ): String =
        startString
            .replace(ERROR_TYPE, errorType)
            .replace(ERROR_DESCRIPTION, errorDescription ?: LocalizationManager.states.ui.errorNoDescriptionMessage.value)

    fun getSaveGraphSuccessMessage(
        startString: String,
        fileName: String,
        directoryPath: String,
        fileFormat: String,
    ): String =
        startString
            .replace(FILE_NAME, fileName)
            .replace(DIRECTORY_PATH, directoryPath)
            .replace(FILE_FORMAT, fileFormat)

    fun getStringWithLineBreak(startString: String): String =
        startString
            .replace(NEXT_LINE, "\n")

    fun getStringWithOneNumber(
        startString: String,
        number: Long,
    ): String =
        startString
            .replace(NUMBER, number.toString())
}
