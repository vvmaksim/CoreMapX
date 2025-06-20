package org.coremapx.app.userDirectory.config

import androidx.compose.ui.graphics.Color
import extensions.toColorOrNull
import model.result.ConfigErrors
import model.result.FileErrors
import model.result.Result
import model.result.showConfigErrorDialog
import mu.KotlinLogging
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.theme.DefaultThemes
import org.coremapx.app.theme.ThemeConfig
import org.coremapx.app.userDirectory.config.ConfigKeys.BACKGROUND
import org.coremapx.app.userDirectory.config.ConfigKeys.BORDER_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.COMMAND_LINE_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_MAIN_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.ERROR
import org.coremapx.app.userDirectory.config.ConfigKeys.HOVERED_BORDER_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_BACKGROUND
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_ERROR
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_PRIMARY
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_SECONDARY
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_SURFACE
import org.coremapx.app.userDirectory.config.ConfigKeys.PRIMARY
import org.coremapx.app.userDirectory.config.ConfigKeys.PRIMARY_VARIANT
import org.coremapx.app.userDirectory.config.ConfigKeys.SECONDARY
import org.coremapx.app.userDirectory.config.ConfigKeys.SECONDARY_VARIANT
import org.coremapx.app.userDirectory.config.ConfigKeys.SUCCESS_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.SURFACE
import org.coremapx.app.userDirectory.config.ConfigKeys.SYSTEM_DIALOG_THEME
import org.coremapx.app.userDirectory.config.ConfigKeys.THEME
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_MAIN_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.WARNING_COLOR
import org.coremapx.app.userDirectory.config.ConfigStates
import org.coremapx.app.userDirectory.config.ConfigValidator
import java.io.File
import java.util.Properties

private val logger = KotlinLogging.logger {}

class ConfigRepository {
    private val configPath = PrivateConfig.UserDirectory.CONFIG_FILE_PATH
    private val defaultConfigPath = PrivateConfig.AppResources.DEFAULT_CONFIG_PATH

    private val userConfig: MutableMap<String, String> = mutableMapOf()
    private val defaultConfig: MutableMap<String, String> = mutableMapOf()

    var states: ConfigStates
        private set

    init {
        loadUserConfig()
        validateUserConfig()
        states = ConfigStates(this)
        updateTheme()
    }

    fun getStringValue(key: String): String = userConfig[key] ?: throw IllegalArgumentException("Unknown key:$key in config")

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
        val validateResult = ConfigValidator.Companion.validate(key, value)
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

    fun setThemeOnCustom() = setValue(THEME, "custom")

    private fun setTheme(themeConfig: ThemeConfig) {
        with(themeConfig) {
            setValue(PRIMARY, primary)
            setValue(PRIMARY_VARIANT, primaryVariant)
            setValue(SECONDARY, secondary)
            setValue(SECONDARY_VARIANT, secondaryVariant)
            setValue(BACKGROUND, background)
            setValue(SURFACE, surface)
            setValue(ERROR, error)
            setValue(ON_PRIMARY, onPrimary)
            setValue(ON_SECONDARY, onSecondary)
            setValue(ON_BACKGROUND, onBackground)
            setValue(ON_SURFACE, onSurface)
            setValue(ON_ERROR, onError)
            setValue(BORDER_COLOR, borderColor)
            setValue(SUCCESS_COLOR, successColor)
            setValue(WARNING_COLOR, warningColor)
            setValue(VERTEX_MAIN_COLOR, vertexMainColor)
            setValue(HOVERED_BORDER_COLOR, hoveredBorderColor)
            setValue(EDGE_MAIN_COLOR, edgeMainColor)
            setValue(CANVAS_BACKGROUND_COLOR, canvasBackgroundColor)
            setValue(COMMAND_LINE_BACKGROUND_COLOR, commandLineBackgroundColor)
            setValue(SYSTEM_DIALOG_THEME, if (isLight) "light" else "dark")
        }
    }

    private fun updateConfigFile(
        key: String,
        value: String,
    ): Result<Boolean> {
        val configFile = File(configPath)
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
        loadConfig(configPath, userConfig)
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
            val validateResult = ConfigValidator.Companion.validate(key, value)
            if (validateResult is Result.Error) {
                throw IllegalArgumentException("ERROR: ${validateResult.error.type}.${validateResult.error.description}")
            }
        }
    }
}
