package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.DialogsKeys

class DialogsState: LocalizationState {
    // User Notification
    val userNotificationSaveError = mutableStateOf("")
    val userNotificationSaveSuccess = mutableStateOf("")
    val userNotificationSaveSuccessMessage = mutableStateOf("")

    override fun updateValue(key: String, value: String) {
        when(key) {
            // User Notification
            DialogsKeys.USER_NOTIFICATION_SAVE_ERROR -> userNotificationSaveError.value = value
            DialogsKeys.USER_NOTIFICATION_SAVE_SUCCESS -> userNotificationSaveSuccess.value = value
            DialogsKeys.USER_NOTIFICATION_SAVE_SUCCESS_MESSAGE -> userNotificationSaveSuccessMessage.value = value
        }
    }
}
