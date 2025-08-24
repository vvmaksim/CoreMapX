package model.dto

import androidx.compose.ui.text.input.TextFieldValue

data class AddVertexData(
    var vertexId: TextFieldValue = TextFieldValue(""),
    var vertexLabel: TextFieldValue = TextFieldValue(""),
    var errorMessage: String = "",
    var showVertexIdError: Boolean = false,
    var showVertexLabelError: Boolean = false,
)
