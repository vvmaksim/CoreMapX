package org.coremapx.app.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import org.coremapx.app.userDirectory.config.ConfigRepository

object Theme {
    val borderColor: Color
        get() = ConfigRepository.states.borderColor.value

    val successColor: Color
        get() = ConfigRepository.states.successColor.value

    val warningColor: Color
        get() = ConfigRepository.states.warningColor.value

    val hoveredBorderColor: Color
        get() = ConfigRepository.states.hoveredBorderColor.value

    val canvasBackgroundColor: Color
        get() = ConfigRepository.states.canvasBackgroundColor.value

    val consoleBackgroundColor: Color
        get() = ConfigRepository.states.consoleBackgroundColor.value

    val colors: Colors
        get() {
            val primary = ConfigRepository.states.primary.value
            val primaryVariant = ConfigRepository.states.primaryVariant.value
            val secondary = ConfigRepository.states.secondary.value
            val secondaryVariant = ConfigRepository.states.secondaryVariant.value
            val background = ConfigRepository.states.background.value
            val surface = ConfigRepository.states.surface.value
            val error = ConfigRepository.states.error.value
            val onPrimary = ConfigRepository.states.onPrimary.value
            val onSecondary = ConfigRepository.states.onSecondary.value
            val onBackground = ConfigRepository.states.onBackground.value
            val onSurface = ConfigRepository.states.onSurface.value
            val onError = ConfigRepository.states.onError.value

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
