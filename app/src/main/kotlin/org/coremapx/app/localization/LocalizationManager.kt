package org.coremapx.app.localization

import model.result.LocalizationErrors
import model.result.Result
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.app.AppLogger.logError
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.localization.objects.LanguageCodes
import org.coremapx.app.localization.objects.LanguageCodesManager.getCodeAsString
import org.coremapx.app.localization.objects.LocalizationKeys
import org.coremapx.app.localization.states.LocalizationState
import org.coremapx.app.localization.states.LocalizationStates
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
        logDebug("Launched updateLanguage() from LocalizationManager with language:$language")
        val loadLanguageResult =
            when (language) {
                getCodeAsString(LanguageCodes.EN) -> loadLanguage(PrivateConfig.AppResources.EN_LANGUAGE_PATH)
                getCodeAsString(LanguageCodes.RU) -> loadLanguage(PrivateConfig.AppResources.RU_LANGUAGE_PATH)
                getCodeAsString(LanguageCodes.CUSTOM) -> loadLanguage(PrivateConfig.AppResources.CUSTOM_LANGUAGE_PATH)
                else -> Result.Error(LocalizationErrors.UnknownLanguage(language))
            }
        if (loadLanguageResult is Result.Error) return loadLanguageResult
        updateAllStates()
        if (failedKeys.isNotEmpty()) return Result.Error(LocalizationErrors.ErrorLanguageLines(failedKeys))
        return Result.Success(true)
    }

    private fun updateAllStates() {
        logDebug("Launched updateAllStates() from LocalizationManager")
        updateStateGroup(states.ui, LocalizationKeys.ui)
        updateStateGroup(states.dialogs, LocalizationKeys.dialogs)
        updateStateGroup(states.descriptions, LocalizationKeys.descriptions)
        updateStateGroup(states.anyIconDescriptionsStates, LocalizationKeys.anyIconDescriptions)
        updateStateGroup(states.anyTextStates, LocalizationKeys.anyText)
    }

    private fun getFallBackText(key: String): String {
        logDebug("Launched getFallBackText() from LocalizationManager with key:$key")
        return "Localization error for $key"
    }

    private fun updateStateGroup(
        stateGroup: LocalizationState,
        keysGroup: Any,
    ) {
        logDebug(
            "Launched updateStateGroup() from LocalizationManager with stateGroup:${stateGroup::class.simpleName}, " +
                "keysGroup:${keysGroup::class.simpleName}",
        )
        keysGroup::class
            .members
            .filter { it is kotlin.reflect.KProperty1<*, *> && it.isConst }
            .forEach { property ->
                try {
                    val key = property.call() as String
                    val value = localizationMap[key] ?: getFallBackText(key)
                    stateGroup.updateValue(key, value)
                } catch (ex: Exception) {
                    logError("updateStateGroup ERROR for $property ${property.call(keysGroup) as String}. Error: $ex")
                    failedKeys.add(property.call(keysGroup) as String)
                }
            }
    }

    private fun loadLanguage(languagePath: String): Result<Boolean> {
        logDebug("Launched loadLanguage() from LocalizationManager with languagePath:$languagePath")
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
