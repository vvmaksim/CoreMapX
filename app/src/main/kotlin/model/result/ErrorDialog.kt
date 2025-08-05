package model.result

import org.coremapx.app.AppLogger.logInfo
import org.coremapx.app.userDirectory.UserDirectory.copyDefaultConfig
import java.io.File
import javax.swing.JOptionPane
import kotlin.system.exitProcess

fun showConfigErrorDialog(message: String) {
    val buttons = arrayOf("OK", "Apply default config")
    val recommendation =
        "You can apply the default configuration and restart the program. \n\n" +
            "The previous file named Config.gcfg will be replaced with the standard one."
    val result =
        JOptionPane.showOptionDialog(
            null,
            message + "\n\n" + recommendation,
            "Config Error",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            buttons,
            buttons[0],
        )
    when (result) {
        0 -> logInfo("Click on `OK' in the error window")
        1 -> {
            logInfo("Click on `Apply default config' in the error window")
            val targetConfigPath = "${System.getProperty("user.home")}/.coremapx/config/Config.gcfg"
            val targetConfigFile = File(targetConfigPath)
            if (targetConfigFile.exists()) {
                targetConfigFile.delete()
                logInfo("The Config.cfg file from $targetConfigPath has been deleted.")
            }
            copyDefaultConfig()
            logInfo("The default configuration file was copied to $targetConfigPath")
        }
    }
    exitProcess(1)
}
