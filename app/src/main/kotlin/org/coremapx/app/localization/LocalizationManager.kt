package org.coremapx.app.localization

import model.result.LocalizationErrors
import model.result.Result
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.localization.states.LocalizationStates
import org.coremapx.app.localization.objects.LocalizationKeys
import org.coremapx.app.localization.states.LocalizationState
import java.io.File
import java.io.InputStreamReader
import java.util.Properties
import kotlin.collections.component1
import kotlin.collections.component2

object LocalizationManager {
    val states = LocalizationStates()

    private val localizationMap = mutableMapOf<String, String>()
    private val failedKeys = mutableListOf<String>()

    fun updateLanguage(language: String): Result<Boolean> {
        val loadLanguageResult =
            when (language) {
                "en" -> loadLanguage(PrivateConfig.AppResources.EN_LANGUAGE_PATH)
                "ru" -> loadLanguage(PrivateConfig.AppResources.RU_LANGUAGE_PATH)
                else -> Result.Error(LocalizationErrors.UnknownLanguage(language))
            }
        if (loadLanguageResult is Result.Error) return loadLanguageResult
        updateAllStates()
        if (failedKeys.isNotEmpty()) return Result.Error(LocalizationErrors.ErrorLanguageLines(failedKeys))
        return Result.Success(true)
    }

    private fun updateAllStates() {
        updateStateGroup(states.ui, LocalizationKeys.ui)
    }

    private fun getFallBackText(key: String): String = "Localization error for $key"

    private fun updateStateGroup(stateGroup: LocalizationState, keysGroup: Any) {
        keysGroup::class.members
            .filter { it is kotlin.reflect.KProperty1<*, *> && it.isConst }
            .forEach { property ->
                try {
                    val key = property.call() as String
                    val value = localizationMap[key] ?: getFallBackText(key)
                    stateGroup.updateValue(key, value)
                } catch (ex: Exception) {
                    failedKeys.add(property.call(keysGroup) as String)
                }
            }
    }

    private fun loadLanguage(languagePath: String): Result<Boolean> {
        val localizationFile = File(languagePath)
        val properties = Properties()
        if (localizationFile.exists()) {
            localizationFile.inputStream().use { inputStream ->
                properties.load(InputStreamReader(inputStream, "UTF-8"))
            }
        } else {
            return Result.Error(LocalizationErrors.LanguageFileNotExist(languagePath))
        }
        properties.forEach { (key, value) ->
            localizationMap[key.toString()] = value.toString()
        }
        return Result.Success(true)
    }
}
