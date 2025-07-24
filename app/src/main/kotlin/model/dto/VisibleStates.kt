package model.dto

import androidx.compose.runtime.State

data class VisibleStates(
    val verticesLabels: State<Boolean>,
    val edgesWeights: State<Boolean>,
    val edgesIds: State<Boolean>,
)
