package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.UIKeys

class UIState(): LocalizationState {
    // Main Menu Buttons
    val mainMenuButtonNewGraph = mutableStateOf("")
    val mainMenuNewGraphIconDescription = mutableStateOf("")
    val mainMenuButtonSave = mutableStateOf("")
    val mainMenuSaveIconDescription = mutableStateOf("")
    val mainMenuButtonSaveAs = mutableStateOf("")
    val mainMenuSaveAsIconDescription = mutableStateOf("")
    val mainMenuButtonOpenGraph = mutableStateOf("")
    val mainMenuOpenGraphIconDescription = mutableStateOf("")
    val mainMenuButtonOpenRepository = mutableStateOf("")
    val mainMenuOpenRepositoryIconDescription = mutableStateOf("")
    val mainMenuButtonAnalytics = mutableStateOf("")
    val mainMenuAnalyticsIconDescription = mutableStateOf("")
    val mainMenuButtonSettings = mutableStateOf("")
    val mainMenuSettingsIconDescription = mutableStateOf("")

    // Error Strings
    val errorBasicString = mutableStateOf("")
    val errorNoDescriptionMessage = mutableStateOf("")

    override fun updateValue(key: String, value: String) {
        when(key) {
            // Main Menu Buttons
            UIKeys.MAIN_MENU_BUTTON_NEW_GRAPH -> mainMenuButtonNewGraph.value = value
            UIKeys.MAIN_MENU_NEW_GRAPH_ICON_DESCRIPTION -> mainMenuNewGraphIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_SAVE -> mainMenuButtonSave.value = value
            UIKeys.MAIN_MENU_SAVE_ICON_DESCRIPTION -> mainMenuSaveIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_SAVE_AS -> mainMenuButtonSaveAs.value = value
            UIKeys.MAIN_MENU_SAVE_AS_ICON_DESCRIPTION -> mainMenuSaveAsIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_OPEN_GRAPH -> mainMenuButtonOpenGraph.value = value
            UIKeys.MAIN_MENU_OPEN_GRAPH_ICON_DESCRIPTION -> mainMenuOpenGraphIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_OPEN_REPOSITORY -> mainMenuButtonOpenRepository.value = value
            UIKeys.MAIN_MENU_OPEN_REPOSITORY_ICON_DESCRIPTION -> mainMenuOpenRepositoryIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_ANALYTICS -> mainMenuButtonAnalytics.value = value
            UIKeys.MAIN_MENU_ANALYTICS_ICON_DESCRIPTION -> mainMenuAnalyticsIconDescription.value = value
            UIKeys.MAIN_MENU_BUTTON_SETTINGS -> mainMenuButtonSettings.value = value
            UIKeys.MAIN_MENU_SETTINGS_ICON_DESCRIPTION -> mainMenuSettingsIconDescription.value = value

            // Error Strings
            UIKeys.ERROR_BASIC_STRING -> errorBasicString.value = value
            UIKeys.ERROR_NO_DESCRIPTION_MESSAGE -> errorNoDescriptionMessage.value = value
        }
    }
}
