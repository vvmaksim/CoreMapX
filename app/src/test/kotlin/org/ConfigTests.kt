package org

import androidx.compose.ui.graphics.Color
import extensions.toColorOrNull
import model.result.Result
import org.coremapx.app.userDirectory.UserDirectory
import org.coremapx.app.userDirectory.config.ConfigKeys
import org.coremapx.app.userDirectory.config.ConfigRepository
import org.coremapx.app.userDirectory.config.ConfigValidator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ConfigTests {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupConfig() {
            UserDirectory.initTestEnvironment(
                testPath = System.getProperty("java.io.tmpdir") + "/coremapx_config_test_" + System.currentTimeMillis(),
            )
        }
    }

    @Test
    fun `get and set all config states`() {
        // Check default values
        assertEquals("en", ConfigRepository.states.language.value)
        assertEquals("light", ConfigRepository.states.theme.value)
        assertEquals("light", ConfigRepository.states.systemDialogTheme.value)
        assertEquals(true, ConfigRepository.states.isExpandedSettings.value)
        assertEquals(Color(0xFF6200EE), ConfigRepository.states.primary.value)
        assertEquals(Color(0xFF757575), ConfigRepository.states.primaryVariant.value)
        assertEquals(Color(0xFFFFC107), ConfigRepository.states.secondary.value)
        assertEquals(Color(0xFFFFA000), ConfigRepository.states.secondaryVariant.value)
        assertEquals(Color(0xFFE6E6FA), ConfigRepository.states.background.value)
        assertEquals(Color(0xFFF5F5F5), ConfigRepository.states.surface.value)
        assertEquals(Color(0xFFF44336), ConfigRepository.states.error.value)
        assertEquals(Color(0xFFFFFFFF), ConfigRepository.states.onPrimary.value)
        assertEquals(Color(0xFF000000), ConfigRepository.states.onSecondary.value)
        assertEquals(Color(0xFF000000), ConfigRepository.states.onBackground.value)
        assertEquals(Color(0xFF000000), ConfigRepository.states.onSurface.value)
        assertEquals(Color(0xFFFFFFFF), ConfigRepository.states.onError.value)
        assertEquals(Color(0xFFC0C0C0), ConfigRepository.states.borderColor.value)
        assertEquals(Color(0xFF4CAF50), ConfigRepository.states.successColor.value)
        assertEquals(Color(0xFFFFB300), ConfigRepository.states.warningColor.value)
        assertEquals(Color(0xFFF15BB5), ConfigRepository.states.vertexMainColor.value)
        assertEquals(Color(0xFF6200EE), ConfigRepository.states.hoveredBorderColor.value)
        assertEquals(Color(0xFF000000), ConfigRepository.states.edgeMainColor.value)
        assertEquals(Color(0xFF4CAF50), ConfigRepository.states.shortestPathColor.value)
        assertEquals(Color(0xFFFF9800), ConfigRepository.states.otherPathsColor.value)
        assertEquals(Color(0xFFFFFFFF), ConfigRepository.states.canvasBackgroundColor.value)
        assertEquals(Color(0xFFFFFFFF), ConfigRepository.states.consoleBackgroundColor.value)
        assertEquals(720, ConfigRepository.states.mainScreenStartHeight.value)
        assertEquals(1280, ConfigRepository.states.mainScreenStartWidth.value)
        assertEquals("Maximized", ConfigRepository.states.startWindowPlacement.value)
        assertEquals(40, ConfigRepository.states.titleBarHeight.value)
        assertEquals(20, ConfigRepository.states.titleBarIconSize.value)
        assertEquals(325, ConfigRepository.states.consoleHeight.value)
        assertEquals(100, ConfigRepository.states.maxCountMessages.value)
        assertEquals(200, ConfigRepository.states.maxCountUserCommands.value)
        assertEquals(750, ConfigRepository.states.consoleWidth.value)
        assertEquals(false, ConfigRepository.states.isTransparentConsoleBlock.value)
        assertEquals(10000, ConfigRepository.states.graphLayoutHeight.value)
        assertEquals(10000, ConfigRepository.states.graphLayoutWidth.value)
        assertEquals(15, ConfigRepository.states.vertexRadius.value)
        assertEquals(14, ConfigRepository.states.vertexLabelSize.value)
        assertEquals(14, ConfigRepository.states.edgeLabelSize.value)
        assertEquals(10.0F, ConfigRepository.states.edgeArrowSize.value)
        assertEquals(2.0F, ConfigRepository.states.edgeWidth.value)
        assertEquals(1.0F, ConfigRepository.states.canvasDragRatio.value)
        assertEquals(10000, ConfigRepository.states.canvasLimit.value)
        assertEquals(300, ConfigRepository.states.animationDuration.value)

        // Set all values
        val color = "#525252"
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.LANGUAGE, "ru"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.THEME, "dark"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.SYSTEM_DIALOG_THEME, "dark"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.IS_EXPANDED_SETTINGS, "false"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.PRIMARY, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.PRIMARY_VARIANT, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.SECONDARY, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.SECONDARY_VARIANT, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.BACKGROUND, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.SURFACE, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.ERROR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.ON_PRIMARY, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.ON_SECONDARY, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.ON_BACKGROUND, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.ON_SURFACE, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.ON_ERROR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.BORDER_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.SUCCESS_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.WARNING_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.VERTEX_MAIN_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.HOVERED_BORDER_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.EDGE_MAIN_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.SHORTEST_PATH_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.OTHER_PATHS_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.CANVAS_BACKGROUND_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.CONSOLE_BACKGROUND_COLOR, color))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.MAIN_SCREEN_START_HEIGHT, "1720"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.MAIN_SCREEN_START_WIDTH, "2280"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.START_WINDOW_PLACEMENT, "Floating"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.TITLE_BAR_HEIGHT, "52"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.TITLE_BAR_ICON_SIZE, "52"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.CONSOLE_HEIGHT, "252"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.MAX_COUNT_MESSAGES, "152"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.MAX_COUNT_USER_COMMANDS, "252"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.CONSOLE_WIDTH, "752"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.IS_TRANSPARENT_CONSOLE, "true"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.GRAPH_LAYOUT_HEIGHT, "5252"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.GRAPH_LAYOUT_WIDTH, "5252"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.VERTEX_RADIUS, "52"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.VERTEX_LABEL_SIZE, "52"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.EDGE_LABEL_SIZE, "52"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.EDGE_ARROW_SIZE, "52.0"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.EDGE_WIDTH, "14.0"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.CANVAS_DRAG_RATIO, "5.5"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.CANVAS_LIMIT, "5252"))
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.ANIMATION_DURATION, "520"))

        // Check new values
        assertEquals("ru", ConfigRepository.states.language.value)
        assertEquals("dark", ConfigRepository.states.theme.value)
        assertEquals("dark", ConfigRepository.states.systemDialogTheme.value)
        assertEquals(false, ConfigRepository.states.isExpandedSettings.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.primary.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.primaryVariant.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.secondary.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.secondaryVariant.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.background.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.surface.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.error.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.onPrimary.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.onSecondary.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.onBackground.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.onSurface.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.onError.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.borderColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.successColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.warningColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.vertexMainColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.hoveredBorderColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.edgeMainColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.shortestPathColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.otherPathsColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.canvasBackgroundColor.value)
        assertEquals(color.toColorOrNull(), ConfigRepository.states.consoleBackgroundColor.value)
        assertEquals(1720, ConfigRepository.states.mainScreenStartHeight.value)
        assertEquals(2280, ConfigRepository.states.mainScreenStartWidth.value)
        assertEquals("Floating", ConfigRepository.states.startWindowPlacement.value)
        assertEquals(52, ConfigRepository.states.titleBarHeight.value)
        assertEquals(52, ConfigRepository.states.titleBarIconSize.value)
        assertEquals(252, ConfigRepository.states.consoleHeight.value)
        assertEquals(152, ConfigRepository.states.maxCountMessages.value)
        assertEquals(252, ConfigRepository.states.maxCountUserCommands.value)
        assertEquals(752, ConfigRepository.states.consoleWidth.value)
        assertEquals(true, ConfigRepository.states.isTransparentConsoleBlock.value)
        assertEquals(5252, ConfigRepository.states.graphLayoutHeight.value)
        assertEquals(5252, ConfigRepository.states.graphLayoutWidth.value)
        assertEquals(52, ConfigRepository.states.vertexRadius.value)
        assertEquals(52, ConfigRepository.states.vertexLabelSize.value)
        assertEquals(52, ConfigRepository.states.edgeLabelSize.value)
        assertEquals(52.0F, ConfigRepository.states.edgeArrowSize.value)
        assertEquals(14.0F, ConfigRepository.states.edgeWidth.value)
        assertEquals(5.5F, ConfigRepository.states.canvasDragRatio.value)
        assertEquals(5252, ConfigRepository.states.canvasLimit.value)
        assertEquals(520, ConfigRepository.states.animationDuration.value)
    }

    @Test
    fun `set value for unknown key`() {
        val setResult = ConfigRepository.setValue("unknownKey", "someValue")
        assertIs<Result.Error>(setResult)
        assertEquals("UnknownProperty", setResult.error.type)
    }

    @Test
    fun `set value for color on invalid value`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.PRIMARY, "incorrectColor")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectColor", setResult.error.type)
    }

    @Test
    fun `set value for enum on invalid value`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.THEME, "incorrectTheme")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectEnum", setResult.error.type)
    }

    @Test
    fun `set value for boolean on invalid value`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.IS_TRANSPARENT_CONSOLE, "incorrectBoolean")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectBoolean", setResult.error.type)
    }

    @Test
    fun `set value for integer on invalid value`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.MAX_COUNT_USER_COMMANDS, "incorrectInt")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectInt", setResult.error.type)
    }

    @Test
    fun `set value for integer on incorrect greater then maxValue value, maxValue and minValue exist`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.MAX_COUNT_USER_COMMANDS, "5252")
        assertIs<Result.Error>(setResult)
        assertEquals("WithoutIntRange", setResult.error.type)
    }

    @Test
    fun `set value for integer on incorrect less then minValue value, maxValue and minValue exist`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.CONSOLE_WIDTH, "52")
        assertIs<Result.Error>(setResult)
        assertEquals("WithoutIntRange", setResult.error.type)
    }

    @Test
    fun `set value for integer on incorrect less then minValue value, only minValue exist`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.CONSOLE_HEIGHT, "52")
        assertIs<Result.Error>(setResult)
        assertEquals("IntDisadvantage", setResult.error.type)
    }

    @Test
    fun `set value for float on invalid value`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.CANVAS_DRAG_RATIO, "incorrectFloat")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectFloat", setResult.error.type)
    }

    @Test
    fun `set value for float on incorrect greater then maxValue value, maxValue and minValue exist`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.CANVAS_DRAG_RATIO, "52")
        assertIs<Result.Error>(setResult)
        assertEquals("WithoutFloatRange", setResult.error.type)
    }

    @Test
    fun `set value for float on incorrect less then minValue value, maxValue and minValue exist`() {
        val setResult = ConfigRepository.setValue(ConfigKeys.CANVAS_DRAG_RATIO, "0.052")
        assertIs<Result.Error>(setResult)
        assertEquals("WithoutFloatRange", setResult.error.type)
    }

    @Test
    fun `build ConfigValidator`() {
        val result = ConfigValidator()
        assertIs<ConfigValidator>(result)
    }

    @Test
    fun `get unknown value from config`() {
        assertThrows<IllegalArgumentException> { ConfigRepository.getStringValue("unknownKey") }
    }

    @Test
    fun `set theme on custom`() {
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.PRIMARY, "#FFFFFF"))
        ConfigRepository.setThemeOnCustom()
        assertEquals("custom", ConfigRepository.states.theme.value)
    }

    @Test
    fun `check updateTheme`() {
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.PRIMARY, "#FFFFFF"))
        ConfigRepository.updateTheme()
        ConfigRepository.setThemeOnCustom()
        assertEquals("custom", ConfigRepository.states.theme.value)
        assertIs<Result.Success<Boolean>>(ConfigRepository.setValue(ConfigKeys.THEME, "dark"))
        ConfigRepository.updateTheme()
        assertEquals("dark", ConfigRepository.states.theme.value)
    }
}
