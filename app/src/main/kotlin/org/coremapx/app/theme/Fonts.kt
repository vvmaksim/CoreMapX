package org.coremapx.app.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import org.coremapx.app.userDirectory.UserDirectory.baseUserFontsDirPath
import java.io.File

object Fonts {
    val fontFamily =
        FontFamily(
            Font(File("$baseUserFontsDirPath/Font-Regular.ttf"), FontWeight.Normal),
            Font(File("$baseUserFontsDirPath/Font-Bold.ttf"), FontWeight.Bold),
            Font(File("$baseUserFontsDirPath/Font-Medium.ttf"), FontWeight.Medium),
            Font(File("$baseUserFontsDirPath/Font-Light.ttf"), FontWeight.Light),
        )
}
