package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.AnyTextKeys

class AnyTextStates: LocalizationState {
    // Save Path
    val savePathSelectDirectory = mutableStateOf("")

    override fun updateValue(key: String, value: String) {
        when(key) {
            // Save Path
            AnyTextKeys.SAVE_PATH_SELECT_DIRECTORY -> savePathSelectDirectory.value = value
        }
    }
}