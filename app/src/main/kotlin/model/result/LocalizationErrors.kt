package model.result

sealed class LocalizationErrors(
    override val type: String,
    override val description: String? = null,
) : Error {
    data class UnknownLanguage(
        val language: String,
    ) : LocalizationErrors(
        type = "UnknownLanguage",
        description = "Unknown language: $language",
    )
    data class LanguageFileNotExist(
        val languagePath: String,
    ) : LocalizationErrors(
        type = "LanguageFileNotExist",
        description = "Language file on path $languagePath is not exist",
    )
    data class ErrorLanguageLines(
        val keys: List<String>,
    ) : LocalizationErrors(
        type = "ErrorLanguageLines",
        description = "There were problems with localization strings for the keys: ${keys.joinToString(", ")}",
    )
}
