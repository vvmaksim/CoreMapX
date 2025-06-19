package view.appInterface.settingsElements.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface
import view.appInterface.settingsElements.lines.ColorPickLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun ColorsBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val primary by remember { config.states.primary }
    val primaryVariant by remember { config.states.primaryVariant }
    val secondary by remember { config.states.secondary }
    val secondaryVariant by remember { config.states.secondaryVariant }
    val background by remember { config.states.background }
    val surface by remember { config.states.surface }
    val error by remember { config.states.error }
    val onPrimary by remember { config.states.onPrimary }
    val onSecondary by remember { config.states.onSecondary }
    val onBackground by remember { config.states.onBackground }
    val onSurface by remember { config.states.onSurface }
    val onError by remember { config.states.onError }
    val borderColor by remember { config.states.borderColor }
    val successColor by remember { config.states.successColor }
    val warningColor by remember { config.states.warningColor }
    val vertexMainColor by remember { config.states.vertexMainColor }
    val hoveredBorderColor by remember { config.states.hoveredBorderColor }
    val edgeMainColor by remember { config.states.edgeMainColor }
    val canvasBackgroundColor by remember { config.states.canvasBackgroundColor }
    val commandLineBackgroundColor by remember { config.states.commandLineBackgroundColor }

    Column {
        ColorPickLine(
            selectedColor = primary,
            onColorSelected = {
                config.setValue("primary", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Primary",
            description =
                """
                The main color of the interface.
                It is used for key controls, buttons, highlighting active elements, etc.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = primaryVariant,
            onColorSelected = {
                config.setValue("primaryVariant", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Primary Variant",
            description =
                """
                A variant of the main color, usually slightly darker or lighter.
                It is used for shades, shadows, highlights, or minor accents related to the main color.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = secondary,
            onColorSelected = {
                config.setValue("secondary", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Secondary",
            description =
                """
                The secondary color of the interface.
                It is used to highlight less important elements, minor buttons, icons, links, etc.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = secondaryVariant,
            onColorSelected = {
                config.setValue("secondaryVariant", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Secondary Variant",
            description =
                """
                A secondary color option, similar to primaryVariant, for shades, shadows, and additional accents associated with secondary.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = background,
            onColorSelected = {
                config.setValue("background", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Background",
            description =
                """
                The background color of the entire application or large areas.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = surface,
            onColorSelected = {
                config.setValue("surface", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Surface",
            description =
                """
                Surface color: cards, panels, pop-ups, dialogs, and other elements that "lie" on the background.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = error,
            onColorSelected = {
                config.setValue("error", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Error",
            description =
                """
                The color for displaying errors: error text, frames, icons, indicators, etc.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onPrimary,
            onColorSelected = {
                config.setValue("onPrimary", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "On Primary",
            description =
                """
                The color of the content (text, icons) that is placed on top of the primary. Usually contrasting.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onSecondary,
            onColorSelected = {
                config.setValue("onSecondary", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "On Secondary",
            description =
                """
                The color of the content that is placed on top of secondary.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onBackground,
            onColorSelected = {
                config.setValue("onBackground", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "On Background",
            description =
                """
                The color of the content that is placed on top of background.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onSurface,
            onColorSelected = {
                config.setValue("onSurface", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "On Surface",
            description =
                """
                The color of the content that is placed on top of surface.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onError,
            onColorSelected = {
                config.setValue("onError", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "On Error",
            description =
                """
                The color of the content that is placed on top of error.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = borderColor,
            onColorSelected = {
                config.setValue("borderColor", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Border Color",
            description =
                """
                The color of the frames for various interface elements: text fields, buttons, cards, etc.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = successColor,
            onColorSelected = {
                config.setValue("successColor", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Success Color",
            description =
                """
                A color to indicate successful actions: confirmations, successful notifications, etc.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = warningColor,
            onColorSelected = {
                config.setValue("warningColor", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Warning Color",
            description =
                """
                Color for warnings: yellow icons, frames, notifications of potential problems.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = vertexMainColor,
            onColorSelected = {
                config.setValue("vertexMainColor", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Vertex Main Color",
            description =
                """
                The primary color for displaying vertices.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = hoveredBorderColor,
            onColorSelected = {
                config.setValue("hoveredBorderColor", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Hovered Border Color",
            description =
                """
                The color of the border when hovering over the element.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = edgeMainColor,
            onColorSelected = {
                config.setValue("edgeMainColor", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Edge Main Color",
            description =
                """
                The primary color for displaying edges between vertices on the graph.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = canvasBackgroundColor,
            onColorSelected = {
                config.setValue("canvasBackgroundColor", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Canvas Background Color",
            description =
                """
                The background color for graph visualization.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = commandLineBackgroundColor,
            onColorSelected = {
                config.setValue("commandLineBackgroundColor", it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = "Command Line Background Color",
            description =
                """
                The background color for the command line.

                This color is applied if the `isTransparentCommandLine` parameter is disabled.
                """.trimIndent(),
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewColorsBlock() {
    AppTheme {
        PreviewSurface(content = { ColorsBlock() })
    }
}
