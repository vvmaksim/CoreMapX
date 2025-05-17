package model.result

import mu.KotlinLogging
import org.coremapx.app.userDirectory.UserDirectory.copyDefaultConfig
import java.io.File
import javax.swing.JOptionPane
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

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
        0 -> logger.info { "The user clicked `OK' in the error window" }
        1 -> {
            logger.info { "The user clicked `Apply default config' in the error window" }
            val targetConfigPath = "${System.getProperty("user.home")}/.coremapx/config/Config.gcfg"
            val targetConfigFile = File(targetConfigPath)
            if (targetConfigFile.exists()) {
                targetConfigFile.delete()
                logger.info { "The Config.cfg file from $targetConfigPath has been deleted." }
            }
            copyDefaultConfig()
            logger.info { "The default configuration file was copied to $targetConfigPath" }
        }
    }
    exitProcess(1)
}
