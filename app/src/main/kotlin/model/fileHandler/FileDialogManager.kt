package model.fileHandler

import org.coremapx.app.userDirectory.UserDirectory
import java.awt.FileDialog
import java.io.File

class FileDialogManager {
    companion object {
        fun showOpenFileDialog(
            title: String = "Select graph file",
            directory: String = UserDirectory.baseUserDirPath + "/data/graphs"
        ): File? {
            val fileDialog = FileDialog(java.awt.Frame(), title, FileDialog.LOAD)
            fileDialog.directory = directory
            fileDialog.isVisible = true
            return fileDialog.files.firstOrNull()
        }
    }
}
