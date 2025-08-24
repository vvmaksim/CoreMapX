package model.dto

import androidx.compose.ui.text.input.TextFieldValue

data class RmEdgeData(
    var vertexFromId: TextFieldValue = TextFieldValue(""),
    var vertexToId: TextFieldValue = TextFieldValue(""),
    var errorMessage: String = "",
    var showVertexFromIdError: Boolean = false,
    var showVertexToIdError: Boolean = false,
)
