package extensions

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import org.coremapx.app.theme.Theme

val Colors.border: Color
    get() = Theme.borderColor

val Colors.success: Color
    get() = Theme.successColor

val Colors.warning: Color
    get() = Theme.warningColor

val Colors.hoveredBorder: Color
    get() = Theme.hoveredBorderColor

val Colors.canvasBackground: Color
    get() = Theme.canvasBackgroundColor

val Colors.consoleBackground: Color
    get() = Theme.consoleBackgroundColor
