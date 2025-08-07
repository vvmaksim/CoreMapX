package org.coremapx.app.theme

object ThemesManager {
    fun getAppThemeAsString(
        theme: AppThemes,
        makeLower: Boolean = false,
    ): String {
        var result =
            when (theme) {
                AppThemes.LIGHT -> "Light"
                AppThemes.DARK -> "Dark"
                AppThemes.CUSTOM -> "Custom"
            }
        if (makeLower) result = result.lowercase()
        return result
    }

    fun getSystemDialogThemeAsString(
        theme: SystemDialogThemes,
        makeLower: Boolean = false,
    ): String {
        var result =
            when (theme) {
                SystemDialogThemes.LIGHT -> "Light"
                SystemDialogThemes.DARK -> "Dark"
            }
        if (makeLower) result = result.lowercase()
        return result
    }

    fun getSystemDialogThemeAsStringByIsLight(
        isLight: Boolean,
        makeLower: Boolean = false,
    ): String =
        if (isLight) {
            getSystemDialogThemeAsString(theme = SystemDialogThemes.LIGHT, makeLower = makeLower)
        } else {
            getSystemDialogThemeAsString(theme = SystemDialogThemes.DARK, makeLower = makeLower)
        }

    fun isDarkSystemDialogTheme(systemDialogThemeAsString: String): Boolean =
        systemDialogThemeAsString == getSystemDialogThemeAsString(theme = SystemDialogThemes.DARK, makeLower = true)

    fun getAllAppThemes(makeLower: Boolean = false): List<String> {
        val result = mutableListOf<String>()
        result.add(
            getAppThemeAsString(
                theme = AppThemes.LIGHT,
                makeLower = makeLower,
            ),
        )
        result.add(
            getAppThemeAsString(
                theme = AppThemes.DARK,
                makeLower = makeLower,
            ),
        )
        result.add(
            getAppThemeAsString(
                theme = AppThemes.CUSTOM,
                makeLower = makeLower,
            ),
        )
        return result
    }

    fun getAllSystemDialogThemes(makeLower: Boolean = false): List<String> {
        val result = mutableListOf<String>()
        result.add(
            getSystemDialogThemeAsString(
                theme = SystemDialogThemes.LIGHT,
                makeLower = makeLower,
            ),
        )
        result.add(
            getSystemDialogThemeAsString(
                theme = SystemDialogThemes.DARK,
                makeLower = makeLower,
            ),
        )
        return result
    }
}
