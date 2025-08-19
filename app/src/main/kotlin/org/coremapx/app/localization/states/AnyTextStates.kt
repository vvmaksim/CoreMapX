package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.AnyTextKeys

class AnyTextStates : LocalizationState {
    // Any words
    val vertices = mutableStateOf("")
    val edges = mutableStateOf("")
    val enterCommand = mutableStateOf("")
    val messages = mutableStateOf("")

    // Save Path
    val savePathSelectDirectory = mutableStateOf("")

    override fun updateValue(
        key: String,
        value: String,
    ) {
        when (key) {
            // Any words
            AnyTextKeys.VERTICES -> vertices.value = value
            AnyTextKeys.EDGES -> edges.value = value
            AnyTextKeys.MESSAGES -> messages.value = value

            // Save Path
            AnyTextKeys.SAVE_PATH_SELECT_DIRECTORY -> savePathSelectDirectory.value = value

            // Save Path
            AnyTextKeys.ENTER_COMMAND -> enterCommand.value = value
        }
    }
}
