package org.coremapx.app.userDirectory

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.userDirectory.config.ConfigKeys.ANIMATION_DURATION
import org.coremapx.app.userDirectory.config.ConfigKeys.BACKGROUND
import org.coremapx.app.userDirectory.config.ConfigKeys.BORDER_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_DRAG_RATIO
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_LIMIT
import org.coremapx.app.userDirectory.config.ConfigKeys.COMMAND_FIELD_SCROLL_DELAY
import org.coremapx.app.userDirectory.config.ConfigKeys.COMMAND_FIELD_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.COMMAND_LINE_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_ARROW_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_LABEL_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_MAIN_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.ERROR
import org.coremapx.app.userDirectory.config.ConfigKeys.GRAPH_LAYOUT_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.GRAPH_LAYOUT_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.HOVERED_BORDER_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.IS_EXPANDED_SETTINGS
import org.coremapx.app.userDirectory.config.ConfigKeys.IS_TRANSPARENT_COMMAND_LINE
import org.coremapx.app.userDirectory.config.ConfigKeys.LANGUAGE
import org.coremapx.app.userDirectory.config.ConfigKeys.MAIN_SCREEN_START_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.MAIN_SCREEN_START_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.MAX_COUNT_MESSAGES
import org.coremapx.app.userDirectory.config.ConfigKeys.MESSAGE_OUTPUT_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_BACKGROUND
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_ERROR
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_PRIMARY
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_SECONDARY
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_SURFACE
import org.coremapx.app.userDirectory.config.ConfigKeys.PRIMARY
import org.coremapx.app.userDirectory.config.ConfigKeys.PRIMARY_VARIANT
import org.coremapx.app.userDirectory.config.ConfigKeys.SECONDARY
import org.coremapx.app.userDirectory.config.ConfigKeys.SECONDARY_VARIANT
import org.coremapx.app.userDirectory.config.ConfigKeys.START_WINDOW_PLACEMENT
import org.coremapx.app.userDirectory.config.ConfigKeys.SUCCESS_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.SURFACE
import org.coremapx.app.userDirectory.config.ConfigKeys.SYSTEM_DIALOG_THEME
import org.coremapx.app.userDirectory.config.ConfigKeys.THEME
import org.coremapx.app.userDirectory.config.ConfigKeys.TITLE_BAR_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.TITLE_BAR_ICON_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_LABEL_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_MAIN_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_RADIUS
import org.coremapx.app.userDirectory.config.ConfigKeys.WARNING_COLOR
import org.coremapx.app.userDirectory.config.ConfigRepository

