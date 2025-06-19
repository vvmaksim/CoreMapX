package view.appInterface.settingsElements.blocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.settingsElements.lines.ColorPickLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun ColorsBlock() {
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
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewColorsBlock() {
    AppTheme {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            ColorsBlock()
        }
    }
}
