package org.coremapx.app.userDirectory

import extensions.toColorOrNull
import model.result.ConfigErrors
import model.result.Result

class ConfigValidator {
    companion object {
        fun validate(
            key: String,
            value: String,
        ): Result<Boolean> =
            when (key) {
                // General
                "language" -> enumStringValidator(key, value, listOf("ru", "en"))
                "theme" -> enumStringValidator(key, value, listOf("light", "dark", "custom"))
                "systemDialogTheme" -> enumStringValidator(key, value, listOf("light", "dark"))
                "isExpandedSettings" -> booleanValidator(key, value)

                // Colors
                "primary", "primaryVariant", "secondary", "secondaryVariant",
                "background", "surface", "error", "onPrimary", "onSecondary",
                "onBackground", "onSurface", "onError", "borderColor", "successColor", "warningColor", "vertexMainColor",
                "hoveredBorderColor", "edgeMainColor", "canvasBackgroundColor",
                "commandLineBackgroundColor",
                -> colorValidator(key, value)

                // Main Screen
                "mainScreenStartHeight" -> intValidator(key, value, minValue = 720)
                "mainScreenStartWidth" -> intValidator(key, value, minValue = 1280)
                "startWindowPlacement" -> enumStringValidator(key, value, listOf("FullScreen", "Floating", "Maximized"))

                // Main Menu
                "mainMenuWidth" -> intValidator(key, value, minValue = 200)

                // Title Bar
                "titleBarHeight" -> intValidator(key, value, minValue = 35)
                "titleBarIconSize" -> intValidator(key, value, minValue = 16)

                // Command Field
                "messageOutputHeight" -> intValidator(key, value, minValue = 150)
                "maxCountMessages" -> intValidator(key, value, minValue = 1, maxValue = 10000)
                "commandFieldWidth" -> intValidator(key, value, minValue = 400)
                "isTransparentCommandLine" -> booleanValidator(key, value)

                // Work Area
                "graphLayoutHeight" -> intValidator(key, value, minValue = 2000)
                "graphLayoutWidth" -> intValidator(key, value, minValue = 1000)
                "vertexRadius" -> intValidator(key, value, minValue = 1)
                "vertexLabelSize" -> intValidator(key, value, minValue = 6)
                "edgeLabelSize" -> intValidator(key, value, minValue = 6)
                "edgeArrowSize" -> floatValidator(key, value, minValue = 1f, maxValue = 100f)
                "edgeWidth" -> floatValidator(key, value, minValue = 1f)
                "canvasDragRatio" -> floatValidator(key, value, minValue = 0.1f, maxValue = 10.0f)
                "canvasLimit" -> intValidator(key, value, minValue = 2000)

                // Performance
                "animationDuration" -> intValidator(key, value, minValue = 100, maxValue = 1500)
                "commandFieldScrollDelay" -> intValidator(key, value, minValue = 10, maxValue = 300)

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
