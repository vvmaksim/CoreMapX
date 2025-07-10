package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.AnyTextKeys

class AnyTextStates: LocalizationState {
    // Any words
    val vertices = mutableStateOf("")
    val edges = mutableStateOf("")

    // Save Path
    val savePathSelectDirectory = mutableStateOf("")

    // Main Work Area
    val mainWorkAreaEnterCommand = mutableStateOf("")

    override fun updateValue(key: String, value: String) {
        when(key) {
            // Any words
            AnyTextKeys.VERTICES -> vertices.value = value
            AnyTextKeys.EDGES -> edges.value = value

            // Save Path
            AnyTextKeys.SAVE_PATH_SELECT_DIRECTORY -> savePathSelectDirectory.value = value

            // Save Path
            AnyTextKeys.MAIN_WORK_AREA_ENTER_COMMAND -> mainWorkAreaEnterCommand.value = value
        }
    }
}