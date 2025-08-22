package org.coremapx.app.userDirectory.config

import extensions.toColorOrNull
import model.result.ConfigErrors
import model.result.Result
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.localization.objects.LanguageCodes
import org.coremapx.app.localization.objects.LanguageCodesManager.getCodeAsString
import org.coremapx.app.theme.ThemesManager.getAllAppThemes
import org.coremapx.app.theme.ThemesManager.getAllSystemDialogThemes

class ConfigValidator {
    companion object {
        fun validate(
            key: String,
            value: String,
        ): Result<Boolean> =
            when (key) {
                // General
                ConfigKeys.VERSION ->
                    versionValidator(
                        key = key,
                        currentValue = value,
                    )
                ConfigKeys.LANGUAGE ->
                    enumStringValidator(
                        key = key,
                        value = value,
                        correctValues =
                            listOf(
                                getCodeAsString(LanguageCodes.EN),
                                getCodeAsString(LanguageCodes.RU),
                                getCodeAsString(LanguageCodes.CUSTOM),
                            ),
                    )
                ConfigKeys.THEME ->
                    enumStringValidator(
                        key = key,
                        value = value,
                        correctValues = getAllAppThemes(makeLower = true),
                    )
                ConfigKeys.SYSTEM_DIALOG_THEME ->
                    enumStringValidator(
                        key = key,
                        value = value,
                        correctValues = getAllSystemDialogThemes(makeLower = true),
                    )
                ConfigKeys.IS_EXPANDED_SETTINGS -> booleanValidator(key, value)

                // Colors
                ConfigKeys.PRIMARY, ConfigKeys.PRIMARY_VARIANT, ConfigKeys.SECONDARY, ConfigKeys.SECONDARY_VARIANT,
                ConfigKeys.BACKGROUND, ConfigKeys.SURFACE, ConfigKeys.ERROR, ConfigKeys.ON_PRIMARY, ConfigKeys.ON_SECONDARY,
                ConfigKeys.ON_BACKGROUND, ConfigKeys.ON_SURFACE, ConfigKeys.ON_ERROR, ConfigKeys.BORDER_COLOR, ConfigKeys.SUCCESS_COLOR,
                ConfigKeys.WARNING_COLOR, ConfigKeys.VERTEX_MAIN_COLOR, ConfigKeys.HOVERED_BORDER_COLOR, ConfigKeys.EDGE_MAIN_COLOR,
                ConfigKeys.SHORTEST_PATH_COLOR, ConfigKeys.OTHER_PATHS_COLOR, ConfigKeys.CANVAS_BACKGROUND_COLOR,
                ConfigKeys.CONSOLE_BACKGROUND_COLOR,
                -> colorValidator(key, value)

                // Main Screen
                ConfigKeys.MAIN_SCREEN_START_HEIGHT -> intValidator(key, value, minValue = 720)
                ConfigKeys.MAIN_SCREEN_START_WIDTH -> intValidator(key, value, minValue = 1280)
                ConfigKeys.START_WINDOW_PLACEMENT -> enumStringValidator(key, value, listOf("FullScreen", "Floating", "Maximized"))

                // Title Bar
                ConfigKeys.TITLE_BAR_HEIGHT -> intValidator(key, value, minValue = 35)
                ConfigKeys.TITLE_BAR_ICON_SIZE -> intValidator(key, value, minValue = 16)

                // Console
                ConfigKeys.CONSOLE_HEIGHT -> intValidator(key, value, minValue = 150)
                ConfigKeys.MAX_COUNT_MESSAGES -> intValidator(key, value, minValue = 1, maxValue = 10000)
                ConfigKeys.MAX_COUNT_USER_COMMANDS -> intValidator(key, value, minValue = 1, maxValue = 1000)
                ConfigKeys.CONSOLE_WIDTH -> intValidator(key, value, minValue = 400, maxValue = 900)
                ConfigKeys.IS_TRANSPARENT_CONSOLE -> booleanValidator(key, value)

                // Work Area
                ConfigKeys.GRAPH_LAYOUT_HEIGHT -> intValidator(key, value, minValue = 2000)
                ConfigKeys.GRAPH_LAYOUT_WIDTH -> intValidator(key, value, minValue = 1000)
                ConfigKeys.VERTEX_RADIUS -> intValidator(key, value, minValue = 1)
                ConfigKeys.VERTEX_LABEL_SIZE -> intValidator(key, value, minValue = 6)
                ConfigKeys.EDGE_LABEL_SIZE -> intValidator(key, value, minValue = 6)
                ConfigKeys.EDGE_ARROW_SIZE -> floatValidator(key, value, minValue = 1f, maxValue = 100f)
                ConfigKeys.EDGE_WIDTH -> floatValidator(key, value, minValue = 1f, maxValue = 15f)
                ConfigKeys.CANVAS_DRAG_RATIO -> floatValidator(key, value, minValue = 0.1f, maxValue = 10.0f)
                ConfigKeys.CANVAS_LIMIT -> intValidator(key, value, minValue = 2000)

                // Performance
                ConfigKeys.ANIMATION_DURATION -> intValidator(key, value, minValue = 100, maxValue = 1500)

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

        private fun versionValidator(
            key: String,
            currentValue: String,
            correctValue: String = PrivateConfig.General.APP_VERSION,
        ): Result<Boolean> =
            if (correctValue != currentValue) {
                Result.Error(ConfigErrors.IncorrectVersion(key, currentValue, correctValue))
            } else {
                Result.Success(true)
            }
    }
}
