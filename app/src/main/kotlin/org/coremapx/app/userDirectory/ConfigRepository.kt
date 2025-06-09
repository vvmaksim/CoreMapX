package org.coremapx.app.userDirectory

import androidx.compose.ui.graphics.Color
import extensions.toColorOrNull
import model.result.showConfigErrorDialog
import mu.KotlinLogging
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

    init {
        loadUserConfig()
        loadPrivateConfig()
        validateUserConfig()
    }

    fun getStringValue(key: String): String =
        userConfig[key] ?: privateConfig[key] ?: throw IllegalArgumentException("Unknown key:$key in config")

    fun getBooleanValue(key: String): Boolean =
        getStringValue(key).toBooleanStrictOrNull() ?: throw IllegalArgumentException("Invalid key:$key. Value must be `true` or `false`")

    fun getIntValue(key: String): Int =
        getStringValue(key).toIntOrNull() ?: throw IllegalArgumentException("Invalid key:$key. Value must be Int type")

    fun getLongValue(key: String): Long =
        getStringValue(key).toLongOrNull() ?: throw IllegalArgumentException("Invalid key:$key. Value must be Long type")

    fun getDoubleValue(key: String): Double =
        getStringValue(key).toDoubleOrNull() ?: throw IllegalArgumentException("Invalid key:$key. Value must be Double type")

    fun getFloatValue(key: String): Float =
        getStringValue(key).toFloatOrNull() ?: throw IllegalArgumentException("Invalid key:$key. Value must be Float type")

    fun getColor(key: String): Color = getStringValue(key).toColorOrNull() ?: throw IllegalArgumentException(getColorErrorMessage(key))

    fun setValue(
        key: String,
        value: String,
    ) {
        updateConfigFile(key, value)
        userConfig[key] = value
        logger.info { "Updated config. For key: $key new value: $value" }
    }

    private fun updateConfigFile(
        key: String,
        value: String,
    ) {
        val configFile = File(mainConfigPath)
        if (!configFile.exists()) {
            logger.error { "There is nothing to update, the configuration file has not been found" }
            return
        }
        val lines = configFile.readLines()
        val updatedLines =
            lines.map { line ->
                if (line.trim().startsWith("$key=")) {
                    "$key=$value"
                } else {
                    line
                }
            }
        if (lines == updatedLines) {
            logger.warn { "Key '$key' not found in config file" }
            return
        }
        configFile.bufferedWriter().use { writer ->
            updatedLines.forEachIndexed { index, line ->
                writer.write(line)
                if (index < updatedLines.size - 1) {
                    writer.newLine()
                }
            }
        }
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
        if (missingParameters.isNotEmpty()) showConfigErrorDialog("Missing parameters in config: $missingParameters")
    }

    private fun getColorErrorMessage(propertyName: String): String = "$propertyName must be color in hex format. For example `...=#FFFFFF`"

    private fun validateUserConfig() {
        try {
            comparisonWithDefaultConfig()
            checkGeneralSettings()
            checkColorsSettings()
            checkMainScreenSettings()
            checkMainMenuSettings()
            checkTitleBarSettings()
            checkCommandFieldSettings()
            checkWorkAreaSettings()
            checkPerformanceSettings()
            logger.info { "Config has been loaded successfully" }
        } catch (ex: IllegalArgumentException) {
            logger.error { "Config validation failed" }
            showConfigErrorDialog(ex.message ?: "Config error")
        }
    }

    private fun validateColor(value: String): Boolean = value.toColorOrNull() != null

    private fun checkGeneralSettings() {
        require(getStringValue("language") in listOf("ru", "en")) { "Supported languages: en, ru" }
        require(getStringValue("theme") in listOf("light", "dark")) { "Supported themes: light, dark" }
        require(getStringValue("fileDialogTheme") in listOf("light", "dark")) { "Supported fileDialogTheme: light, dark" }
    }

    private fun checkColorsSettings() {
        require(validateColor(getStringValue("primary"))) { getColorErrorMessage("primary") }
        require(validateColor(getStringValue("primaryVariant"))) { getColorErrorMessage("primaryVariant") }
        require(validateColor(getStringValue("secondary"))) { getColorErrorMessage("secondary") }
        require(validateColor(getStringValue("secondaryVariant"))) { getColorErrorMessage("secondaryVariant") }
        require(validateColor(getStringValue("background"))) { getColorErrorMessage("background") }
        require(validateColor(getStringValue("surface"))) { getColorErrorMessage("surface") }
        require(validateColor(getStringValue("error"))) { getColorErrorMessage("error") }
        require(validateColor(getStringValue("onPrimary"))) { getColorErrorMessage("onPrimary") }
        require(validateColor(getStringValue("onSecondary"))) { getColorErrorMessage("onSecondary") }
        require(validateColor(getStringValue("onBackground"))) { getColorErrorMessage("onBackground") }
        require(validateColor(getStringValue("onSurface"))) { getColorErrorMessage("onSurface") }
        require(validateColor(getStringValue("onError"))) { getColorErrorMessage("onError") }

        require(validateColor(getStringValue("borderColor"))) { getColorErrorMessage("borderColor") }
        require(validateColor(getStringValue("cancelIconColor"))) { getColorErrorMessage("cancelIconColor") }
        require(validateColor(getStringValue("warningColor"))) { getColorErrorMessage("warningColor") }
        require(validateColor(getStringValue("vertexMainColor"))) { getColorErrorMessage("vertexMainColor") }
        require(validateColor(getStringValue("hoveredBorderColor"))) { getColorErrorMessage("hoveredBorderColor") }
        require(validateColor(getStringValue("edgeMainColor"))) { getColorErrorMessage("edgeMainColor") }
        require(validateColor(getStringValue("canvasBackgroundColor"))) { getColorErrorMessage("canvasBackgroundColor") }
        require(validateColor(getStringValue("commandLineBackgroundColor"))) { getColorErrorMessage("commandLineBackgroundColor") }
    }

    private fun checkMainScreenSettings() {
        require((((getIntValue("mainScreenStartHeight")) >= 720))) { "mainScreenStartHeight must be >= 720 px" }
        require((((getIntValue("mainScreenStartWidth")) >= 1280))) { "mainScreenStartWidth must be >= 1280 px" }
        require(getStringValue("startWindowPlacement") in listOf("FullScreen", "Floating", "Maximized")) {
            "Supported startWindowPlacement values: FullScreen, Floating, Maximized"
        }
    }

    private fun checkMainMenuSettings() {
        require((((getIntValue("mainMenuWidth")) >= 200))) { "mainMenuWidth must be >= 200 dp" }
    }

    private fun checkTitleBarSettings() {
        require((((getIntValue("titleBarHeight")) >= 35))) { "titleBarHeight must be >= 35 dp" }
        require((((getIntValue("titleBarIconSize")) >= 16))) { "titleBarIconSize must be >= 16 dp" }
    }

    private fun checkCommandFieldSettings() {
        require((((getIntValue("messageOutputHeight")) >= 150))) { "messageOutputHeight must be >= 150 dp" }
        val maxCountMessages = getIntValue("maxCountMessages")
        require((1 <= maxCountMessages) && (maxCountMessages <= 10000)) { "maxCountMessages must be >= 1, but <= 10000" }
        require((((getIntValue("commandFieldWidth")) >= 400))) { "commandFieldWidth must be >= 400 dp" }
    }

    private fun checkWorkAreaSettings() {
        require((((getIntValue("graphLayoutHeight")) >= 2000))) { "graphLayoutHeight must be >= 2000 dp" }
        require((((getIntValue("graphLayoutWidth")) >= 1000))) { "graphLayoutWidth must be >= 1000 dp" }
        require((((getIntValue("vertexRadius")) >= 1))) { "vertexRadius must be >= 1 dp" }
        require((((getIntValue("vertexLabelSize")) >= 1))) { "vertexLabelSize must be >= 1 sp" }
        require((((getIntValue("edgeLabelSize")) >= 1))) { "edgeLabelSize must be >= 1 sp" }
        val edgeArrowSize = getIntValue("edgeArrowSize")
        require((1 <= edgeArrowSize) && (edgeArrowSize <= 100)) { "edgeArrowSize must be >= 1, but <= 100" }
        require((((getIntValue("edgeWidth")) >= 1))) { "edgeWidth must be >= 1 dp" }
        val canvasDragRatio = getDoubleValue("canvasDragRatio")
        require((0.1 <= canvasDragRatio) && (canvasDragRatio <= 10)) { "canvasDragRatio must be >= 0.1, but <= 10" }
        require((((getIntValue("canvasLimit")) >= 2000))) { "canvasLimit must be >= 2000 px" }
    }

    private fun checkPerformanceSettings() {
        val animationDuration = getIntValue("animationDuration")
        require((100 <= animationDuration) && (animationDuration <= 1500)) { "animationDuration must be >= 100, but <= 1500 ms" }
        val commandFieldScrollDelay = getIntValue("commandFieldScrollDelay")
        require(
            (10 <= commandFieldScrollDelay) && (commandFieldScrollDelay <= 300),
        ) { "commandFieldScrollDelay must be >= 10, but <= 300 ms" }
    }
}