class ConfigStates(
    private val configRepository: ConfigRepository,
) {
    // General
    val language = mutableStateOf(configRepository.getStringValue(LANGUAGE))
    val theme = mutableStateOf(configRepository.getStringValue(THEME))
    val systemDialogTheme = mutableStateOf(configRepository.getStringValue(SYSTEM_DIALOG_THEME))
    val isExpandedSettings = mutableStateOf(configRepository.getBooleanValue(IS_EXPANDED_SETTINGS))

    // Colors
    val primary = mutableStateOf(configRepository.getColor(PRIMARY))
    val primaryVariant = mutableStateOf(configRepository.getColor(PRIMARY_VARIANT))
    val secondary = mutableStateOf(configRepository.getColor(SECONDARY))
    val secondaryVariant = mutableStateOf(configRepository.getColor(SECONDARY_VARIANT))
    val background = mutableStateOf(configRepository.getColor(BACKGROUND))
    val surface = mutableStateOf(configRepository.getColor(SURFACE))
    val error = mutableStateOf(configRepository.getColor(ERROR))
    val onPrimary = mutableStateOf(configRepository.getColor(ON_PRIMARY))
    val onSecondary = mutableStateOf(configRepository.getColor(ON_SECONDARY))
    val onBackground = mutableStateOf(configRepository.getColor(ON_BACKGROUND))
    val onSurface = mutableStateOf(configRepository.getColor(ON_SURFACE))
    val onError = mutableStateOf(configRepository.getColor(ON_ERROR))
    val borderColor = mutableStateOf(configRepository.getColor(BORDER_COLOR))
    val successColor = mutableStateOf(configRepository.getColor(SUCCESS_COLOR))
    val warningColor = mutableStateOf(configRepository.getColor(WARNING_COLOR))
    val vertexMainColor = mutableStateOf(configRepository.getColor(VERTEX_MAIN_COLOR))
    val hoveredBorderColor = mutableStateOf(configRepository.getColor(HOVERED_BORDER_COLOR))
    val edgeMainColor = mutableStateOf(configRepository.getColor(EDGE_MAIN_COLOR))
    val canvasBackgroundColor = mutableStateOf(configRepository.getColor(CANVAS_BACKGROUND_COLOR))
    val commandLineBackgroundColor = mutableStateOf(configRepository.getColor(COMMAND_LINE_BACKGROUND_COLOR))

    // Main Screen
    val mainScreenStartHeight = mutableStateOf(configRepository.getIntValue(MAIN_SCREEN_START_HEIGHT))
    val mainScreenStartWidth = mutableStateOf(configRepository.getIntValue(MAIN_SCREEN_START_WIDTH))
    val startWindowPlacement = mutableStateOf(configRepository.getStringValue(START_WINDOW_PLACEMENT))

    // Title Bar
    val titleBarHeight = mutableStateOf(configRepository.getIntValue(TITLE_BAR_HEIGHT))
    val titleBarIconSize = mutableStateOf(configRepository.getIntValue(TITLE_BAR_ICON_SIZE))

    // Command Field
    val messageOutputHeight = mutableStateOf(configRepository.getIntValue(MESSAGE_OUTPUT_HEIGHT))
    val maxCountMessages = mutableStateOf(configRepository.getIntValue(MAX_COUNT_MESSAGES))
    val commandFieldWidth = mutableStateOf(configRepository.getIntValue(COMMAND_FIELD_WIDTH))
    val isTransparentCommandLine = mutableStateOf(configRepository.getBooleanValue(IS_TRANSPARENT_COMMAND_LINE))

    // Work Area
    val graphLayoutHeight = mutableStateOf(configRepository.getIntValue(GRAPH_LAYOUT_HEIGHT))
    val graphLayoutWidth = mutableStateOf(configRepository.getIntValue(GRAPH_LAYOUT_WIDTH))
    val vertexRadius = mutableStateOf(configRepository.getIntValue(VERTEX_RADIUS))
    val vertexLabelSize = mutableStateOf(configRepository.getIntValue(VERTEX_LABEL_SIZE))
    val edgeLabelSize = mutableStateOf(configRepository.getIntValue(EDGE_LABEL_SIZE))
    val edgeArrowSize = mutableStateOf(configRepository.getFloatValue(EDGE_ARROW_SIZE))
    val edgeWidth = mutableStateOf(configRepository.getFloatValue(EDGE_WIDTH))
    val canvasDragRatio = mutableStateOf(configRepository.getFloatValue(CANVAS_DRAG_RATIO))
    val canvasLimit = mutableStateOf(configRepository.getIntValue(CANVAS_LIMIT))

    // Performance
    val animationDuration = mutableStateOf(configRepository.getIntValue(ANIMATION_DURATION))
    val commandFieldScrollDelay = mutableStateOf(configRepository.getIntValue(COMMAND_FIELD_SCROLL_DELAY))

    fun updateValue(
        key: String,
        value: String,
    ) {
        when (key) {
            // General
            LANGUAGE -> language.value = value
            THEME -> theme.value = value
            SYSTEM_DIALOG_THEME -> systemDialogTheme.value = value
            IS_EXPANDED_SETTINGS -> isExpandedSettings.value = configRepository.getBooleanValue(IS_EXPANDED_SETTINGS)

            // Colors
            PRIMARY -> primary.value = configRepository.getColor(PRIMARY)
            PRIMARY_VARIANT -> primaryVariant.value = configRepository.getColor(PRIMARY_VARIANT)
            SECONDARY -> secondary.value = configRepository.getColor(SECONDARY)
            SECONDARY_VARIANT -> secondaryVariant.value = configRepository.getColor(SECONDARY_VARIANT)
            BACKGROUND -> background.value = configRepository.getColor(BACKGROUND)
            SURFACE -> surface.value = configRepository.getColor(SURFACE)
            ERROR -> error.value = configRepository.getColor(ERROR)
            ON_PRIMARY -> onPrimary.value = configRepository.getColor(ON_PRIMARY)
            ON_SECONDARY -> onSecondary.value = configRepository.getColor(ON_SECONDARY)
            ON_BACKGROUND -> onBackground.value = configRepository.getColor(ON_BACKGROUND)
            ON_SURFACE -> onSurface.value = configRepository.getColor(ON_SURFACE)
            ON_ERROR -> onError.value = configRepository.getColor(ON_ERROR)
            BORDER_COLOR -> borderColor.value = configRepository.getColor(BORDER_COLOR)
            SUCCESS_COLOR -> successColor.value = configRepository.getColor(SUCCESS_COLOR)
            WARNING_COLOR -> warningColor.value = configRepository.getColor(WARNING_COLOR)
            VERTEX_MAIN_COLOR -> vertexMainColor.value = configRepository.getColor(VERTEX_MAIN_COLOR)
            HOVERED_BORDER_COLOR -> hoveredBorderColor.value = configRepository.getColor(HOVERED_BORDER_COLOR)
            EDGE_MAIN_COLOR -> edgeMainColor.value = configRepository.getColor(EDGE_MAIN_COLOR)
            CANVAS_BACKGROUND_COLOR -> canvasBackgroundColor.value = configRepository.getColor(CANVAS_BACKGROUND_COLOR)
            COMMAND_LINE_BACKGROUND_COLOR -> commandLineBackgroundColor.value = configRepository.getColor(COMMAND_LINE_BACKGROUND_COLOR)

            // Main Screen
            MAIN_SCREEN_START_HEIGHT -> mainScreenStartHeight.value = configRepository.getIntValue(MAIN_SCREEN_START_HEIGHT)
            MAIN_SCREEN_START_WIDTH -> mainScreenStartWidth.value = configRepository.getIntValue(MAIN_SCREEN_START_WIDTH)
            START_WINDOW_PLACEMENT -> startWindowPlacement.value = value

            // Title Bar
            TITLE_BAR_HEIGHT -> titleBarHeight.value = configRepository.getIntValue(TITLE_BAR_HEIGHT)
            TITLE_BAR_ICON_SIZE -> titleBarIconSize.value = configRepository.getIntValue(TITLE_BAR_ICON_SIZE)

            // Command Field
            MESSAGE_OUTPUT_HEIGHT -> messageOutputHeight.value = configRepository.getIntValue(MESSAGE_OUTPUT_HEIGHT)
            MAX_COUNT_MESSAGES -> maxCountMessages.value = configRepository.getIntValue(MAX_COUNT_MESSAGES)
            COMMAND_FIELD_WIDTH -> commandFieldWidth.value = configRepository.getIntValue(COMMAND_FIELD_WIDTH)
            IS_TRANSPARENT_COMMAND_LINE -> isTransparentCommandLine.value = configRepository.getBooleanValue(IS_TRANSPARENT_COMMAND_LINE)

            // Work Area
            GRAPH_LAYOUT_HEIGHT -> graphLayoutHeight.value = configRepository.getIntValue(GRAPH_LAYOUT_HEIGHT)
            GRAPH_LAYOUT_WIDTH -> graphLayoutWidth.value = configRepository.getIntValue(GRAPH_LAYOUT_WIDTH)
            VERTEX_RADIUS -> vertexRadius.value = configRepository.getIntValue(VERTEX_RADIUS)
            VERTEX_LABEL_SIZE -> vertexLabelSize.value = configRepository.getIntValue(VERTEX_LABEL_SIZE)
            EDGE_LABEL_SIZE -> edgeLabelSize.value = configRepository.getIntValue(EDGE_LABEL_SIZE)
            EDGE_ARROW_SIZE -> edgeArrowSize.value = configRepository.getFloatValue(EDGE_ARROW_SIZE)
            EDGE_WIDTH -> edgeWidth.value = configRepository.getFloatValue(EDGE_WIDTH)
            CANVAS_DRAG_RATIO -> canvasDragRatio.value = configRepository.getFloatValue(CANVAS_DRAG_RATIO)
            CANVAS_LIMIT -> canvasLimit.value = configRepository.getIntValue(CANVAS_LIMIT)

            // Performance
            ANIMATION_DURATION -> animationDuration.value = configRepository.getIntValue(ANIMATION_DURATION)
            COMMAND_FIELD_SCROLL_DELAY -> commandFieldScrollDelay.value = configRepository.getIntValue(COMMAND_FIELD_SCROLL_DELAY)
        }
    }
}
