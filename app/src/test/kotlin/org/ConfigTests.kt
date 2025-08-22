package org

import androidx.compose.ui.graphics.Color
import extensions.toColorOrNull
import model.result.Result
import org.coremapx.app.config
import org.coremapx.app.userDirectory.UserDirectory
import org.coremapx.app.userDirectory.config.ConfigKeys
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
        assertEquals("en", config.states.language.value)
        assertEquals("light", config.states.theme.value)
        assertEquals("light", config.states.systemDialogTheme.value)
        assertEquals(true, config.states.isExpandedSettings.value)
        assertEquals(Color(0xFF6200EE), config.states.primary.value)
        assertEquals(Color(0xFF757575), config.states.primaryVariant.value)
        assertEquals(Color(0xFFFFC107), config.states.secondary.value)
        assertEquals(Color(0xFFFFA000), config.states.secondaryVariant.value)
        assertEquals(Color(0xFFE6E6FA), config.states.background.value)
        assertEquals(Color(0xFFF5F5F5), config.states.surface.value)
        assertEquals(Color(0xFFF44336), config.states.error.value)
        assertEquals(Color(0xFFFFFFFF), config.states.onPrimary.value)
        assertEquals(Color(0xFF000000), config.states.onSecondary.value)
        assertEquals(Color(0xFF000000), config.states.onBackground.value)
        assertEquals(Color(0xFF000000), config.states.onSurface.value)
        assertEquals(Color(0xFFFFFFFF), config.states.onError.value)
        assertEquals(Color(0xFFC0C0C0), config.states.borderColor.value)
        assertEquals(Color(0xFF4CAF50), config.states.successColor.value)
        assertEquals(Color(0xFFFFB300), config.states.warningColor.value)
        assertEquals(Color(0xFFF15BB5), config.states.vertexMainColor.value)
        assertEquals(Color(0xFF6200EE), config.states.hoveredBorderColor.value)
        assertEquals(Color(0xFF000000), config.states.edgeMainColor.value)
        assertEquals(Color(0xFF4CAF50), config.states.shortestPathColor.value)
        assertEquals(Color(0xFFFF9800), config.states.otherPathsColor.value)
        assertEquals(Color(0xFFFFFFFF), config.states.canvasBackgroundColor.value)
        assertEquals(Color(0xFFFFFFFF), config.states.consoleBackgroundColor.value)
        assertEquals(720, config.states.mainScreenStartHeight.value)
        assertEquals(1280, config.states.mainScreenStartWidth.value)
        assertEquals("Maximized", config.states.startWindowPlacement.value)
        assertEquals(40, config.states.titleBarHeight.value)
        assertEquals(20, config.states.titleBarIconSize.value)
        assertEquals(200, config.states.consoleHeight.value)
        assertEquals(100, config.states.maxCountMessages.value)
        assertEquals(200, config.states.maxCountUserCommands.value)
        assertEquals(700, config.states.consoleWidth.value)
        assertEquals(false, config.states.isTransparentConsoleBlock.value)
        assertEquals(10000, config.states.graphLayoutHeight.value)
        assertEquals(10000, config.states.graphLayoutWidth.value)
        assertEquals(15, config.states.vertexRadius.value)
        assertEquals(14, config.states.vertexLabelSize.value)
        assertEquals(14, config.states.edgeLabelSize.value)
        assertEquals(10.0F, config.states.edgeArrowSize.value)
        assertEquals(2.0F, config.states.edgeWidth.value)
        assertEquals(1.0F, config.states.canvasDragRatio.value)
        assertEquals(10000, config.states.canvasLimit.value)
        assertEquals(300, config.states.animationDuration.value)

        // Set all values
        val color = "#525252"
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.LANGUAGE, "ru"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.THEME, "dark"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.SYSTEM_DIALOG_THEME, "dark"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.IS_EXPANDED_SETTINGS, "false"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.PRIMARY, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.PRIMARY_VARIANT, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.SECONDARY, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.SECONDARY_VARIANT, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.BACKGROUND, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.SURFACE, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.ERROR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.ON_PRIMARY, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.ON_SECONDARY, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.ON_BACKGROUND, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.ON_SURFACE, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.ON_ERROR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.BORDER_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.SUCCESS_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.WARNING_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.VERTEX_MAIN_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.HOVERED_BORDER_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.EDGE_MAIN_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.SHORTEST_PATH_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.OTHER_PATHS_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.CANVAS_BACKGROUND_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.CONSOLE_BACKGROUND_COLOR, color))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.MAIN_SCREEN_START_HEIGHT, "1720"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.MAIN_SCREEN_START_WIDTH, "2280"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.START_WINDOW_PLACEMENT, "Floating"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.TITLE_BAR_HEIGHT, "52"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.TITLE_BAR_ICON_SIZE, "52"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.CONSOLE_HEIGHT, "252"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.MAX_COUNT_MESSAGES, "152"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.MAX_COUNT_USER_COMMANDS, "252"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.CONSOLE_WIDTH, "752"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.IS_TRANSPARENT_CONSOLE, "true"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.GRAPH_LAYOUT_HEIGHT, "5252"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.GRAPH_LAYOUT_WIDTH, "5252"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.VERTEX_RADIUS, "52"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.VERTEX_LABEL_SIZE, "52"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.EDGE_LABEL_SIZE, "52"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.EDGE_ARROW_SIZE, "52.0"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.EDGE_WIDTH, "14.0"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.CANVAS_DRAG_RATIO, "5.5"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.CANVAS_LIMIT, "5252"))
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.ANIMATION_DURATION, "520"))

        // Check new values
        assertEquals("ru", config.states.language.value)
        assertEquals("dark", config.states.theme.value)
        assertEquals("dark", config.states.systemDialogTheme.value)
        assertEquals(false, config.states.isExpandedSettings.value)
        assertEquals(color.toColorOrNull(), config.states.primary.value)
        assertEquals(color.toColorOrNull(), config.states.primaryVariant.value)
        assertEquals(color.toColorOrNull(), config.states.secondary.value)
        assertEquals(color.toColorOrNull(), config.states.secondaryVariant.value)
        assertEquals(color.toColorOrNull(), config.states.background.value)
        assertEquals(color.toColorOrNull(), config.states.surface.value)
        assertEquals(color.toColorOrNull(), config.states.error.value)
        assertEquals(color.toColorOrNull(), config.states.onPrimary.value)
        assertEquals(color.toColorOrNull(), config.states.onSecondary.value)
        assertEquals(color.toColorOrNull(), config.states.onBackground.value)
        assertEquals(color.toColorOrNull(), config.states.onSurface.value)
        assertEquals(color.toColorOrNull(), config.states.onError.value)
        assertEquals(color.toColorOrNull(), config.states.borderColor.value)
        assertEquals(color.toColorOrNull(), config.states.successColor.value)
        assertEquals(color.toColorOrNull(), config.states.warningColor.value)
        assertEquals(color.toColorOrNull(), config.states.vertexMainColor.value)
        assertEquals(color.toColorOrNull(), config.states.hoveredBorderColor.value)
        assertEquals(color.toColorOrNull(), config.states.edgeMainColor.value)
        assertEquals(color.toColorOrNull(), config.states.shortestPathColor.value)
        assertEquals(color.toColorOrNull(), config.states.otherPathsColor.value)
        assertEquals(color.toColorOrNull(), config.states.canvasBackgroundColor.value)
        assertEquals(color.toColorOrNull(), config.states.consoleBackgroundColor.value)
        assertEquals(1720, config.states.mainScreenStartHeight.value)
        assertEquals(2280, config.states.mainScreenStartWidth.value)
        assertEquals("Floating", config.states.startWindowPlacement.value)
        assertEquals(52, config.states.titleBarHeight.value)
        assertEquals(52, config.states.titleBarIconSize.value)
        assertEquals(252, config.states.consoleHeight.value)
        assertEquals(152, config.states.maxCountMessages.value)
        assertEquals(252, config.states.maxCountUserCommands.value)
        assertEquals(752, config.states.consoleWidth.value)
        assertEquals(true, config.states.isTransparentConsoleBlock.value)
        assertEquals(5252, config.states.graphLayoutHeight.value)
        assertEquals(5252, config.states.graphLayoutWidth.value)
        assertEquals(52, config.states.vertexRadius.value)
        assertEquals(52, config.states.vertexLabelSize.value)
        assertEquals(52, config.states.edgeLabelSize.value)
        assertEquals(52.0F, config.states.edgeArrowSize.value)
        assertEquals(14.0F, config.states.edgeWidth.value)
        assertEquals(5.5F, config.states.canvasDragRatio.value)
        assertEquals(5252, config.states.canvasLimit.value)
        assertEquals(520, config.states.animationDuration.value)
    }

    @Test
    fun `set value for unknown key`() {
        val setResult = config.setValue("unknownKey", "someValue")
        assertIs<Result.Error>(setResult)
        assertEquals("UnknownProperty", setResult.error.type)
    }

    @Test
    fun `set value for color on invalid value`() {
        val setResult = config.setValue(ConfigKeys.PRIMARY, "incorrectColor")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectColor", setResult.error.type)
    }

    @Test
    fun `set value for enum on invalid value`() {
        val setResult = config.setValue(ConfigKeys.THEME, "incorrectTheme")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectEnum", setResult.error.type)
    }

    @Test
    fun `set value for boolean on invalid value`() {
        val setResult = config.setValue(ConfigKeys.IS_TRANSPARENT_CONSOLE, "incorrectBoolean")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectBoolean", setResult.error.type)
    }

    @Test
    fun `set value for integer on invalid value`() {
        val setResult = config.setValue(ConfigKeys.MAX_COUNT_USER_COMMANDS, "incorrectInt")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectInt", setResult.error.type)
    }

    @Test
    fun `set value for integer on incorrect greater then maxValue value, maxValue and minValue exist`() {
        val setResult = config.setValue(ConfigKeys.MAX_COUNT_USER_COMMANDS, "5252")
        assertIs<Result.Error>(setResult)
        assertEquals("WithoutIntRange", setResult.error.type)
    }

    @Test
    fun `set value for integer on incorrect less then minValue value, maxValue and minValue exist`() {
        val setResult = config.setValue(ConfigKeys.CONSOLE_WIDTH, "52")
        assertIs<Result.Error>(setResult)
        assertEquals("WithoutIntRange", setResult.error.type)
    }

    @Test
    fun `set value for integer on incorrect less then minValue value, only minValue exist`() {
        val setResult = config.setValue(ConfigKeys.CONSOLE_HEIGHT, "52")
        assertIs<Result.Error>(setResult)
        assertEquals("IntDisadvantage", setResult.error.type)
    }

    @Test
    fun `set value for float on invalid value`() {
        val setResult = config.setValue(ConfigKeys.CANVAS_DRAG_RATIO, "incorrectFloat")
        assertIs<Result.Error>(setResult)
        assertEquals("IncorrectFloat", setResult.error.type)
    }

    @Test
    fun `set value for float on incorrect greater then maxValue value, maxValue and minValue exist`() {
        val setResult = config.setValue(ConfigKeys.CANVAS_DRAG_RATIO, "52")
        assertIs<Result.Error>(setResult)
        assertEquals("WithoutFloatRange", setResult.error.type)
    }

    @Test
    fun `set value for float on incorrect less then minValue value, maxValue and minValue exist`() {
        val setResult = config.setValue(ConfigKeys.CANVAS_DRAG_RATIO, "0.052")
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
        assertThrows<IllegalArgumentException> { config.getStringValue("unknownKey") }
    }

    @Test
    fun `set theme on custom`() {
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.PRIMARY, "#FFFFFF"))
        config.setThemeOnCustom()
        assertEquals("custom", config.states.theme.value)
    }

    @Test
    fun `check updateTheme`() {
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.PRIMARY, "#FFFFFF"))
        config.updateTheme()
        config.setThemeOnCustom()
        assertEquals("custom", config.states.theme.value)
        assertIs<Result.Success<Boolean>>(config.setValue(ConfigKeys.THEME, "dark"))
        config.updateTheme()
        assertEquals("dark", config.states.theme.value)
    }
}
