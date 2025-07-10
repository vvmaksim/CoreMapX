package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.AnyIconDescriptionsKeys

class AnyIconDescriptionsStates: LocalizationState {
    // Logo
    val logoBackground = mutableStateOf("")
    val logoIcon = mutableStateOf("")

    // Color Picker
    val colorPickerChooseColor = mutableStateOf("")

    // Dropdown Select
    val dropdownSelectArrow = mutableStateOf("")

    // Save Path
    val savePathOpenDirectoryDialog = mutableStateOf("")

    // Slide Menu
    val slideMenu = mutableStateOf("")

    // User Directory
    val userDirectoryOpen = mutableStateOf("")

    override fun updateValue(key: String, value: String) {
        when(key) {
            // Logo
            AnyIconDescriptionsKeys.LOGO_BACKGROUND -> logoBackground.value = value
            AnyIconDescriptionsKeys.LOGO_ICON -> logoIcon.value = value

            // Color Picker
            AnyIconDescriptionsKeys.COLOR_PICKER_CHOOSE_COLOR -> colorPickerChooseColor.value = value

            // Dropdown Select
            AnyIconDescriptionsKeys.DROPDOWN_SELECT_ARROW -> dropdownSelectArrow.value = value

            // Save Path
            AnyIconDescriptionsKeys.SAVE_PATH_OPEN_DIRECTORY_DIALOG -> savePathOpenDirectoryDialog.value = value

            // Slide Menu
            AnyIconDescriptionsKeys.SLIDE_MENU -> slideMenu.value = value

            // User Directory
            AnyIconDescriptionsKeys.USER_DIRECTORY_OPEN -> userDirectoryOpen.value = value
        }
    }
}
