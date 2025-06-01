package model.fileHandler

import com.formdev.flatlaf.FlatLightLaf
import java.io.File
import javax.swing.JFileChooser
import javax.swing.UIManager

class FileDialogManager {
    companion object {
        fun showOpenFileDialog(
            title: String = "Select graph file",
            directory: String = System.getProperty("user.home"),
            useDarkTheme: Boolean = false,
        ): File? {
            return dialogManager(
                selectionMode = JFileChooser.FILES_ONLY,
                title = title,
                directory = directory,
                useDarkTheme = useDarkTheme,
            )?.selectedFile
        }

        fun showSelectDirectoryDialog(
            title: String = "Select Directory",
            directory: String = System.getProperty("user.home"),
            useDarkTheme: Boolean = false,
        ): String? {
            return dialogManager(
                selectionMode = JFileChooser.DIRECTORIES_ONLY,
                title = title,
                directory = directory,
                useDarkTheme = useDarkTheme,
                )?.selectedFile?.absolutePath
        }

        fun dialogManager(
            selectionMode: Int,
            title: String,
            directory: String = System.getProperty("user.home"),
            useDarkTheme: Boolean = false,
        ): JFileChooser? {
            try {
                if (useDarkTheme) {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf")
                } else {
                    UIManager.setLookAndFeel(FlatLightLaf())
                }
            } catch (e: Exception) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            }

            val fileChooser = JFileChooser().apply {
                fileSelectionMode = selectionMode
                isMultiSelectionEnabled = false
                isFileHidingEnabled = false
                dialogTitle = title
                val initialDir = File(directory)
                currentDirectory = if (initialDir.exists() && initialDir.isDirectory) {
                    initialDir
                } else {
                    File(System.getProperty("user.home"))
                }
            }

            return if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fileChooser
            } else {
                null
            }
        }
    }
}
