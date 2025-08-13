package view.appInterface.dialogElements.blocks

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
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
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
import org.coremapx.app.userDirectory.config.ConfigKeys.OTHER_PATHS_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.PRIMARY
import org.coremapx.app.userDirectory.config.ConfigKeys.PRIMARY_VARIANT
import org.coremapx.app.userDirectory.config.ConfigKeys.SECONDARY
import org.coremapx.app.userDirectory.config.ConfigKeys.SECONDARY_VARIANT
import org.coremapx.app.userDirectory.config.ConfigKeys.SHORTEST_PATH_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.SUCCESS_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.SURFACE
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_MAIN_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.WARNING_COLOR
import view.appInterface.preview.PreviewSurface
import view.appInterface.dialogElements.lines.ColorPickLine

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
    val shortestPathColor by remember { config.states.shortestPathColor }
    val otherPathsColor by remember { config.states.otherPathsColor }
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
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionPrimary.value,
                ),
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
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionPrimaryVariant.value,
                ),
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
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionSecondary.value,
                ),
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
            description = LocalizationManager.states.descriptions.descriptionSecondaryVariant.value,
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
            description = LocalizationManager.states.descriptions.descriptionBackground.value,
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
            description = LocalizationManager.states.descriptions.descriptionSurface.value,
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
            description = LocalizationManager.states.descriptions.descriptionError.value,
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
            description = LocalizationManager.states.descriptions.descriptionOnPrimary.value,
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
            description = LocalizationManager.states.descriptions.descriptionOnSecondary.value,
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
            description = LocalizationManager.states.descriptions.descriptionOnBackground.value,
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
            description = LocalizationManager.states.descriptions.descriptionOnSurface.value,
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
            description = LocalizationManager.states.descriptions.descriptionOnError.value,
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
            description = LocalizationManager.states.descriptions.descriptionBorderColor.value,
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
            description = LocalizationManager.states.descriptions.descriptionSuccessColor.value,
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
            description = LocalizationManager.states.descriptions.descriptionWarningColor.value,
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
            description = LocalizationManager.states.descriptions.descriptionVertexMainColor.value,
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
            description = LocalizationManager.states.descriptions.descriptionHoveredBorderColor.value,
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
            description = LocalizationManager.states.descriptions.descriptionEdgeMainColor.value,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = shortestPathColor,
            onColorSelected = {
                config.setValue(SHORTEST_PATH_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsShortestPathColor.value,
            description = LocalizationManager.states.descriptions.descriptionShortestPathColor.value,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = otherPathsColor,
            onColorSelected = {
                config.setValue(OTHER_PATHS_COLOR, it)
                config.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsOtherPathsColor.value,
            description = LocalizationManager.states.descriptions.descriptionOtherPathsColor.value,
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
            description = LocalizationManager.states.descriptions.descriptionCanvasBackgroundColor.value,
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
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionCommandLineBackgroundColor.value,
                ),
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
