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
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigDescriptions
import org.coremapx.app.userDirectory.config.ConfigKeys.BACKGROUND
import org.coremapx.app.userDirectory.config.ConfigKeys.BORDER_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.COMMAND_LINE_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_MAIN_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.ERROR
import org.coremapx.app.userDirectory.config.ConfigKeys.HOVERED_BORDER_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_BACKGROUND
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_ERROR
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_PRIMARY
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_SECONDARY
import org.coremapx.app.userDirectory.config.ConfigKeys.ON_SURFACE
import org.coremapx.app.userDirectory.config.ConfigKeys.PRIMARY
import org.coremapx.app.userDirectory.config.ConfigKeys.PRIMARY_VARIANT
import org.coremapx.app.userDirectory.config.ConfigKeys.SECONDARY
import org.coremapx.app.userDirectory.config.ConfigKeys.SECONDARY_VARIANT
import org.coremapx.app.userDirectory.config.ConfigKeys.SUCCESS_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.SURFACE
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_MAIN_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.WARNING_COLOR
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
                config.setValue(PRIMARY, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsPrimary.value,
            description = ConfigDescriptions.PRIMARY,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = primaryVariant,
            onColorSelected = {
                config.setValue(PRIMARY_VARIANT, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsPrimaryVariant.value,
            description = ConfigDescriptions.PRIMARY_VARIANT,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = secondary,
            onColorSelected = {
                config.setValue(SECONDARY, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsSecondary.value,
            description = ConfigDescriptions.SECONDARY,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = secondaryVariant,
            onColorSelected = {
                config.setValue(SECONDARY_VARIANT, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsSecondaryVariant.value,
            description = ConfigDescriptions.SECONDARY_VARIANT,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = background,
            onColorSelected = {
                config.setValue(BACKGROUND, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsBackground.value,
            description = ConfigDescriptions.BACKGROUND,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = surface,
            onColorSelected = {
                config.setValue(SURFACE, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsSurface.value,
            description = ConfigDescriptions.SURFACE,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = error,
            onColorSelected = {
                config.setValue(ERROR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsError.value,
            description = ConfigDescriptions.ERROR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onPrimary,
            onColorSelected = {
                config.setValue(ON_PRIMARY, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsOnPrimary.value,
            description = ConfigDescriptions.ON_PRIMARY,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onSecondary,
            onColorSelected = {
                config.setValue(ON_SECONDARY, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsOnSecondary.value,
            description = ConfigDescriptions.ON_SECONDARY,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onBackground,
            onColorSelected = {
                config.setValue(ON_BACKGROUND, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsOnBackground.value,
            description = ConfigDescriptions.ON_BACKGROUND,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onSurface,
            onColorSelected = {
                config.setValue(ON_SURFACE, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsOnSurface.value,
            description = ConfigDescriptions.ON_SURFACE,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = onError,
            onColorSelected = {
                config.setValue(ON_ERROR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsOnError.value,
            description = ConfigDescriptions.ON_ERROR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = borderColor,
            onColorSelected = {
                config.setValue(BORDER_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsBorderColor.value,
            description = ConfigDescriptions.BORDER_COLOR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = successColor,
            onColorSelected = {
                config.setValue(SUCCESS_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsSuccessColor.value,
            description = ConfigDescriptions.SUCCESS_COLOR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = warningColor,
            onColorSelected = {
                config.setValue(WARNING_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsWarningColor.value,
            description = ConfigDescriptions.WARNING_COLOR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = vertexMainColor,
            onColorSelected = {
                config.setValue(VERTEX_MAIN_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsVertexMainColor.value,
            description = ConfigDescriptions.VERTEX_MAIN_COLOR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = hoveredBorderColor,
            onColorSelected = {
                config.setValue(HOVERED_BORDER_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsHoveredBorderColor.value,
            description = ConfigDescriptions.HOVERED_BORDER_COLOR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = edgeMainColor,
            onColorSelected = {
                config.setValue(EDGE_MAIN_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsEdgeMainColor.value,
            description = ConfigDescriptions.EDGE_MAIN_COLOR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = canvasBackgroundColor,
            onColorSelected = {
                config.setValue(CANVAS_BACKGROUND_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsCanvasBackgroundColor.value,
            description = ConfigDescriptions.CANVAS_BACKGROUND_COLOR,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = commandLineBackgroundColor,
            onColorSelected = {
                config.setValue(COMMAND_LINE_BACKGROUND_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsCommandLineBackgroundColor.value,
            description = ConfigDescriptions.COMMAND_LINE_BACKGROUND_COLOR,
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
