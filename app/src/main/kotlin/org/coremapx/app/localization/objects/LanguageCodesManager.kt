package org.coremapx.app.localization.objects

object LanguageCodesManager {
    val FALLBACK_LANGUAGE_CODE = LanguageCodes.EN

    fun getCodeAsString(
        code: LanguageCodes,
        makeLower: Boolean = true,
    ): String {
        var result =
            when (code) {
                LanguageCodes.EN -> "EN"
                LanguageCodes.RU -> "RU"
                LanguageCodes.CUSTOM -> "CUSTOM"
            }
        if (makeLower) result = result.lowercase()
        return result
    }

    fun getCodeAsStringByFullName(fullLanguageName: String): String? =
        when (fullLanguageName) {
            getFullLanguageName(LanguageCodes.EN) -> getCodeAsString(LanguageCodes.EN)
            getFullLanguageName(LanguageCodes.RU) -> getCodeAsString(LanguageCodes.RU)
            getFullLanguageName(LanguageCodes.CUSTOM) -> getCodeAsString(LanguageCodes.CUSTOM)
            else -> null
        }

    fun getCodeAsStringByFullNameWithFallback(fullLanguageName: String): String =
        getCodeAsStringByFullName(fullLanguageName) ?: getFullLanguageName(FALLBACK_LANGUAGE_CODE)

    fun getFullLanguageName(code: LanguageCodes): String =
        when (code) {
            LanguageCodes.EN -> "English"
            LanguageCodes.RU -> "Русский"
            LanguageCodes.CUSTOM -> "Custom"
        }

    fun getFullLanguageNameByStringCode(codeAsString: String): String? {
        return when (codeAsString) {
            getCodeAsString(LanguageCodes.EN) -> getFullLanguageName(LanguageCodes.EN)
            getCodeAsString(LanguageCodes.RU) -> getFullLanguageName(LanguageCodes.RU)
            getCodeAsString(LanguageCodes.CUSTOM) -> getFullLanguageName(LanguageCodes.CUSTOM)
            else -> null
        }
    }

    fun getFullLanguageNameByStringCodeWithFallback(codeAsString: String): String =
        getFullLanguageNameByStringCode(codeAsString) ?: getFullLanguageName(LanguageCodes.EN)

    fun getAllFullLanguagesNames(): List<String> {
        val result = mutableListOf<String>()
        result.add(getFullLanguageName(LanguageCodes.EN))
        result.add(getFullLanguageName(LanguageCodes.RU))
        result.add(getFullLanguageName(LanguageCodes.CUSTOM))
        return result
    }
}
