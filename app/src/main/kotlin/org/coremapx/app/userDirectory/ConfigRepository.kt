package org.coremapx.app.userDirectory

import androidx.compose.ui.graphics.Color
import model.result.showConfigErrorDialog
import mu.KotlinLogging
import java.io.File
import java.util.Properties

private val logger = KotlinLogging.logger {}

class ConfigRepository {
    private val mainConfigPath = "${System.getProperty("user.home")}/.coremapx/config/Config.gcfg"
    private val defaultConfigPath = "app/src/main/resources/Configs/DefaultConfig.gcfg"
    private val privateConfigPath = "app/src/main/resources/Configs/PrivateConfig.gcfg"
    private var config: Map<String, String> = emptyMap()
    private val userConfig: MutableMap<String, String> = mutableMapOf()
    private val privateConfig: MutableMap<String, String> = mutableMapOf()
    private val defaultConfig: MutableMap<String, String> = mutableMapOf()

    init {
        loadUserConfig()
        loadPrivateConfig()
        config = joinTwoConfigs(userConfig, privateConfig)
        validateUserConfig()
    }

    fun getStringValue(key: String): String? = config[key]

    fun getBooleanValue(key: String): Boolean? = config[key]?.toBooleanStrictOrNull()

    fun getIntValue(key: String): Int? = config[key]?.toIntOrNull()

    fun getLongValue(key: String): Long? = config[key]?.toLongOrNull()

    fun getDoubleValue(key: String): Double? = config[key]?.toDoubleOrNull()

    fun getFloatValue(key: String): Float? = config[key]?.toFloatOrNull()

    fun getColor(key: String): Color {
        val colorForException = Color(0x000000)
        val stringColor = config[key]
        if (stringColor == null) return colorForException
        return try {
            tryConvertStringToColor(stringColor)
        } catch (ex: IllegalArgumentException) {
            colorForException
        }
    }

