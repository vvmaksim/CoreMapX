package extensions

import androidx.compose.ui.graphics.Color

fun String.toColorOrNull(): Color? {
    if (!this.startsWith("#")) return null

    return try {
        val colorInt = "FF${this.removePrefix("#")}".toLong(16)
        Color(colorInt)
    } catch (ex: NumberFormatException) {
        return null
    }
}
