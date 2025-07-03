package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.DialogsKeys

class DialogsState: LocalizationState {
    // New Graph
    val newGraphTitle = mutableStateOf("")
    val newGraphTextFieldPlaceholder = mutableStateOf("")
    val newGraphTextFieldError = mutableStateOf("")
    val newGraphIsDirected = mutableStateOf("")
    val newGraphIsWeighted = mutableStateOf("")
    val newGraphCreateButton = mutableStateOf("")

    // Save Graph
    val saveGraphAsTitle = mutableStateOf("")
    val saveGraphAsSubtitle = mutableStateOf("")
    val saveGraphAsFileNameHint = mutableStateOf("")
    val saveGraphAsFileNamePlaceholder = mutableStateOf("")
    val saveGraphAsSaveDirectoryHint = mutableStateOf("")
    val saveGraphAsFileFormatHint = mutableStateOf("")
    val saveGraphAsFileExistsWarning = mutableStateOf("")
    val saveGraphAsSaveButton = mutableStateOf("")

    // User Notification
    val userNotificationSaveError = mutableStateOf("")
    val userNotificationSaveSuccess = mutableStateOf("")
    val userNotificationSaveSuccessMessage = mutableStateOf("")
    val userNotificationOkButton = mutableStateOf("")

    // Settings
    val settingsTitle = mutableStateOf("")
    val settingsSubTitle = mutableStateOf("")
    val settingsGeneralBlockName = mutableStateOf("")
    val settingsColorsBlockName = mutableStateOf("")
    val settingsMainScreenBlockName = mutableStateOf("")
    val settingsTitleBarBlockName = mutableStateOf("")
    val settingsCommandFieldBlockName = mutableStateOf("")
    val settingsWorkAreaBlockName = mutableStateOf("")
    val settingsPerformanceBlockName = mutableStateOf("")
    //// General Block
    val generalLanguage = mutableStateOf("")
    val generalTheme = mutableStateOf("")
    val generalSystemDialogTheme = mutableStateOf("")
    val generalExpanded = mutableStateOf("")
    //// Colors Block
    val colorsPrimary = mutableStateOf("")
    val colorsPrimaryVariant = mutableStateOf("")
    val colorsSecondary = mutableStateOf("")
    val colorsSecondaryVariant = mutableStateOf("")
    val colorsBackground = mutableStateOf("")
    val colorsSurface = mutableStateOf("")
    val colorsError = mutableStateOf("")
    val colorsOnPrimary = mutableStateOf("")
    val colorsOnSecondary = mutableStateOf("")
    val colorsOnBackground = mutableStateOf("")
    val colorsOnSurface = mutableStateOf("")
    val colorsOnError = mutableStateOf("")
    val colorsBorderColor = mutableStateOf("")
    val colorsSuccessColor = mutableStateOf("")
    val colorsWarningColor = mutableStateOf("")
    val colorsVertexMainColor = mutableStateOf("")
    val colorsHoveredBorderColor = mutableStateOf("")
    val colorsEdgeMainColor = mutableStateOf("")
    val colorsCanvasBackgroundColor = mutableStateOf("")
    val colorsCommandLineBackgroundColor = mutableStateOf("")
    //// Main Screen Block
    val mainScreenStartHeight = mutableStateOf("")
    val mainScreenStartWidth = mutableStateOf("")
    val mainScreenPlacement = mutableStateOf("")
    //// Title Bar Block
    val titleBarHeight = mutableStateOf("")
    val titleBarIconSize = mutableStateOf("")
    //// Command Field Block
    val commandFieldMessageOutputHeight = mutableStateOf("")
    val commandFieldMaxCountMessages = mutableStateOf("")
    val commandFieldMaxCountUserCommands = mutableStateOf("")
    val commandFieldWidth = mutableStateOf("")
    val commandFieldIsTransparent = mutableStateOf("")
    //// Work Area Block
    val workAreaGraphLayoutHeight = mutableStateOf("")
    val workAreaGraphLayoutWidth = mutableStateOf("")
    val workAreaVertexRadius = mutableStateOf("")
    val workAreaVertexLabelSize = mutableStateOf("")
    val workAreaEdgeLabelSize = mutableStateOf("")
    val workAreaEdgeArrowSize = mutableStateOf("")
    val workAreaEdgeWidth = mutableStateOf("")
    val workAreaCanvasDragRatio = mutableStateOf("")
    val workAreaCanvasLimit = mutableStateOf("")
    //// Performance Block
    val performanceAnimationDuration = mutableStateOf("")
    val performanceCommandFieldScrollDelay = mutableStateOf("")


