package org.coremapx.app.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import org.coremapx.app.config.PrivateConfig
import java.io.File

object Fonts {
    val fontFamily =
        FontFamily(
            Font(File(PrivateConfig.UserDirectory.REGULAR_FONT_PATH), FontWeight.Normal),
            Font(File(PrivateConfig.UserDirectory.BOLD_FONT_PATH), FontWeight.Bold),
            Font(File(PrivateConfig.UserDirectory.MEDIUM_FONT_PATH), FontWeight.Medium),
            Font(File(PrivateConfig.UserDirectory.LIGHT_FONT_PATH), FontWeight.Light),
        )
}
