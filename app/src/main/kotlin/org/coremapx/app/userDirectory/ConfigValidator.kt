package org.coremapx.app.userDirectory

import extensions.toColorOrNull
import model.result.ConfigErrors
import model.result.Result
import org.coremapx.app.userDirectory.ConfigKeys.ANIMATION_DURATION
import org.coremapx.app.userDirectory.ConfigKeys.BACKGROUND
import org.coremapx.app.userDirectory.ConfigKeys.BORDER_COLOR
import org.coremapx.app.userDirectory.ConfigKeys.CANVAS_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.ConfigKeys.CANVAS_DRAG_RATIO
import org.coremapx.app.userDirectory.ConfigKeys.CANVAS_LIMIT
import org.coremapx.app.userDirectory.ConfigKeys.COMMAND_FIELD_SCROLL_DELAY
import org.coremapx.app.userDirectory.ConfigKeys.COMMAND_FIELD_WIDTH
import org.coremapx.app.userDirectory.ConfigKeys.COMMAND_LINE_BACKGROUND_COLOR
import org.coremapx.app.userDirectory.ConfigKeys.EDGE_ARROW_SIZE
import org.coremapx.app.userDirectory.ConfigKeys.EDGE_LABEL_SIZE
import org.coremapx.app.userDirectory.ConfigKeys.EDGE_MAIN_COLOR
import org.coremapx.app.userDirectory.ConfigKeys.EDGE_WIDTH
import org.coremapx.app.userDirectory.ConfigKeys.ERROR
import org.coremapx.app.userDirectory.ConfigKeys.GRAPH_LAYOUT_HEIGHT
import org.coremapx.app.userDirectory.ConfigKeys.GRAPH_LAYOUT_WIDTH
import org.coremapx.app.userDirectory.ConfigKeys.HOVERED_BORDER_COLOR
import org.coremapx.app.userDirectory.ConfigKeys.IS_EXPANDED_SETTINGS
import org.coremapx.app.userDirectory.ConfigKeys.IS_TRANSPARENT_COMMAND_LINE
import org.coremapx.app.userDirectory.ConfigKeys.LANGUAGE
import org.coremapx.app.userDirectory.ConfigKeys.MAIN_SCREEN_START_HEIGHT
import org.coremapx.app.userDirectory.ConfigKeys.MAIN_SCREEN_START_WIDTH
import org.coremapx.app.userDirectory.ConfigKeys.MAX_COUNT_MESSAGES
import org.coremapx.app.userDirectory.ConfigKeys.MESSAGE_OUTPUT_HEIGHT
import org.coremapx.app.userDirectory.ConfigKeys.ON_BACKGROUND
import org.coremapx.app.userDirectory.ConfigKeys.ON_ERROR
import org.coremapx.app.userDirectory.ConfigKeys.ON_PRIMARY
import org.coremapx.app.userDirectory.ConfigKeys.ON_SECONDARY
import org.coremapx.app.userDirectory.ConfigKeys.ON_SURFACE
import org.coremapx.app.userDirectory.ConfigKeys.PRIMARY
import org.coremapx.app.userDirectory.ConfigKeys.PRIMARY_VARIANT
import org.coremapx.app.userDirectory.ConfigKeys.SECONDARY
import org.coremapx.app.userDirectory.ConfigKeys.SECONDARY_VARIANT
import org.coremapx.app.userDirectory.ConfigKeys.START_WINDOW_PLACEMENT
import org.coremapx.app.userDirectory.ConfigKeys.SUCCESS_COLOR
import org.coremapx.app.userDirectory.ConfigKeys.SURFACE
import org.coremapx.app.userDirectory.ConfigKeys.SYSTEM_DIALOG_THEME
import org.coremapx.app.userDirectory.ConfigKeys.THEME
import org.coremapx.app.userDirectory.ConfigKeys.TITLE_BAR_HEIGHT
import org.coremapx.app.userDirectory.ConfigKeys.TITLE_BAR_ICON_SIZE
import org.coremapx.app.userDirectory.ConfigKeys.VERTEX_LABEL_SIZE
import org.coremapx.app.userDirectory.ConfigKeys.VERTEX_MAIN_COLOR
import org.coremapx.app.userDirectory.ConfigKeys.VERTEX_RADIUS
import org.coremapx.app.userDirectory.ConfigKeys.WARNING_COLOR

