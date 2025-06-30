package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.DialogsKeys

class DialogsState: LocalizationState {
    // User Notification
    val userNotificationSaveError = mutableStateOf("")
    val userNotificationSaveSuccess = mutableStateOf("")
    val userNotificationSaveSuccessMessage = mutableStateOf("")

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


    override fun updateValue(key: String, value: String) {
        when(key) {
            // User Notification
            DialogsKeys.USER_NOTIFICATION_SAVE_ERROR -> userNotificationSaveError.value = value
            DialogsKeys.USER_NOTIFICATION_SAVE_SUCCESS -> userNotificationSaveSuccess.value = value
            DialogsKeys.USER_NOTIFICATION_SAVE_SUCCESS_MESSAGE -> userNotificationSaveSuccessMessage.value = value

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
        }
    }
}
