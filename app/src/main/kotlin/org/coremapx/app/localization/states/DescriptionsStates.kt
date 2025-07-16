package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.DescriptionsKeys

class DescriptionsStates : LocalizationState {
    // Settings
    // // General Block
    val descriptionLanguage = mutableStateOf("")
    val descriptionTheme = mutableStateOf("")
    val descriptionSystemDialogTheme = mutableStateOf("")
    val descriptionIsExpandedSettings = mutableStateOf("")

    // // Colors Block
    val descriptionPrimary = mutableStateOf("")
    val descriptionPrimaryVariant = mutableStateOf("")
    val descriptionSecondary = mutableStateOf("")
    val descriptionSecondaryVariant = mutableStateOf("")
    val descriptionBackground = mutableStateOf("")
    val descriptionSurface = mutableStateOf("")
    val descriptionError = mutableStateOf("")
    val descriptionOnPrimary = mutableStateOf("")
    val descriptionOnSecondary = mutableStateOf("")
    val descriptionOnBackground = mutableStateOf("")
    val descriptionOnSurface = mutableStateOf("")
    val descriptionOnError = mutableStateOf("")
    val descriptionBorderColor = mutableStateOf("")
    val descriptionSuccessColor = mutableStateOf("")
    val descriptionWarningColor = mutableStateOf("")
    val descriptionVertexMainColor = mutableStateOf("")
    val descriptionHoveredBorderColor = mutableStateOf("")
    val descriptionEdgeMainColor = mutableStateOf("")
    val descriptionCanvasBackgroundColor = mutableStateOf("")
    val descriptionCommandLineBackgroundColor = mutableStateOf("")

    // // Main Screen Block
    val descriptionMainScreenStartHeight = mutableStateOf("")
    val descriptionMainScreenStartWidth = mutableStateOf("")
    val descriptionStartWindowPlacement = mutableStateOf("")

    // // Title Bar Block
    val descriptionTitleBarHeight = mutableStateOf("")
    val descriptionTitleBarIconSize = mutableStateOf("")

    // // Command Field Block
    val descriptionMessageOutputHeight = mutableStateOf("")
    val descriptionMaxCountMessages = mutableStateOf("")
    val descriptionMaxCountUserCommands = mutableStateOf("")
    val descriptionCommandFieldWidth = mutableStateOf("")
    val descriptionIsTransparentCommandLine = mutableStateOf("")

    // // Work Area Block
    val descriptionGraphLayoutHeight = mutableStateOf("")
    val descriptionGraphLayoutWidth = mutableStateOf("")
    val descriptionVertexRadius = mutableStateOf("")
    val descriptionVertexLabelSize = mutableStateOf("")
    val descriptionEdgeLabelSize = mutableStateOf("")
    val descriptionEdgeArrowSize = mutableStateOf("")
    val descriptionEdgeWidth = mutableStateOf("")
    val descriptionCanvasDragRatio = mutableStateOf("")
    val descriptionCanvasLimit = mutableStateOf("")

    // // Performance Block
    val descriptionAnimationDuration = mutableStateOf("")
    val descriptionCommandFieldScrollDelay = mutableStateOf("")

    // Top Menu
    val descriptionTopMenuFindPathStartVertex = mutableStateOf("")
    val descriptionTopMenuFindPathEndVertex = mutableStateOf("")
    val descriptionTopMenuFindPathMaxPaths = mutableStateOf("")
    val descriptionTopMenuFindPathFindStrategy = mutableStateOf("")

