package org.coremapx.app.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import org.coremapx.app.config

object Theme {
    val borderColor: Color
        get() = config.getColor("borderColor")

    val cancelIconColor: Color
        get() = config.getColor("cancelIconColor")

    val warningColor: Color
        get() = config.getColor("warningColor")

    val vertexMainColor: Color
        get() = config.getColor("vertexMainColor")

    val hoveredBorderColor: Color
        get() = config.getColor("hoveredBorderColor")

    val edgeMainColor: Color
        get() = config.getColor("edgeMainColor")

    val canvasBackgroundColor: Color
        get() = config.getColor("canvasBackgroundColor")

    val commandLineBackgroundColor: Color
        get() = config.getColor("commandLineBackgroundColor")

    val colors: Colors
        get() {
            val primary = config.getColor("primary")
            val primaryVariant = config.getColor("primaryVariant")
            val secondary = config.getColor("secondary")
            val secondaryVariant = config.getColor("secondaryVariant")
            val background = config.getColor("background")
            val surface = config.getColor("surface")
            val error = config.getColor("error")
            val onPrimary = config.getColor("onPrimary")
            val onSecondary = config.getColor("onSecondary")
            val onBackground = config.getColor("onBackground")
            val onSurface = config.getColor("onSurface")
            val onError = config.getColor("onError")

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
