package extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.toHexString(): String = String.format("#%06X", toArgb() and 0xFFFFFF)