    override fun updateValue(key: String, value: String) {
        when(key) {
            // New Graph
            DialogsKeys.NEW_GRAPH_TITLE -> newGraphTitle.value = value
            DialogsKeys.NEW_GRAPH_TEXT_FIELD_PLACEHOLDER -> newGraphTextFieldPlaceholder.value = value
            DialogsKeys.NEW_GRAPH_TEXT_FIELD_ERROR -> newGraphTextFieldError.value = value
            DialogsKeys.NEW_GRAPH_IS_DIRECTED -> newGraphIsDirected.value = value
            DialogsKeys.NEW_GRAPH_IS_WEIGHTED -> newGraphIsWeighted.value = value
            DialogsKeys.NEW_GRAPH_CREATE_BUTTON -> newGraphCreateButton.value = value

            // Save Graph
            DialogsKeys.SAVE_GRAPH_AS_TITLE -> saveGraphAsTitle.value = value
            DialogsKeys.SAVE_GRAPH_AS_SUBTITLE -> saveGraphAsSubtitle.value = value
            DialogsKeys.SAVE_GRAPH_AS_FILE_NAME_HINT -> saveGraphAsFileNameHint.value = value
            DialogsKeys.SAVE_GRAPH_AS_FILE_NAME_PLACEHOLDER -> saveGraphAsFileNamePlaceholder.value = value
            DialogsKeys.SAVE_GRAPH_AS_SAVE_DIRECTORY_HINT -> saveGraphAsSaveDirectoryHint.value = value
            DialogsKeys.SAVE_GRAPH_AS_FILE_FORMAT_HINT -> saveGraphAsFileFormatHint.value = value
            DialogsKeys.SAVE_GRAPH_AS_FILE_EXISTS_WARNING -> saveGraphAsFileExistsWarning.value = value
            DialogsKeys.SAVE_GRAPH_AS_SAVE_BUTTON -> saveGraphAsSaveButton.value = value

            // User Notification
            DialogsKeys.USER_NOTIFICATION_SAVE_ERROR -> userNotificationSaveError.value = value
            DialogsKeys.USER_NOTIFICATION_SAVE_SUCCESS -> userNotificationSaveSuccess.value = value
            DialogsKeys.USER_NOTIFICATION_SAVE_SUCCESS_MESSAGE -> userNotificationSaveSuccessMessage.value = value
            DialogsKeys.USER_NOTIFICATION_OK_BUTTON -> userNotificationOkButton.value = value

            // Settings
            DialogsKeys.SETTINGS_TITLE -> settingsTitle.value = value
            DialogsKeys.SETTINGS_SUB_TITLE -> settingsSubTitle.value = value
            DialogsKeys.SETTINGS_GENERAL_BLOCK_NAME -> settingsGeneralBlockName.value = value
            DialogsKeys.SETTINGS_COLORS_BLOCK_NAME -> settingsColorsBlockName.value = value
            DialogsKeys.SETTINGS_MAIN_SCREEN_BLOCK_NAME -> settingsMainScreenBlockName.value = value
            DialogsKeys.SETTINGS_TITLE_BAR_BLOCK_NAME -> settingsTitleBarBlockName.value = value
            DialogsKeys.SETTINGS_COMMAND_FIELD_BLOCK_NAME -> settingsCommandFieldBlockName.value = value
            DialogsKeys.SETTINGS_WORK_AREA_BLOCK_NAME -> settingsWorkAreaBlockName.value = value
            DialogsKeys.SETTINGS_PERFORMANCE_BLOCK_NAME -> settingsPerformanceBlockName.value = value
            //// General Block
            DialogsKeys.GENERAL_LANGUAGE -> generalLanguage.value = value
            DialogsKeys.GENERAL_THEME -> generalTheme.value = value
            DialogsKeys.GENERAL_SYSTEM_DIALOG_THEME -> generalSystemDialogTheme.value = value
            DialogsKeys.GENERAL_EXPANDED -> generalExpanded.value = value
            //// Colors Block
            DialogsKeys.COLORS_PRIMARY -> colorsPrimary.value = value
            DialogsKeys.COLORS_PRIMARY_VARIANT -> colorsPrimaryVariant.value = value
            DialogsKeys.COLORS_SECONDARY -> colorsSecondary.value = value
            DialogsKeys.COLORS_SECONDARY_VARIANT -> colorsSecondaryVariant.value = value
            DialogsKeys.COLORS_BACKGROUND -> colorsBackground.value = value
            DialogsKeys.COLORS_SURFACE -> colorsSurface.value = value
            DialogsKeys.COLORS_ERROR -> colorsError.value = value
            DialogsKeys.COLORS_ON_PRIMARY -> colorsOnPrimary.value = value
            DialogsKeys.COLORS_ON_SECONDARY -> colorsOnSecondary.value = value
            DialogsKeys.COLORS_ON_BACKGROUND -> colorsOnBackground.value = value
            DialogsKeys.COLORS_ON_SURFACE -> colorsOnSurface.value = value
            DialogsKeys.COLORS_ON_ERROR -> colorsOnError.value = value
            DialogsKeys.COLORS_BORDER_COLOR -> colorsBorderColor.value = value
            DialogsKeys.COLORS_SUCCESS_COLOR -> colorsSuccessColor.value = value
            DialogsKeys.COLORS_WARNING_COLOR -> colorsWarningColor.value = value
            DialogsKeys.COLORS_VERTEX_MAIN_COLOR -> colorsVertexMainColor.value = value
            DialogsKeys.COLORS_HOVERED_BORDER_COLOR -> colorsHoveredBorderColor.value = value
            DialogsKeys.COLORS_EDGE_MAIN_COLOR -> colorsEdgeMainColor.value = value
            DialogsKeys.COLORS_CANVAS_BACKGROUND_COLOR -> colorsCanvasBackgroundColor.value = value
            DialogsKeys.COLORS_COMMAND_LINE_BACKGROUND_COLOR -> colorsCommandLineBackgroundColor.value = value
            //// Main Screen Block
            DialogsKeys.MAIN_SCREEN_START_HEIGHT -> mainScreenStartHeight.value = value
            DialogsKeys.MAIN_SCREEN_START_WIDTH -> mainScreenStartWidth.value = value
            DialogsKeys.MAIN_SCREEN_PLACEMENT -> mainScreenPlacement.value = value
            //// Title Bar Block
            DialogsKeys.TITLE_BAR_HEIGHT -> titleBarHeight.value = value
            DialogsKeys.TITLE_BAR_ICON_SIZE -> titleBarIconSize.value = value
            //// Command Field Block
            DialogsKeys.COMMAND_FIELD_MESSAGE_OUTPUT_HEIGHT -> commandFieldMessageOutputHeight.value = value
            DialogsKeys.COMMAND_FIELD_MAX_COUNT_MESSAGES -> commandFieldMaxCountMessages.value = value
            DialogsKeys.COMMAND_FIELD_MAX_COUNT_USER_COMMANDS -> commandFieldMaxCountUserCommands.value = value
            DialogsKeys.COMMAND_FIELD_WIDTH -> commandFieldWidth.value = value
            DialogsKeys.COMMAND_FIELD_IS_TRANSPARENT -> commandFieldIsTransparent.value = value
            //// Command Field Block
            DialogsKeys.WORK_AREA_GRAPH_LAYOUT_HEIGHT -> workAreaGraphLayoutHeight.value = value
            DialogsKeys.WORK_AREA_GRAPH_LAYOUT_WIDTH -> workAreaGraphLayoutWidth.value = value
            DialogsKeys.WORK_AREA_VERTEX_RADIUS -> workAreaVertexRadius.value = value
            DialogsKeys.WORK_AREA_VERTEX_LABEL_SIZE -> workAreaVertexLabelSize.value = value
            DialogsKeys.WORK_AREA_EDGE_LABEL_SIZE -> workAreaEdgeLabelSize.value = value
            DialogsKeys.WORK_AREA_EDGE_ARROW_SIZE -> workAreaEdgeArrowSize.value = value
            DialogsKeys.WORK_AREA_EDGE_WIDTH -> workAreaEdgeWidth.value = value
            DialogsKeys.WORK_AREA_CANVAS_DRAG_RATIO -> workAreaCanvasDragRatio.value = value
            DialogsKeys.WORK_AREA_CANVAS_LIMIT -> workAreaCanvasLimit.value = value
            //// Title Bar Block
            DialogsKeys.PERFORMANCE_ANIMATION_DURATION -> performanceAnimationDuration.value = value
            DialogsKeys.PERFORMANCE_COMMAND_FIELD_SCROLL_DELAY -> performanceCommandFieldScrollDelay.value = value
        }
    }
}
