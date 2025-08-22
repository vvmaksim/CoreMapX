package model.fileHandler

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.formdev.flatlaf.FlatLightLaf
import org.coremapx.app.AppLogger.logError
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.theme.ThemesManager.isDarkSystemDialogTheme
import org.coremapx.app.userDirectory.config.ConfigRepository
import java.io.File
import javax.swing.JColorChooser
import javax.swing.JFileChooser
import javax.swing.UIManager
import java.awt.Color as AwtColor

class DialogManager {
    companion object {
        fun showOpenFileDialog(
            title: String = "Select graph file",
            directory: String = PrivateConfig.UserDirectory.HOME_DIR_PATH,
            useDarkTheme: Boolean = isDarkSystemDialogTheme(systemDialogThemeAsString = ConfigRepository.states.systemDialogTheme.value),
        ): File? =
            fileDialogManager(
                selectionMode = JFileChooser.FILES_ONLY,
                title = title,
                directory = directory,
                useDarkTheme = useDarkTheme,
            )?.selectedFile

        fun showSelectDirectoryDialog(
            title: String = "Select Directory",
            directory: String = PrivateConfig.UserDirectory.HOME_DIR_PATH,
            useDarkTheme: Boolean = isDarkSystemDialogTheme(systemDialogThemeAsString = ConfigRepository.states.systemDialogTheme.value),
        ): String? =
            fileDialogManager(
                selectionMode = JFileChooser.DIRECTORIES_ONLY,
                title = title,
                directory = directory,
                useDarkTheme = useDarkTheme,
            )?.selectedFile?.absolutePath

        fun fileDialogManager(
            selectionMode: Int,
            title: String,
            directory: String = PrivateConfig.UserDirectory.HOME_DIR_PATH,
            useDarkTheme: Boolean = isDarkSystemDialogTheme(systemDialogThemeAsString = ConfigRepository.states.systemDialogTheme.value),
        ): JFileChooser? {
            try {
                if (useDarkTheme) {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf")
                } else {
                    UIManager.setLookAndFeel(FlatLightLaf())
                }
            } catch (ex: Exception) {
                logError("fileDialogManager ERROR: $ex")
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            }

            val fileChooser =
                JFileChooser().apply {
                    fileSelectionMode = selectionMode
                    isMultiSelectionEnabled = false
                    isFileHidingEnabled = false
                    dialogTitle = title
                    val initialDir = File(directory)
                    currentDirectory =
                        if (initialDir.exists() && initialDir.isDirectory) {
                            initialDir
                        } else {
                            File(PrivateConfig.UserDirectory.HOME_DIR_PATH)
                        }
                }

            return if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fileChooser
            } else {
                null
            }
        }

        fun showSelectColorDialog(
            initialColor: Color,
            useDarkTheme: Boolean = isDarkSystemDialogTheme(systemDialogThemeAsString = ConfigRepository.states.systemDialogTheme.value),
        ): String? =
            colorDialogManager(
                initialColor = initialColor,
                useDarkTheme = useDarkTheme,
            )

        private fun colorDialogManager(
            initialColor: Color,
            useDarkTheme: Boolean,
        ): String? {
            try {
                if (useDarkTheme) {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf")
                } else {
                    UIManager.setLookAndFeel(FlatLightLaf())
                }
            } catch (ex: Exception) {
                logError("colorDialogManager ERROR: $ex")
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            }

            val color =
                JColorChooser.showDialog(
                    null,
                    "Choose Color",
                    AwtColor(initialColor.toArgb()),
                )

            return if (color != null) {
                val opaqueColor = AwtColor(color.red, color.green, color.blue, 255)
                String.format("#%06X", opaqueColor.rgb)
            } else {
                null
            }
        }
    }
}
