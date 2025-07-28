import org.coremapx.app.localization.objects.LanguageCodes
import org.coremapx.app.localization.objects.LanguageCodesManager.FALLBACK_LANGUAGE_CODE
import org.coremapx.app.localization.objects.LanguageCodesManager.getAllFullLanguagesNames
import org.coremapx.app.localization.objects.LanguageCodesManager.getCodeAsString
import org.coremapx.app.localization.objects.LanguageCodesManager.getCodeAsStringByFullName
import org.coremapx.app.localization.objects.LanguageCodesManager.getCodeAsStringByFullNameWithFallback
import org.coremapx.app.localization.objects.LanguageCodesManager.getFullLanguageName
import org.coremapx.app.localization.objects.LanguageCodesManager.getFullLanguageNameByStringCode
import org.coremapx.app.localization.objects.LanguageCodesManager.getFullLanguageNameByStringCodeWithFallback
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LanguageCodesManagerTests {
    val enUpperCode: String = "EN"
    val enLowerCode: String = enUpperCode.lowercase()
    val enFullName: String = "English"
    val ruUpperCode: String = "RU"
    val ruLowerCode: String = ruUpperCode.lowercase()
    val ruFullName: String = "Русский"
    val customUpperCode: String = "CUSTOM"
    val customLowerCode: String = customUpperCode.lowercase()
    val customFullName: String = "Custom"

    val unknownName: String = "unknownName"

    val allFullLanguagesNames = listOf(enFullName, ruFullName, customFullName)

    @Test
    fun `get codes as strings`() {
        assertEquals(enUpperCode, getCodeAsString(code = LanguageCodes.EN, makeLower = false))
        assertEquals(enLowerCode, getCodeAsString(code = LanguageCodes.EN, makeLower = true))
        assertEquals(ruUpperCode, getCodeAsString(code = LanguageCodes.RU, makeLower = false))
        assertEquals(ruLowerCode, getCodeAsString(code = LanguageCodes.RU, makeLower = true))
        assertEquals(customUpperCode, getCodeAsString(code = LanguageCodes.CUSTOM, makeLower = false))
        assertEquals(customLowerCode, getCodeAsString(code = LanguageCodes.CUSTOM, makeLower = true))
    }

    @Test
    fun `get full language names`() {
        assertEquals(enFullName, getFullLanguageName(LanguageCodes.EN))
        assertEquals(ruFullName, getFullLanguageName(LanguageCodes.RU))
        assertEquals(customFullName, getFullLanguageName(LanguageCodes.CUSTOM))
    }

    @Test
    fun `get codes as string by full names`() {
        assertEquals(enLowerCode, getCodeAsStringByFullName(enFullName))
        assertEquals(ruLowerCode, getCodeAsStringByFullName(ruFullName))
        assertEquals(customLowerCode, getCodeAsStringByFullName(customFullName))
    }

    @Test
    fun `get codes as string by full names with fallback`() {
        assertEquals(enLowerCode, getCodeAsStringByFullNameWithFallback(unknownName))
        assertEquals(enLowerCode, getCodeAsStringByFullNameWithFallback(enFullName))
    }

    @Test
    fun `get full language names by string codes`() {
        assertEquals(enFullName, getFullLanguageNameByStringCode(enLowerCode))
        assertEquals(ruFullName, getFullLanguageNameByStringCode(ruLowerCode))
        assertEquals(customFullName, getFullLanguageNameByStringCode(customLowerCode))
    }

    @Test
    fun `get full language names by string codes with fallback`() {
        assertEquals(enFullName, getFullLanguageNameByStringCodeWithFallback(unknownName))
        assertEquals(enFullName, getFullLanguageNameByStringCodeWithFallback(enLowerCode))
    }

    @Test
    fun `get all full languages names`() {
        assertEquals(allFullLanguagesNames, getAllFullLanguagesNames())
    }

    @Test
    fun `check fallback language code`() {
        assertEquals(LanguageCodes.EN, FALLBACK_LANGUAGE_CODE)
    }
}