class ConfigValidator {
    companion object {
        fun validate(
            key: String,
            value: String,
        ): Result<Boolean> =
            when (key) {
                // General
                LANGUAGE -> enumStringValidator(key, value, listOf("ru", "en"))
                THEME -> enumStringValidator(key, value, listOf("light", "dark", "custom"))
                SYSTEM_DIALOG_THEME -> enumStringValidator(key, value, listOf("light", "dark"))
                IS_EXPANDED_SETTINGS -> booleanValidator(key, value)

                // Colors
                PRIMARY, PRIMARY_VARIANT, SECONDARY, SECONDARY_VARIANT,
                BACKGROUND, SURFACE, ERROR, ON_PRIMARY, ON_SECONDARY,
                ON_BACKGROUND, ON_SURFACE, ON_ERROR, BORDER_COLOR, SUCCESS_COLOR, WARNING_COLOR, VERTEX_MAIN_COLOR,
                HOVERED_BORDER_COLOR, EDGE_MAIN_COLOR, CANVAS_BACKGROUND_COLOR,
                COMMAND_LINE_BACKGROUND_COLOR,
                -> colorValidator(key, value)

                // Main Screen
                MAIN_SCREEN_START_HEIGHT -> intValidator(key, value, minValue = 720)
                MAIN_SCREEN_START_WIDTH -> intValidator(key, value, minValue = 1280)
                START_WINDOW_PLACEMENT -> enumStringValidator(key, value, listOf("FullScreen", "Floating", "Maximized"))

                // Title Bar
                TITLE_BAR_HEIGHT -> intValidator(key, value, minValue = 35)
                TITLE_BAR_ICON_SIZE -> intValidator(key, value, minValue = 16)

                // Command Field
                MESSAGE_OUTPUT_HEIGHT -> intValidator(key, value, minValue = 150)
                MAX_COUNT_MESSAGES -> intValidator(key, value, minValue = 1, maxValue = 10000)
                COMMAND_FIELD_WIDTH -> intValidator(key, value, minValue = 400)
                IS_TRANSPARENT_COMMAND_LINE -> booleanValidator(key, value)

                // Work Area
                GRAPH_LAYOUT_HEIGHT -> intValidator(key, value, minValue = 2000)
                GRAPH_LAYOUT_WIDTH -> intValidator(key, value, minValue = 1000)
                VERTEX_RADIUS -> intValidator(key, value, minValue = 1)
                VERTEX_LABEL_SIZE -> intValidator(key, value, minValue = 6)
                EDGE_LABEL_SIZE -> intValidator(key, value, minValue = 6)
                EDGE_ARROW_SIZE -> floatValidator(key, value, minValue = 1f, maxValue = 100f)
                EDGE_WIDTH -> floatValidator(key, value, minValue = 1f)
                CANVAS_DRAG_RATIO -> floatValidator(key, value, minValue = 0.1f, maxValue = 10.0f)
                CANVAS_LIMIT -> intValidator(key, value, minValue = 2000)

                // Performance
                ANIMATION_DURATION -> intValidator(key, value, minValue = 100, maxValue = 1500)
                COMMAND_FIELD_SCROLL_DELAY -> intValidator(key, value, minValue = 10, maxValue = 300)

                else -> Result.Error(ConfigErrors.UnknownProperty(key))
            }

        private fun colorValidator(
            key: String,
            value: String,
        ): Result<Boolean> {
            if (value.toColorOrNull() != null) return Result.Success(true)
            return Result.Error(ConfigErrors.IncorrectColor(key))
        }

        private fun enumStringValidator(
            key: String,
            value: String,
            correctValues: List<String>,
        ): Result<Boolean> {
            if (value in correctValues) return Result.Success(true)
            return Result.Error(ConfigErrors.IncorrectEnum(key, value, correctValues))
        }

        private fun booleanValidator(
            key: String,
            value: String,
        ): Result<Boolean> {
            if (value.toBooleanStrictOrNull() !is Boolean) return Result.Error(ConfigErrors.IncorrectBoolean(key, value))
            return Result.Success(true)
        }

        private fun intValidator(
            key: String,
            value: String,
            minValue: Int? = null,
            maxValue: Int? = null,
        ): Result<Boolean> {
            if (value.toIntOrNull() !is Int) return Result.Error(ConfigErrors.IncorrectInt(key, value))
            val intValue = value.toInt()
            if (maxValue != null && minValue != null) {
                if (minValue > maxValue) return Result.Error(ConfigErrors.IncorrectIntRange(key, minValue, maxValue))
                if (intValue < minValue || intValue > maxValue) {
                    return Result.Error(ConfigErrors.WithoutIntRange(key, intValue, minValue, maxValue))
                }
            } else if (maxValue != null) {
                if (intValue > maxValue) return Result.Error(ConfigErrors.IntExcess(key, intValue, maxValue))
            } else if (minValue != null) {
                if (intValue < minValue) return Result.Error(ConfigErrors.IntDisadvantage(key, intValue, minValue))
            }
            return Result.Success(true)
        }

        private fun floatValidator(
            key: String,
            value: String,
            minValue: Float? = null,
            maxValue: Float? = null,
        ): Result<Boolean> {
            if (value.toFloatOrNull() !is Float) return Result.Error(ConfigErrors.IncorrectFloat(key, value))
            val floatValue = value.toFloat()
            if (maxValue != null && minValue != null) {
                if (minValue > maxValue) return Result.Error(ConfigErrors.IncorrectFloatRange(key, minValue, maxValue))
                if (floatValue < minValue || floatValue > maxValue) {
                    return Result.Error(ConfigErrors.WithoutFloatRange(key, floatValue, minValue, maxValue))
                }
            } else if (maxValue != null) {
                if (floatValue > maxValue) return Result.Error(ConfigErrors.FloatExcess(key, floatValue, maxValue))
            } else if (minValue != null) {
                if (floatValue < minValue) return Result.Error(ConfigErrors.FloatDisadvantage(key, floatValue, minValue))
            }
            return Result.Success(true)
        }
    }
}
