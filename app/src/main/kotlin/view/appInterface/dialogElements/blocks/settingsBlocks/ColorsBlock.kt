package view.appInterface.dialogElements.blocks.settingsBlocks

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
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigKeys.BACKGROUND
import org.coremapx.app.userDirectory.config.ConfigKeys.BORDER_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.config.ConfigKeys.CONSOLE_BACKGROUND_COLOR
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
import org.coremapx.app.userDirectory.config.ConfigRepository
import view.appInterface.dialogElements.lines.ColorPickLine
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun ColorsBlock(isExpandedSettings: Boolean = ConfigRepository.states.isExpandedSettings.value) {
    val primary by remember { ConfigRepository.states.primary }
    val primaryVariant by remember { ConfigRepository.states.primaryVariant }
    val secondary by remember { ConfigRepository.states.secondary }
    val secondaryVariant by remember { ConfigRepository.states.secondaryVariant }
    val background by remember { ConfigRepository.states.background }
    val surface by remember { ConfigRepository.states.surface }
    val error by remember { ConfigRepository.states.error }
    val onPrimary by remember { ConfigRepository.states.onPrimary }
    val onSecondary by remember { ConfigRepository.states.onSecondary }
    val onBackground by remember { ConfigRepository.states.onBackground }
    val onSurface by remember { ConfigRepository.states.onSurface }
    val onError by remember { ConfigRepository.states.onError }
    val borderColor by remember { ConfigRepository.states.borderColor }
    val successColor by remember { ConfigRepository.states.successColor }
    val warningColor by remember { ConfigRepository.states.warningColor }
    val vertexMainColor by remember { ConfigRepository.states.vertexMainColor }
    val hoveredBorderColor by remember { ConfigRepository.states.hoveredBorderColor }
    val edgeMainColor by remember { ConfigRepository.states.edgeMainColor }
    val shortestPathColor by remember { ConfigRepository.states.shortestPathColor }
    val otherPathsColor by remember { ConfigRepository.states.otherPathsColor }
    val canvasBackgroundColor by remember { ConfigRepository.states.canvasBackgroundColor }
    val consoleBackgroundColor by remember { ConfigRepository.states.consoleBackgroundColor }

    Column {
        ColorPickLine(
            selectedColor = primary,
            onColorSelected = {
                ConfigRepository.setValue(PRIMARY, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(PRIMARY_VARIANT, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(SECONDARY, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(SECONDARY_VARIANT, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(BACKGROUND, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(SURFACE, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(ERROR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(ON_PRIMARY, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(ON_SECONDARY, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(ON_BACKGROUND, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(ON_SURFACE, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(ON_ERROR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(BORDER_COLOR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(SUCCESS_COLOR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(WARNING_COLOR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(VERTEX_MAIN_COLOR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(HOVERED_BORDER_COLOR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(EDGE_MAIN_COLOR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(SHORTEST_PATH_COLOR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(OTHER_PATHS_COLOR, it)
                ConfigRepository.setThemeOnCustom()
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
                ConfigRepository.setValue(CANVAS_BACKGROUND_COLOR, it)
                ConfigRepository.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsCanvasBackgroundColor.value,
            description = LocalizationManager.states.descriptions.descriptionCanvasBackgroundColor.value,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        ColorPickLine(
            selectedColor = consoleBackgroundColor,
            onColorSelected = {
                ConfigRepository.setValue(CONSOLE_BACKGROUND_COLOR, it)
                ConfigRepository.setThemeOnCustom()
            },
            modifier = Modifier.fillMaxWidth(),
            title = LocalizationManager.states.dialogs.colorsConsoleBackgroundColor.value,
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionConsoleBackgroundColor.value,
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
