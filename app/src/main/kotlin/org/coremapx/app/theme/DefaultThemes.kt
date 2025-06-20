package org.coremapx.app.theme

object DefaultThemes {
    val light =
        ThemeConfig(
            primary = "#6200EE",
            primaryVariant = "#757575",
            secondary = "#FFC107",
            secondaryVariant = "#FFA000",
            background = "#E6E6FA",
            surface = "#F5F5F5",
            error = "#F44336",
            onPrimary = "#FFFFFF",
            onSecondary = "#000000",
            onBackground = "#000000",
            onSurface = "#000000",
            onError = "#FFFFFF",
            isLight = true,
            borderColor = "#C0C0C0",
            successColor = "#4CAF50",
            warningColor = "#FFB300",
            vertexMainColor = "#F15BB5",
            hoveredBorderColor = "#6200EE",
            edgeMainColor = "#000000",
            canvasBackgroundColor = "#FFFFFF",
            commandLineBackgroundColor = "#FFFFFF",
        )

    val dark =
        ThemeConfig(
            primary = "#BB86FC",
            primaryVariant = "#3700B3",
            secondary = "#03DAC6",
            secondaryVariant = "#018786",
            background = "#1A1A1A",
            surface = "#2D2D2D",
            error = "#CF6679",
            onPrimary = "#000000",
            onSecondary = "#000000",
            onBackground = "#FFFFFF",
            onSurface = "#FFFFFF",
            onError = "#000000",
            isLight = false,
            borderColor = "#424242",
            successColor = "#4CAF50",
            warningColor = "#FFB74D",
            vertexMainColor = "#64B5F6",
            hoveredBorderColor = "#81D4FA",
            edgeMainColor = "#BDBDBD",
            canvasBackgroundColor = "#2D2D2D",
            commandLineBackgroundColor = "#363636",
        )
}
