package org.coremapx.app.userDirectory

import androidx.compose.runtime.mutableStateOf

class ConfigStates(
    private val configRepository: ConfigRepository,
) {
    // General
    val language = mutableStateOf(configRepository.getStringValue("language"))
    val theme = mutableStateOf(configRepository.getStringValue("theme"))
    val fileDialogTheme = mutableStateOf(configRepository.getStringValue("fileDialogTheme"))

    // Colors
    val primary = mutableStateOf(configRepository.getColor("primary"))
    val primaryVariant = mutableStateOf(configRepository.getColor("primaryVariant"))
    val secondary = mutableStateOf(configRepository.getColor("secondary"))
    val secondaryVariant = mutableStateOf(configRepository.getColor("secondaryVariant"))
    val background = mutableStateOf(configRepository.getColor("background"))
    val surface = mutableStateOf(configRepository.getColor("surface"))
    val error = mutableStateOf(configRepository.getColor("error"))
    val onPrimary = mutableStateOf(configRepository.getColor("onPrimary"))
    val onSecondary = mutableStateOf(configRepository.getColor("onSecondary"))
    val onBackground = mutableStateOf(configRepository.getColor("onBackground"))
    val onSurface = mutableStateOf(configRepository.getColor("onSurface"))
    val onError = mutableStateOf(configRepository.getColor("onError"))
    val borderColor = mutableStateOf(configRepository.getColor("borderColor"))
    val warningColor = mutableStateOf(configRepository.getColor("warningColor"))
    val vertexMainColor = mutableStateOf(configRepository.getColor("vertexMainColor"))
    val hoveredBorderColor = mutableStateOf(configRepository.getColor("hoveredBorderColor"))
    val edgeMainColor = mutableStateOf(configRepository.getColor("edgeMainColor"))
    val canvasBackgroundColor = mutableStateOf(configRepository.getColor("canvasBackgroundColor"))
    val commandLineBackgroundColor = mutableStateOf(configRepository.getColor("commandLineBackgroundColor"))

    // Main Screen
    val mainScreenStartHeight = mutableStateOf(configRepository.getIntValue("mainScreenStartHeight"))
    val mainScreenStartWidth = mutableStateOf(configRepository.getIntValue("mainScreenStartWidth"))
    val startWindowPlacement = mutableStateOf(configRepository.getStringValue("startWindowPlacement"))

    // Title Bar
    val titleBarHeight = mutableStateOf(configRepository.getIntValue("titleBarHeight"))
    val titleBarIconSize = mutableStateOf(configRepository.getIntValue("titleBarIconSize"))

    // Command Field
    val messageOutputHeight = mutableStateOf(configRepository.getIntValue("messageOutputHeight"))
    val maxCountMessages = mutableStateOf(configRepository.getIntValue("maxCountMessages"))
    val commandFieldWidth = mutableStateOf(configRepository.getIntValue("commandFieldWidth"))
    val isTransparentCommandLine = mutableStateOf(configRepository.getBooleanValue("isTransparentCommandLine"))

    // Work Area
    val graphLayoutHeight = mutableStateOf(configRepository.getIntValue("graphLayoutHeight"))
    val graphLayoutWidth = mutableStateOf(configRepository.getIntValue("graphLayoutWidth"))
    val vertexRadius = mutableStateOf(configRepository.getIntValue("vertexRadius"))
    val vertexLabelSize = mutableStateOf(configRepository.getIntValue("vertexLabelSize"))
    val edgeLabelSize = mutableStateOf(configRepository.getIntValue("edgeLabelSize"))
    val edgeArrowSize = mutableStateOf(configRepository.getFloatValue("edgeArrowSize"))
    val edgeWidth = mutableStateOf(configRepository.getFloatValue("edgeWidth"))
    val canvasDragRatio = mutableStateOf(configRepository.getFloatValue("canvasDragRatio"))
    val canvasLimit = mutableStateOf(configRepository.getIntValue("canvasLimit"))

    // Performance
    val animationDuration = mutableStateOf(configRepository.getIntValue("animationDuration"))
    val commandFieldScrollDelay = mutableStateOf(configRepository.getIntValue("commandFieldScrollDelay"))

    fun updateValue(
        key: String,
        value: String,
    ) {
        when (key) {
            // General
            "language" -> language.value = value
            "theme" -> theme.value = value
            "fileDialogTheme" -> fileDialogTheme.value = value

            // Colors
            "primary" -> primary.value = configRepository.getColor("primary")
            "primaryVariant" -> primaryVariant.value = configRepository.getColor("primaryVariant")
            "secondary" -> secondary.value = configRepository.getColor("secondary")
            "secondaryVariant" -> secondaryVariant.value = configRepository.getColor("secondaryVariant")
            "background" -> background.value = configRepository.getColor("background")
            "surface" -> surface.value = configRepository.getColor("surface")
            "error" -> error.value = configRepository.getColor("error")
            "onPrimary" -> onPrimary.value = configRepository.getColor("onPrimary")
            "onSecondary" -> onSecondary.value = configRepository.getColor("onSecondary")
            "onBackground" -> onBackground.value = configRepository.getColor("onBackground")
            "onSurface" -> onSurface.value = configRepository.getColor("onSurface")
            "onError" -> onError.value = configRepository.getColor("onError")
            "borderColor" -> borderColor.value = configRepository.getColor("borderColor")
            "warningColor" -> warningColor.value = configRepository.getColor("warningColor")
            "vertexMainColor" -> vertexMainColor.value = configRepository.getColor("vertexMainColor")
            "hoveredBorderColor" -> hoveredBorderColor.value = configRepository.getColor("hoveredBorderColor")
            "edgeMainColor" -> edgeMainColor.value = configRepository.getColor("edgeMainColor")
            "canvasBackgroundColor" -> canvasBackgroundColor.value = configRepository.getColor("canvasBackgroundColor")
            "commandLineBackgroundColor" -> commandLineBackgroundColor.value = configRepository.getColor("commandLineBackgroundColor")

            // Main Screen
            "mainScreenStartHeight" -> mainScreenStartHeight.value = configRepository.getIntValue("mainScreenStartHeight")
            "mainScreenStartWidth" -> mainScreenStartWidth.value = configRepository.getIntValue("mainScreenStartWidth")
            "startWindowPlacement" -> startWindowPlacement.value = value

            // Title Bar
            "titleBarHeight" -> titleBarHeight.value = configRepository.getIntValue("titleBarHeight")
            "titleBarIconSize" -> titleBarIconSize.value = configRepository.getIntValue("titleBarIconSize")

            // Command Field
            "messageOutputHeight" -> messageOutputHeight.value = configRepository.getIntValue("messageOutputHeight")
            "maxCountMessages" -> maxCountMessages.value = configRepository.getIntValue("maxCountMessages")
            "commandFieldWidth" -> commandFieldWidth.value = configRepository.getIntValue("commandFieldWidth")
            "isTransparentCommandLine" -> isTransparentCommandLine.value = configRepository.getBooleanValue("isTransparentCommandLine")

            // Work Area
            "graphLayoutHeight" -> graphLayoutHeight.value = configRepository.getIntValue("graphLayoutHeight")
            "graphLayoutWidth" -> graphLayoutWidth.value = configRepository.getIntValue("graphLayoutWidth")
            "vertexRadius" -> vertexRadius.value = configRepository.getIntValue("vertexRadius")
            "vertexLabelSize" -> vertexLabelSize.value = configRepository.getIntValue("vertexLabelSize")
            "edgeLabelSize" -> edgeLabelSize.value = configRepository.getIntValue("edgeLabelSize")
            "edgeArrowSize" -> edgeArrowSize.value = configRepository.getFloatValue("edgeArrowSize")
            "edgeWidth" -> edgeWidth.value = configRepository.getFloatValue("edgeWidth")
            "canvasDragRatio" -> canvasDragRatio.value = configRepository.getFloatValue("canvasDragRatio")
            "canvasLimit" -> canvasLimit.value = configRepository.getIntValue("canvasLimit")

            // Performance
            "animationDuration" -> animationDuration.value = configRepository.getIntValue("animationDuration")
            "commandFieldScrollDelay" -> commandFieldScrollDelay.value = configRepository.getIntValue("commandFieldScrollDelay")
        }
    }
}
