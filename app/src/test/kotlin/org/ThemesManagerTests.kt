package org

import org.coremapx.app.theme.AppThemes
import org.coremapx.app.theme.SystemDialogThemes
import org.coremapx.app.theme.ThemesManager.getAllAppThemes
import org.coremapx.app.theme.ThemesManager.getAllSystemDialogThemes
import org.coremapx.app.theme.ThemesManager.getAppThemeAsString
import org.coremapx.app.theme.ThemesManager.getSystemDialogThemeAsString
import org.coremapx.app.theme.ThemesManager.getSystemDialogThemeAsStringByIsLight
import org.coremapx.app.theme.ThemesManager.isDarkSystemDialogTheme
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ThemesManagerTests {
    val lightAppTheme = "Light"
    val lightLowerAppTheme = lightAppTheme.lowercase()
    val darkAppTheme = "Dark"
    val darkLowerAppTheme = darkAppTheme.lowercase()
    val customAppTheme = "Custom"
    val customLowerAppTheme = customAppTheme.lowercase()

    val allAppThemes = listOf(lightAppTheme, darkAppTheme, customAppTheme)
    val allLowerAppThemes = listOf(lightLowerAppTheme, darkLowerAppTheme, customLowerAppTheme)

    val lightSystemDialogTheme = "Light"
    val lightLowerSystemDialogTheme = lightSystemDialogTheme.lowercase()
    val darkSystemDialogTheme = "Dark"
    val darkLowerSystemDialogTheme = darkSystemDialogTheme.lowercase()

    val allSystemDialogThemes = listOf(lightSystemDialogTheme, darkSystemDialogTheme)
    val allLowerSystemDialogThemes = listOf(lightLowerSystemDialogTheme, darkLowerSystemDialogTheme)

    @Test
    fun `get app themes as strings`() {
        assertEquals(lightAppTheme, getAppThemeAsString(AppThemes.LIGHT))
        assertEquals(lightAppTheme, getAppThemeAsString(theme = AppThemes.LIGHT, makeLower = false))
        assertEquals(lightLowerAppTheme, getAppThemeAsString(theme = AppThemes.LIGHT, makeLower = true))
        assertEquals(darkAppTheme, getAppThemeAsString(theme = AppThemes.DARK, makeLower = false))
        assertEquals(darkLowerAppTheme, getAppThemeAsString(theme = AppThemes.DARK, makeLower = true))
        assertEquals(customAppTheme, getAppThemeAsString(theme = AppThemes.CUSTOM, makeLower = false))
        assertEquals(customLowerAppTheme, getAppThemeAsString(theme = AppThemes.CUSTOM, makeLower = true))
    }

    @Test
    fun `get system dialog themes as strings`() {
        assertEquals(lightSystemDialogTheme, getSystemDialogThemeAsString(SystemDialogThemes.LIGHT))
        assertEquals(lightSystemDialogTheme, getSystemDialogThemeAsString(theme = SystemDialogThemes.LIGHT, makeLower = false))
        assertEquals(lightLowerSystemDialogTheme, getSystemDialogThemeAsString(theme = SystemDialogThemes.LIGHT, makeLower = true))
        assertEquals(darkSystemDialogTheme, getSystemDialogThemeAsString(theme = SystemDialogThemes.DARK, makeLower = false))
        assertEquals(darkLowerSystemDialogTheme, getSystemDialogThemeAsString(theme = SystemDialogThemes.DARK, makeLower = true))
    }

    @Test
    fun `get system dialog themes as string by isLight`() {
        assertEquals(lightSystemDialogTheme, getSystemDialogThemeAsStringByIsLight(isLight = true))
        assertEquals(lightLowerSystemDialogTheme, getSystemDialogThemeAsStringByIsLight(isLight = true, makeLower = true))
        assertEquals(lightSystemDialogTheme, getSystemDialogThemeAsStringByIsLight(isLight = true, makeLower = false))
        assertEquals(darkLowerSystemDialogTheme, getSystemDialogThemeAsStringByIsLight(isLight = false, makeLower = true))
        assertEquals(darkSystemDialogTheme, getSystemDialogThemeAsStringByIsLight(isLight = false, makeLower = false))
    }

    @Test
    fun `check is dark system dialog theme`() {
        assertTrue(isDarkSystemDialogTheme(darkLowerSystemDialogTheme))
        assertFalse(isDarkSystemDialogTheme(lightLowerSystemDialogTheme))
    }

    @Test
    fun `get all app themes`() {
        assertEquals(allAppThemes, getAllAppThemes())
        assertEquals(allAppThemes, getAllAppThemes(makeLower = false))
        assertEquals(allLowerAppThemes, getAllAppThemes(makeLower = true))
    }

    @Test
    fun `get all system dialog themes`() {
        assertEquals(allSystemDialogThemes, getAllSystemDialogThemes())
        assertEquals(allSystemDialogThemes, getAllSystemDialogThemes(makeLower = false))
        assertEquals(allLowerSystemDialogThemes, getAllSystemDialogThemes(makeLower = true))
    }
}
