package org.coremapx.app.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import org.coremapx.app.config

object Theme {
    val borderColor: Color
        get() = config.states.borderColor.value

    val successColor: Color
        get() = config.states.successColor.value

    val warningColor: Color
        get() = config.states.warningColor.value

    val hoveredBorderColor: Color
        get() = config.states.hoveredBorderColor.value

    val canvasBackgroundColor: Color
        get() = config.states.canvasBackgroundColor.value

    val consoleBackgroundColor: Color
        get() = config.states.consoleBackgroundColor.value

    val colors: Colors
        get() {
            val primary = config.states.primary.value
            val primaryVariant = config.states.primaryVariant.value
            val secondary = config.states.secondary.value
            val secondaryVariant = config.states.secondaryVariant.value
            val background = config.states.background.value
            val surface = config.states.surface.value
            val error = config.states.error.value
            val onPrimary = config.states.onPrimary.value
            val onSecondary = config.states.onSecondary.value
            val onBackground = config.states.onBackground.value
            val onSurface = config.states.onSurface.value
            val onError = config.states.onError.value

            return Colors(
                primary = primary,
                primaryVariant = primaryVariant,
                secondary = secondary,
                secondaryVariant = secondaryVariant,
                background = background,
                surface = surface,
                error = error,
                onPrimary = onPrimary,
                onSecondary = onSecondary,
                onBackground = onBackground,
                onSurface = onSurface,
                onError = onError,
                isLight = true,
            )
        }
}
