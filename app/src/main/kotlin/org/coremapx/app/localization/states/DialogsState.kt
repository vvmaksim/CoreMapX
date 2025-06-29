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
        }
    }
}
