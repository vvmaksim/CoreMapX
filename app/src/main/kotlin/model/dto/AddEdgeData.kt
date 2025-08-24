package model.dto

import androidx.compose.ui.text.input.TextFieldValue

data class AddEdgeData(
    var vertexFromId: TextFieldValue = TextFieldValue(""),
    var vertexToId: TextFieldValue = TextFieldValue(""),
    var weight: TextFieldValue = TextFieldValue(""),
    var errorMessage: String = "",
    var showVertexFromIdError: Boolean = false,
    var showVertexToIdError: Boolean = false,
    var showWeightError: Boolean = false,
)
