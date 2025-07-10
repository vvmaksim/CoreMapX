package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.AnyIconDescriptionsKeys

class AnyIconDescriptionsStates: LocalizationState {
    // Logo
    val logoBackground = mutableStateOf("")
    val logoIcon = mutableStateOf("")

    override fun updateValue(key: String, value: String) {
        when(key) {
            // Logo
            AnyIconDescriptionsKeys.LOGO_BACKGROUND -> logoBackground.value = value
            AnyIconDescriptionsKeys.LOGO_ICON -> logoIcon.value = value
        }
    }
}
