package org.coremapx.app.userDirectory.config

import androidx.compose.runtime.mutableStateOf

class ConfigStates(
    private val configRepository: ConfigRepository,
) {
    // General
    val version = mutableStateOf(configRepository.getStringValue(ConfigKeys.VERSION))
    val language = mutableStateOf(configRepository.getStringValue(ConfigKeys.LANGUAGE))
    val theme = mutableStateOf(configRepository.getStringValue(ConfigKeys.THEME))
    val systemDialogTheme = mutableStateOf(configRepository.getStringValue(ConfigKeys.SYSTEM_DIALOG_THEME))
    val isExpandedSettings = mutableStateOf(configRepository.getBooleanValue(ConfigKeys.IS_EXPANDED_SETTINGS))

    // Colors
    val primary = mutableStateOf(configRepository.getColor(ConfigKeys.PRIMARY))
    val primaryVariant = mutableStateOf(configRepository.getColor(ConfigKeys.PRIMARY_VARIANT))
    val secondary = mutableStateOf(configRepository.getColor(ConfigKeys.SECONDARY))
    val secondaryVariant = mutableStateOf(configRepository.getColor(ConfigKeys.SECONDARY_VARIANT))
    val background = mutableStateOf(configRepository.getColor(ConfigKeys.BACKGROUND))
    val surface = mutableStateOf(configRepository.getColor(ConfigKeys.SURFACE))
    val error = mutableStateOf(configRepository.getColor(ConfigKeys.ERROR))
    val onPrimary = mutableStateOf(configRepository.getColor(ConfigKeys.ON_PRIMARY))
    val onSecondary = mutableStateOf(configRepository.getColor(ConfigKeys.ON_SECONDARY))
    val onBackground = mutableStateOf(configRepository.getColor(ConfigKeys.ON_BACKGROUND))
    val onSurface = mutableStateOf(configRepository.getColor(ConfigKeys.ON_SURFACE))
    val onError = mutableStateOf(configRepository.getColor(ConfigKeys.ON_ERROR))
    val borderColor = mutableStateOf(configRepository.getColor(ConfigKeys.BORDER_COLOR))
    val successColor = mutableStateOf(configRepository.getColor(ConfigKeys.SUCCESS_COLOR))
    val warningColor = mutableStateOf(configRepository.getColor(ConfigKeys.WARNING_COLOR))
    val vertexMainColor = mutableStateOf(configRepository.getColor(ConfigKeys.VERTEX_MAIN_COLOR))
    val hoveredBorderColor = mutableStateOf(configRepository.getColor(ConfigKeys.HOVERED_BORDER_COLOR))
    val edgeMainColor = mutableStateOf(configRepository.getColor(ConfigKeys.EDGE_MAIN_COLOR))
    val shortestPathColor = mutableStateOf(configRepository.getColor(ConfigKeys.SHORTEST_PATH_COLOR))
    val otherPathsColor = mutableStateOf(configRepository.getColor(ConfigKeys.OTHER_PATHS_COLOR))
    val canvasBackgroundColor = mutableStateOf(configRepository.getColor(ConfigKeys.CANVAS_BACKGROUND_COLOR))
    val consoleBackgroundColor = mutableStateOf(configRepository.getColor(ConfigKeys.CONSOLE_BACKGROUND_COLOR))

    // Main Screen
    val mainScreenStartHeight = mutableStateOf(configRepository.getIntValue(ConfigKeys.MAIN_SCREEN_START_HEIGHT))
    val mainScreenStartWidth = mutableStateOf(configRepository.getIntValue(ConfigKeys.MAIN_SCREEN_START_WIDTH))
    val startWindowPlacement = mutableStateOf(configRepository.getStringValue(ConfigKeys.START_WINDOW_PLACEMENT))

    // Title Bar
    val titleBarHeight = mutableStateOf(configRepository.getIntValue(ConfigKeys.TITLE_BAR_HEIGHT))
    val titleBarIconSize = mutableStateOf(configRepository.getIntValue(ConfigKeys.TITLE_BAR_ICON_SIZE))

    // Console
    val consoleHeight = mutableStateOf(configRepository.getIntValue(ConfigKeys.CONSOLE_HEIGHT))
    val consoleWidth = mutableStateOf(configRepository.getIntValue(ConfigKeys.CONSOLE_WIDTH))
    val maxCountMessages = mutableStateOf(configRepository.getIntValue(ConfigKeys.MAX_COUNT_MESSAGES))
    val maxCountUserCommands = mutableStateOf(configRepository.getIntValue(ConfigKeys.MAX_COUNT_USER_COMMANDS))
    val isTransparentConsoleBlock =
        mutableStateOf(configRepository.getBooleanValue(ConfigKeys.IS_TRANSPARENT_CONSOLE))

    // Work Area
    val graphLayoutHeight = mutableStateOf(configRepository.getIntValue(ConfigKeys.GRAPH_LAYOUT_HEIGHT))
    val graphLayoutWidth = mutableStateOf(configRepository.getIntValue(ConfigKeys.GRAPH_LAYOUT_WIDTH))
    val vertexRadius = mutableStateOf(configRepository.getIntValue(ConfigKeys.VERTEX_RADIUS))
    val vertexLabelSize = mutableStateOf(configRepository.getIntValue(ConfigKeys.VERTEX_LABEL_SIZE))
    val edgeLabelSize = mutableStateOf(configRepository.getIntValue(ConfigKeys.EDGE_LABEL_SIZE))
    val edgeArrowSize = mutableStateOf(configRepository.getFloatValue(ConfigKeys.EDGE_ARROW_SIZE))
    val edgeWidth = mutableStateOf(configRepository.getFloatValue(ConfigKeys.EDGE_WIDTH))
    val canvasDragRatio = mutableStateOf(configRepository.getFloatValue(ConfigKeys.CANVAS_DRAG_RATIO))
    val canvasLimit = mutableStateOf(configRepository.getIntValue(ConfigKeys.CANVAS_LIMIT))

    // Performance
    val animationDuration = mutableStateOf(configRepository.getIntValue(ConfigKeys.ANIMATION_DURATION))

    fun updateValue(
        key: String,
        value: String,
    ) {
        when (key) {
            // General
            ConfigKeys.VERSION -> version.value = value
            ConfigKeys.LANGUAGE -> language.value = value
            ConfigKeys.THEME -> theme.value = value
            ConfigKeys.SYSTEM_DIALOG_THEME -> systemDialogTheme.value = value
            ConfigKeys.IS_EXPANDED_SETTINGS -> isExpandedSettings.value = configRepository.getBooleanValue(ConfigKeys.IS_EXPANDED_SETTINGS)

            // Colors
            ConfigKeys.PRIMARY -> primary.value = configRepository.getColor(ConfigKeys.PRIMARY)
            ConfigKeys.PRIMARY_VARIANT -> primaryVariant.value = configRepository.getColor(ConfigKeys.PRIMARY_VARIANT)
            ConfigKeys.SECONDARY -> secondary.value = configRepository.getColor(ConfigKeys.SECONDARY)
            ConfigKeys.SECONDARY_VARIANT -> secondaryVariant.value = configRepository.getColor(ConfigKeys.SECONDARY_VARIANT)
            ConfigKeys.BACKGROUND -> background.value = configRepository.getColor(ConfigKeys.BACKGROUND)
            ConfigKeys.SURFACE -> surface.value = configRepository.getColor(ConfigKeys.SURFACE)
            ConfigKeys.ERROR -> error.value = configRepository.getColor(ConfigKeys.ERROR)
            ConfigKeys.ON_PRIMARY -> onPrimary.value = configRepository.getColor(ConfigKeys.ON_PRIMARY)
            ConfigKeys.ON_SECONDARY -> onSecondary.value = configRepository.getColor(ConfigKeys.ON_SECONDARY)
            ConfigKeys.ON_BACKGROUND -> onBackground.value = configRepository.getColor(ConfigKeys.ON_BACKGROUND)
            ConfigKeys.ON_SURFACE -> onSurface.value = configRepository.getColor(ConfigKeys.ON_SURFACE)
            ConfigKeys.ON_ERROR -> onError.value = configRepository.getColor(ConfigKeys.ON_ERROR)
            ConfigKeys.BORDER_COLOR -> borderColor.value = configRepository.getColor(ConfigKeys.BORDER_COLOR)
            ConfigKeys.SUCCESS_COLOR -> successColor.value = configRepository.getColor(ConfigKeys.SUCCESS_COLOR)
            ConfigKeys.WARNING_COLOR -> warningColor.value = configRepository.getColor(ConfigKeys.WARNING_COLOR)
            ConfigKeys.VERTEX_MAIN_COLOR -> vertexMainColor.value = configRepository.getColor(ConfigKeys.VERTEX_MAIN_COLOR)
            ConfigKeys.HOVERED_BORDER_COLOR -> hoveredBorderColor.value = configRepository.getColor(ConfigKeys.HOVERED_BORDER_COLOR)
            ConfigKeys.EDGE_MAIN_COLOR -> edgeMainColor.value = configRepository.getColor(ConfigKeys.EDGE_MAIN_COLOR)
            ConfigKeys.SHORTEST_PATH_COLOR -> shortestPathColor.value = configRepository.getColor(ConfigKeys.SHORTEST_PATH_COLOR)
            ConfigKeys.OTHER_PATHS_COLOR -> otherPathsColor.value = configRepository.getColor(ConfigKeys.OTHER_PATHS_COLOR)
            ConfigKeys.CANVAS_BACKGROUND_COLOR ->
                canvasBackgroundColor.value =
                    configRepository.getColor(ConfigKeys.CANVAS_BACKGROUND_COLOR)
            ConfigKeys.CONSOLE_BACKGROUND_COLOR ->
                consoleBackgroundColor.value =
                    configRepository.getColor(
                        ConfigKeys.CONSOLE_BACKGROUND_COLOR,
                    )

            // Main Screen
            ConfigKeys.MAIN_SCREEN_START_HEIGHT ->
                mainScreenStartHeight.value =
                    configRepository.getIntValue(ConfigKeys.MAIN_SCREEN_START_HEIGHT)
            ConfigKeys.MAIN_SCREEN_START_WIDTH ->
                mainScreenStartWidth.value =
                    configRepository.getIntValue(ConfigKeys.MAIN_SCREEN_START_WIDTH)
            ConfigKeys.START_WINDOW_PLACEMENT -> startWindowPlacement.value = value

            // Title Bar
            ConfigKeys.TITLE_BAR_HEIGHT -> titleBarHeight.value = configRepository.getIntValue(ConfigKeys.TITLE_BAR_HEIGHT)
            ConfigKeys.TITLE_BAR_ICON_SIZE -> titleBarIconSize.value = configRepository.getIntValue(ConfigKeys.TITLE_BAR_ICON_SIZE)

            // Console
            ConfigKeys.CONSOLE_HEIGHT -> consoleHeight.value = configRepository.getIntValue(ConfigKeys.CONSOLE_HEIGHT)
            ConfigKeys.MAX_COUNT_MESSAGES -> maxCountMessages.value = configRepository.getIntValue(ConfigKeys.MAX_COUNT_MESSAGES)
            ConfigKeys.MAX_COUNT_USER_COMMANDS ->
                maxCountUserCommands.value =
                    configRepository.getIntValue(ConfigKeys.MAX_COUNT_USER_COMMANDS)
            ConfigKeys.CONSOLE_WIDTH -> consoleWidth.value = configRepository.getIntValue(ConfigKeys.CONSOLE_WIDTH)
            ConfigKeys.IS_TRANSPARENT_CONSOLE ->
                isTransparentConsoleBlock.value =
                    configRepository.getBooleanValue(
                        ConfigKeys.IS_TRANSPARENT_CONSOLE,
                    )

            // Work Area
            ConfigKeys.GRAPH_LAYOUT_HEIGHT -> graphLayoutHeight.value = configRepository.getIntValue(ConfigKeys.GRAPH_LAYOUT_HEIGHT)
            ConfigKeys.GRAPH_LAYOUT_WIDTH -> graphLayoutWidth.value = configRepository.getIntValue(ConfigKeys.GRAPH_LAYOUT_WIDTH)
            ConfigKeys.VERTEX_RADIUS -> vertexRadius.value = configRepository.getIntValue(ConfigKeys.VERTEX_RADIUS)
            ConfigKeys.VERTEX_LABEL_SIZE -> vertexLabelSize.value = configRepository.getIntValue(ConfigKeys.VERTEX_LABEL_SIZE)
            ConfigKeys.EDGE_LABEL_SIZE -> edgeLabelSize.value = configRepository.getIntValue(ConfigKeys.EDGE_LABEL_SIZE)
            ConfigKeys.EDGE_ARROW_SIZE -> edgeArrowSize.value = configRepository.getFloatValue(ConfigKeys.EDGE_ARROW_SIZE)
            ConfigKeys.EDGE_WIDTH -> edgeWidth.value = configRepository.getFloatValue(ConfigKeys.EDGE_WIDTH)
            ConfigKeys.CANVAS_DRAG_RATIO -> canvasDragRatio.value = configRepository.getFloatValue(ConfigKeys.CANVAS_DRAG_RATIO)
            ConfigKeys.CANVAS_LIMIT -> canvasLimit.value = configRepository.getIntValue(ConfigKeys.CANVAS_LIMIT)

            // Performance
            ConfigKeys.ANIMATION_DURATION -> animationDuration.value = configRepository.getIntValue(ConfigKeys.ANIMATION_DURATION)
        }
    }
}
