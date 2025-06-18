package org.coremapx.app.userDirectory

import androidx.compose.ui.graphics.Color
import extensions.toColorOrNull
import model.result.ConfigErrors
import model.result.FileErrors
import model.result.Result
import model.result.showConfigErrorDialog
import mu.KotlinLogging
import org.coremapx.app.theme.DefaultThemes
import org.coremapx.app.theme.ThemeConfig
import java.io.File
import java.util.Properties

private val logger = KotlinLogging.logger {}

class ConfigRepository {
    private val mainConfigPath = "${System.getProperty("user.home")}/.coremapx/config/Config.gcfg"
    private val defaultConfigPath = "app/src/main/resources/Configs/DefaultConfig.gcfg"
    private val privateConfigPath = "app/src/main/resources/Configs/PrivateConfig.gcfg"

    private val userConfig: MutableMap<String, String> = mutableMapOf()
    private val privateConfig: MutableMap<String, String> = mutableMapOf()
    private val defaultConfig: MutableMap<String, String> = mutableMapOf()

    var states: ConfigStates
        private set

    init {
        loadUserConfig()
        loadPrivateConfig()
        validateUserConfig()
        states = ConfigStates(this)
        updateTheme()
    }

    fun getStringValue(key: String): String =
        userConfig[key] ?: privateConfig[key] ?: throw IllegalArgumentException("Unknown key:$key in config")

    fun getBooleanValue(key: String): Boolean =
        getStringValue(key).toBooleanStrictOrNull() ?: throw IllegalArgumentException("Invalid key:$key. Value must be `true` or `false`")

    fun getIntValue(key: String): Int =
        getStringValue(key).toIntOrNull() ?: throw IllegalArgumentException("Invalid key:$key. Value must be Int type")

    fun getFloatValue(key: String): Float =
        getStringValue(key).toFloatOrNull() ?: throw IllegalArgumentException("Invalid key:$key. Value must be Float type")

    fun getColor(key: String): Color = getStringValue(key).toColorOrNull() ?: throw IllegalArgumentException(getColorErrorMessage(key))

    fun setValue(
        key: String,
        value: String,
    ): Result<Boolean> {
        val validateResult = ConfigValidator.validate(key, value)
        if (validateResult is Result.Error) return validateResult
        val updateConfigFileResult = updateConfigFile(key, value)
        if (updateConfigFileResult is Result.Error) return updateConfigFileResult
        userConfig[key] = value
        states.updateValue(key, value)
        logger.info { "Updated config. For key: $key new value: $value" }
        return Result.Success(true)
    }

    fun updateTheme() {
        val theme = states.theme.value
        if (theme != "custom") {
            when (theme) {
                "light" -> setTheme(DefaultThemes.light)
                "dark" -> setTheme(DefaultThemes.dark)
                else -> showConfigErrorDialog("Unknown theme: $theme")
            }
        }
    }

    fun setThemeOnCustom() = setValue("theme", "custom")

    private fun setTheme(themeConfig: ThemeConfig) {
        with(themeConfig) {
            setValue("primary", primary)
            setValue("primaryVariant", primaryVariant)
            setValue("secondary", secondary)
            setValue("secondaryVariant", secondaryVariant)
            setValue("background", background)
            setValue("surface", surface)
            setValue("error", error)
            setValue("onPrimary", onPrimary)
            setValue("onSecondary", onSecondary)
            setValue("onBackground", onBackground)
            setValue("onSurface", onSurface)
            setValue("onError", onError)
            setValue("borderColor", borderColor)
            setValue("warningColor", warningColor)
            setValue("vertexMainColor", vertexMainColor)
            setValue("hoveredBorderColor", hoveredBorderColor)
            setValue("edgeMainColor", edgeMainColor)
            setValue("canvasBackgroundColor", canvasBackgroundColor)
            setValue("commandLineBackgroundColor", commandLineBackgroundColor)
            setValue("fileDialogTheme", if (isLight) "light" else "dark")
        }
    }

    private fun updateConfigFile(
        key: String,
        value: String,
    ): Result<Boolean> {
        val configFile = File(mainConfigPath)
        if (!configFile.exists()) {
            val message = "There is nothing to update, the configuration file has not been found"
            logger.error { message }
            return Result.Error(FileErrors.ErrorReadingFile(message))
        }
        var keyFound = false
        val updatedLines =
            configFile.readLines().map { line ->
                if (line.trim().startsWith("$key=")) {
                    keyFound = true
                    "$key=$value"
                } else {
                    line
                }
            }
        if (!keyFound) {
            logger.warn { "Key '$key' not found in config file" }
            return Result.Error(ConfigErrors.UnknownProperty(key))
        }
        configFile.bufferedWriter().use { writer ->
            updatedLines.forEachIndexed { index, line ->
                writer.write(line)
                if (index < updatedLines.size - 1) {
                    writer.newLine()
                }
            }
        }
        return Result.Success(true)
    }

    private fun loadUserConfig() {
        loadConfig(mainConfigPath, userConfig)
    }

    private fun loadPrivateConfig() {
        loadConfig(privateConfigPath, privateConfig)
    }

    private fun loadDefaultConfig() {
        loadConfig(defaultConfigPath, defaultConfig)
    }

    private fun loadConfig(
        path: String,
        config: MutableMap<String, String>,
    ) {
        val configFile = File(path)
        val properties = Properties()
        if (configFile.exists()) {
            configFile.inputStream().use { inputStream ->
                properties.load(inputStream)
            }
        }
        properties.forEach { (key, value) ->
            config[key.toString()] = value.toString()
        }
    }

    private fun comparisonWithDefaultConfig() {
        loadDefaultConfig()
        val missingParameters = mutableListOf<String>()
        defaultConfig.forEach { param ->
            if (!userConfig.containsKey(param.key)) missingParameters.add(param.key)
        }
        if (missingParameters.isNotEmpty()) throw IllegalArgumentException("Missing parameters in config: $missingParameters")
    }

    private fun getColorErrorMessage(propertyName: String): String = "$propertyName must be color in hex format. For example `...=#FFFFFF`"

    private fun validateUserConfig() {
        try {
            comparisonWithDefaultConfig()
            validateUserConfigValues()
            logger.info { "Config has been loaded successfully" }
        } catch (ex: IllegalArgumentException) {
            logger.error { "Config validation failed" }
            showConfigErrorDialog(ex.message ?: "Config error")
        }
    }

    private fun validateUserConfigValues() {
        userConfig.forEach { (key, value) ->
            val validateResult = ConfigValidator.validate(key, value)
            if (validateResult is Result.Error) {
                throw IllegalArgumentException("ERROR: ${validateResult.error.type}.${validateResult.error.description}")
            }
        }
    }
}