    fun setValue(
        key: String,
        value: String,
    ) {
        val configFile = File(mainConfigPath)
        val properties = Properties()
        if (configFile.exists()) {
            configFile.inputStream().use { input ->
                properties.load(input)
            }
        }
        properties.setProperty(key, value)
        configFile.outputStream().use { output ->
            properties.store(output, "Updated Config")
        }
        userConfig[key] = value
        logger.info { "Updated config. For key: $key new value: $value" }
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

    private fun joinTwoConfigs(
        first: MutableMap<String, String>,
        second: MutableMap<String, String>,
    ): Map<String, String> = HashMap(first).apply { putAll(second) }

    private fun comparisonWithDefaultConfig() {
        loadDefaultConfig()
        val missingParameters = mutableListOf<String>()
        defaultConfig.forEach { param ->
            if (!userConfig.containsKey(param.key)) missingParameters.add(param.key)
        }
        if (missingParameters.isNotEmpty()) showConfigErrorDialog("Missing parameters in config: $missingParameters")
    }

    private fun getColorErrorMessage(propertyName: String): String = "$propertyName must be color in hex format. For example `...=#FFFFFF`"

    private fun tryConvertStringToColor(color: String): Color {
        if (!color.startsWith("#")) {
            throw IllegalArgumentException("Color cannot start without `#`")
        }

        return try {
            val colorInt = "FF${color.removePrefix("#")}".toLong(16)
            Color(colorInt)
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("Invalid color format: $color")
        }
    }

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
            showConfigErrorDialog(ex.message ?: "Unknown config error")
        }
    }

    private fun validateColor(value: String): Boolean {
        if (!value.startsWith("#")) {
            return false
        }
        try {
            java.awt.Color.decode(value)
            return true
        } catch (ex: NumberFormatException) {
            return false
        }
    }

    private fun checkGeneralSettings() {
        require(getStringValue("language") in listOf("ru", "en")) { "Supported languages: en, ru" }
        require(getStringValue("theme") in listOf("light", "dark")) { "Supported themes: light, dark" }
        require(getStringValue("fileDialogTheme") in listOf("light", "dark")) { "Supported fileDialogTheme: light, dark" }
    }

    private fun checkColorsSettings() {
        require(validateColor(getStringValue("primary") ?: "")) { getColorErrorMessage("primary") }
        require(validateColor(getStringValue("primaryVariant") ?: "")) { getColorErrorMessage("primaryVariant") }
        require(validateColor(getStringValue("secondary") ?: "")) { getColorErrorMessage("secondary") }
        require(validateColor(getStringValue("secondaryVariant") ?: "")) { getColorErrorMessage("secondaryVariant") }
        require(validateColor(getStringValue("background") ?: "")) { getColorErrorMessage("background") }
        require(validateColor(getStringValue("surface") ?: "")) { getColorErrorMessage("surface") }
        require(validateColor(getStringValue("error") ?: "")) { getColorErrorMessage("error") }
        require(validateColor(getStringValue("onPrimary") ?: "")) { getColorErrorMessage("onPrimary") }
        require(validateColor(getStringValue("onSecondary") ?: "")) { getColorErrorMessage("onSecondary") }
        require(validateColor(getStringValue("onBackground") ?: "")) { getColorErrorMessage("onBackground") }
        require(validateColor(getStringValue("onSurface") ?: "")) { getColorErrorMessage("onSurface") }
        require(validateColor(getStringValue("onError") ?: "")) { getColorErrorMessage("onError") }

        require(validateColor(getStringValue("borderColor") ?: "")) { getColorErrorMessage("borderColor") }
        require(validateColor(getStringValue("cancelIconColor") ?: "")) { getColorErrorMessage("cancelIconColor") }
        require(validateColor(getStringValue("warningColor") ?: "")) { getColorErrorMessage("warningColor") }
        require(validateColor(getStringValue("vertexMainColor") ?: "")) { getColorErrorMessage("vertexMainColor") }
        require(validateColor(getStringValue("hoveredBorderColor") ?: "")) { getColorErrorMessage("hoveredBorderColor") }
        require(validateColor(getStringValue("edgeMainColor") ?: "")) { getColorErrorMessage("edgeMainColor") }
        require(validateColor(getStringValue("canvasBackgroundColor") ?: "")) { getColorErrorMessage("canvasBackgroundColor") }
        require(validateColor(getStringValue("commandLineBackgroundColor") ?: "")) { getColorErrorMessage("commandLineBackgroundColor") }
    }

    private fun checkMainScreenSettings() {
        require((((getIntValue("mainScreenStartHeight") ?: 0) >= 720))) { "mainScreenStartHeight must be >= 720 px" }
        require((((getIntValue("mainScreenStartWidth") ?: 0) >= 1280))) { "mainScreenStartWidth must be >= 1280 px" }
        require(getStringValue("startWindowPlacement") in listOf("FullScreen", "Floating", "Maximized")) {
            "Supported startWindowPlacement values: FullScreen, Floating, Maximized"
        }
    }

    private fun checkMainMenuSettings() {
        require((((getIntValue("mainMenuWidth") ?: 0) >= 200))) { "mainMenuWidth must be >= 200 dp" }
    }

    private fun checkTitleBarSettings() {
        require((((getIntValue("titleBarHeight") ?: 0) >= 35))) { "titleBarHeight must be >= 35 dp" }
        require((((getIntValue("titleBarIconSize") ?: 0) >= 16))) { "titleBarIconSize must be >= 16 dp" }
    }

    private fun checkCommandFieldSettings() {
        require((((getIntValue("messageOutputHeight") ?: 0) >= 150))) { "messageOutputHeight must be >= 150 dp" }
        val maxCountMessages = getIntValue("maxCountMessages") ?: 0
        require((1 <= maxCountMessages) && (maxCountMessages <= 10000)) { "maxCountMessages must be >= 1, but <= 10000" }
        require((((getIntValue("commandFieldWidth") ?: 0) >= 400))) { "commandFieldWidth must be >= 400 dp" }
        require((getBooleanValue("isTransparentCommandLine")) != null) { "isTransparentCommandLine must be true or false" }
    }

    private fun checkWorkAreaSettings() {
        require((((getIntValue("graphLayoutHeight") ?: 0) >= 2000))) { "graphLayoutHeight must be >= 2000 dp" }
        require((((getIntValue("graphLayoutWidth") ?: 0) >= 1000))) { "graphLayoutWidth must be >= 1000 dp" }
        require((((getIntValue("vertexRadius") ?: 0) >= 1))) { "vertexRadius must be >= 1 dp" }
        require((((getIntValue("vertexLabelSize") ?: 0) >= 1))) { "vertexLabelSize must be >= 1 sp" }
        require((((getIntValue("edgeLabelSize") ?: 0) >= 1))) { "edgeLabelSize must be >= 1 sp" }
        val edgeArrowSize = getIntValue("edgeArrowSize") ?: 0
        require((1 <= edgeArrowSize) && (edgeArrowSize <= 100)) { "edgeArrowSize must be >= 1, but <= 100" }
        require((((getIntValue("edgeWidth") ?: 0) >= 1))) { "edgeWidth must be >= 1 dp" }
        val canvasDragRatio = getDoubleValue("canvasDragRatio") ?: 0.0
        require((0.1 <= canvasDragRatio) && (canvasDragRatio <= 10)) { "canvasDragRatio must be >= 0.1, but <= 10" }
        require((((getIntValue("canvasLimit") ?: 0) >= 2000))) { "canvasLimit must be >= 2000 px" }
    }

    private fun checkPerformanceSettings() {
        val animationDuration = getIntValue("animationDuration") ?: 0
        require((100 <= animationDuration) && (animationDuration <= 1500)) { "animationDuration must be >= 100, but <= 1500 ms" }
        val commandFieldScrollDelay = getIntValue("commandFieldScrollDelay") ?: 0
        require(
            (10 <= commandFieldScrollDelay) && (commandFieldScrollDelay <= 300),
        ) { "commandFieldScrollDelay must be >= 10, but <= 300 ms" }
    }
}
