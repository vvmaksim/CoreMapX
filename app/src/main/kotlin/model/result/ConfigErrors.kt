package model.result

sealed class ConfigErrors(
    override val type: String,
    override val description: String? = null,
) : Error {
    data class UnknownProperty(
        val propertyName: String,
    ) : ConfigErrors(
            type = "UnknownProperty",
            description = "Unknown property: $propertyName",
        )

    data class IncorrectColor(
        val propertyName: String,
    ) : ConfigErrors(
            type = "IncorrectColor",
            description = "$propertyName must be color in hex format. For example `$propertyName=#FFFFFF`",
        )

    data class IncorrectEnum(
        val propertyName: String,
        val incorrectValue: String,
        val correctValues: List<String>,
    ) : ConfigErrors(
            type = "IncorrectEnum",
            description = "$propertyName value $incorrectValue is incorrect. Supported values: ${correctValues.joinToString(", ")}",
        )

    data class IncorrectIntRange(
        val propertyName: String,
        val minValue: Int,
        val maxValue: Int,
    ) : ConfigErrors(
            type = "IncorrectIntRange",
            description = "Range $minValue..$maxValue for $propertyName is incorrect",
        )

    data class WithoutIntRange(
        val propertyName: String,
        val incorrectValue: Int,
        val minValue: Int,
        val maxValue: Int,
    ) : ConfigErrors(
            type = "WithoutIntRange",
            description = "$propertyName value is incorrect. $incorrectValue not in $minValue..$maxValue range",
        )

    data class IntExcess(
        val propertyName: String,
        val incorrectValue: Int,
        val maxValue: Int,
    ) : ConfigErrors(
            type = "IntExcess",
            description = "$propertyName value is incorrect. $incorrectValue is greater than $maxValue",
        )

    data class IntDisadvantage(
        val propertyName: String,
        val incorrectValue: Int,
        val minValue: Int,
    ) : ConfigErrors(
            type = "IntDisadvantage",
            description = "$propertyName value is incorrect. $incorrectValue is less than $minValue",
        )

    data class IncorrectInt(
        val propertyName: String,
        val incorrectValue: String,
    ) : ConfigErrors(
            type = "IncorrectInt",
            description = "$propertyName value $incorrectValue is incorrect. $propertyName must be Int",
        )

    data class IncorrectFloatRange(
        val propertyName: String,
        val minValue: Float,
        val maxValue: Float,
    ) : ConfigErrors(
            type = "IncorrectIntRange",
            description = "Range $minValue..$maxValue for $propertyName is incorrect",
        )

    data class WithoutFloatRange(
        val propertyName: String,
        val incorrectValue: Float,
        val minValue: Float,
        val maxValue: Float,
    ) : ConfigErrors(
            type = "WithoutFloatRange",
            description = "$propertyName value is incorrect. $incorrectValue not in $minValue..$maxValue range",
        )

    data class FloatExcess(
        val propertyName: String,
        val incorrectValue: Float,
        val maxValue: Float,
    ) : ConfigErrors(
            type = "FloatExcess",
            description = "$propertyName value is incorrect. $incorrectValue is greater than $maxValue",
        )

    data class FloatDisadvantage(
        val propertyName: String,
        val incorrectValue: Float,
        val minValue: Float,
    ) : ConfigErrors(
            type = "FloatDisadvantage",
            description = "$propertyName value is incorrect. $incorrectValue is less than $minValue",
        )

    data class IncorrectFloat(
        val propertyName: String,
        val incorrectValue: String,
    ) : ConfigErrors(
            type = "IncorrectFloat",
            description = "$propertyName value $incorrectValue is incorrect. $propertyName must be Float",
        )

    data class IncorrectBoolean(
        val propertyName: String,
        val incorrectValue: String,
    ) : ConfigErrors(
            type = "IncorrectBoolean",
            description = "$propertyName value $incorrectValue is incorrect. $propertyName must be `true` or `false`",
        )
}
