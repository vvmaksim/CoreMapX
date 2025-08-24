package org.coremapx.app.localization.states

import androidx.compose.runtime.mutableStateOf
import org.coremapx.app.localization.objects.AnyTextKeys

class AnyTextStates : LocalizationState {
    // Any words
    val vertexWithNumber = mutableStateOf("")
    val vertices = mutableStateOf("")
    val edges = mutableStateOf("")
    val enterCommand = mutableStateOf("")
    val console = mutableStateOf("")
    val removeLast = mutableStateOf("")
    val addMore = mutableStateOf("")

    // Save Path
    val savePathSelectDirectory = mutableStateOf("")

    override fun updateValue(
        key: String,
        value: String,
    ) {
        when (key) {
            // Any words
            AnyTextKeys.VERTEX_WITH_NUMBER -> vertexWithNumber.value = value
            AnyTextKeys.VERTICES -> vertices.value = value
            AnyTextKeys.EDGES -> edges.value = value
            AnyTextKeys.CONSOLE -> console.value = value
            AnyTextKeys.REMOVE_LAST -> removeLast.value = value
            AnyTextKeys.ADD_MORE -> addMore.value = value

            // Save Path
            AnyTextKeys.SAVE_PATH_SELECT_DIRECTORY -> savePathSelectDirectory.value = value

            // Save Path
            AnyTextKeys.ENTER_COMMAND -> enterCommand.value = value
        }
    }
}
