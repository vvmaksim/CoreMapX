package org.coremapx.app.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import org.coremapx.app.config.PrivateConfig
import java.io.File

object Fonts {
    private val fontsDirPath = PrivateConfig.UserDirectory.FONTS_DIR_PATH
    val fontFamily =
        FontFamily(
            Font(File("$fontsDirPath/Font-Regular.ttf"), FontWeight.Normal),
            Font(File("$fontsDirPath/Font-Bold.ttf"), FontWeight.Bold),
            Font(File("$fontsDirPath/Font-Medium.ttf"), FontWeight.Medium),
            Font(File("$fontsDirPath/Font-Light.ttf"), FontWeight.Light),
        )
}
