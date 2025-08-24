package model.dto

import androidx.compose.ui.text.input.TextFieldValue

data class RmVertexData(
    var vertexId: TextFieldValue = TextFieldValue(""),
    var errorMessage: String = "",
    var showVertexIdError: Boolean = false,
)
