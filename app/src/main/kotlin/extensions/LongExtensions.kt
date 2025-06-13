package extensions

fun Long.toBooleanOrNull(): Boolean? =
    when (this) {
        1L -> true
        0L -> false
        else -> null
    }