    override fun updateValue(
        key: String,
        value: String,
    ) {
        when (key) {
            // Settings
            // // General Block
            DescriptionsKeys.LANGUAGE -> descriptionLanguage.value = value
            DescriptionsKeys.THEME -> descriptionTheme.value = value
            DescriptionsKeys.SYSTEM_DIALOG_THEME -> descriptionSystemDialogTheme.value = value
            DescriptionsKeys.IS_EXPANDED_SETTINGS -> descriptionIsExpandedSettings.value = value
            // // Colors Block
            DescriptionsKeys.PRIMARY -> descriptionPrimary.value = value
            DescriptionsKeys.PRIMARY_VARIANT -> descriptionPrimaryVariant.value = value
            DescriptionsKeys.SECONDARY -> descriptionSecondary.value = value
            DescriptionsKeys.SECONDARY_VARIANT -> descriptionSecondaryVariant.value = value
            DescriptionsKeys.BACKGROUND -> descriptionBackground.value = value
            DescriptionsKeys.SURFACE -> descriptionSurface.value = value
            DescriptionsKeys.ERROR -> descriptionError.value = value
            DescriptionsKeys.ON_PRIMARY -> descriptionOnPrimary.value = value
            DescriptionsKeys.ON_SECONDARY -> descriptionOnSecondary.value = value
            DescriptionsKeys.ON_BACKGROUND -> descriptionOnBackground.value = value
            DescriptionsKeys.ON_SURFACE -> descriptionOnSurface.value = value
            DescriptionsKeys.ON_ERROR -> descriptionOnError.value = value
            DescriptionsKeys.BORDER_COLOR -> descriptionBorderColor.value = value
            DescriptionsKeys.SUCCESS_COLOR -> descriptionSuccessColor.value = value
            DescriptionsKeys.WARNING_COLOR -> descriptionWarningColor.value = value
            DescriptionsKeys.VERTEX_MAIN_COLOR -> descriptionVertexMainColor.value = value
            DescriptionsKeys.HOVERED_BORDER_COLOR -> descriptionHoveredBorderColor.value = value
            DescriptionsKeys.EDGE_MAIN_COLOR -> descriptionEdgeMainColor.value = value
            DescriptionsKeys.CANVAS_BACKGROUND_COLOR -> descriptionCanvasBackgroundColor.value = value
            DescriptionsKeys.COMMAND_LINE_BACKGROUND_COLOR -> descriptionCommandLineBackgroundColor.value = value
            // // Main Screen Block
            DescriptionsKeys.MAIN_SCREEN_START_HEIGHT -> descriptionMainScreenStartHeight.value = value
            DescriptionsKeys.MAIN_SCREEN_START_WIDTH -> descriptionMainScreenStartWidth.value = value
            DescriptionsKeys.START_WINDOW_PLACEMENT -> descriptionStartWindowPlacement.value = value
            // // Title Bar Block
            DescriptionsKeys.TITLE_BAR_HEIGHT -> descriptionTitleBarHeight.value = value
            DescriptionsKeys.TITLE_BAR_ICON_SIZE -> descriptionTitleBarIconSize.value = value
            // // Command Field Block
            DescriptionsKeys.MESSAGE_OUTPUT_HEIGHT -> descriptionMessageOutputHeight.value = value
            DescriptionsKeys.MAX_COUNT_MESSAGES -> descriptionMaxCountMessages.value = value
            DescriptionsKeys.MAX_COUNT_USER_COMMANDS -> descriptionMaxCountUserCommands.value = value
            DescriptionsKeys.COMMAND_FIELD_WIDTH -> descriptionCommandFieldWidth.value = value
            DescriptionsKeys.IS_TRANSPARENT_COMMAND_LINE -> descriptionIsTransparentCommandLine.value = value
            // // Work Area Block
            DescriptionsKeys.GRAPH_LAYOUT_HEIGHT -> descriptionGraphLayoutHeight.value = value
            DescriptionsKeys.GRAPH_LAYOUT_WIDTH -> descriptionGraphLayoutWidth.value = value
            DescriptionsKeys.VERTEX_RADIUS -> descriptionVertexRadius.value = value
            DescriptionsKeys.VERTEX_LABEL_SIZE -> descriptionVertexLabelSize.value = value
            DescriptionsKeys.EDGE_LABEL_SIZE -> descriptionEdgeLabelSize.value = value
            DescriptionsKeys.EDGE_ARROW_SIZE -> descriptionEdgeArrowSize.value = value
            DescriptionsKeys.EDGE_WIDTH -> descriptionEdgeWidth.value = value
            DescriptionsKeys.CANVAS_DRAG_RATIO -> descriptionCanvasDragRatio.value = value
            DescriptionsKeys.CANVAS_LIMIT -> descriptionCanvasLimit.value = value
            // // Performance Block
            DescriptionsKeys.ANIMATION_DURATION -> descriptionAnimationDuration.value = value
            DescriptionsKeys.COMMAND_FIELD_SCROLL_DELAY -> descriptionCommandFieldScrollDelay.value = value

            // Top Menu
            DescriptionsKeys.TOP_MENU_FIND_PATH_START_VERTEX -> descriptionTopMenuFindPathStartVertex.value = value
            DescriptionsKeys.TOP_MENU_FIND_PATH_END_VERTEX -> descriptionTopMenuFindPathEndVertex.value = value
            DescriptionsKeys.TOP_MENU_FIND_PATH_MAX_PATHS -> descriptionTopMenuFindPathMaxPaths.value = value
            DescriptionsKeys.TOP_MENU_FIND_PATH_FIND_STRATEGY -> descriptionTopMenuFindPathFindStrategy.value = value
        }
    }
}
