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
                fileSelectionMode = JFileChooser.FILES_ONLY
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
                fileChooser.selectedFile
            } else {
                null
            }
        }
    }
}
