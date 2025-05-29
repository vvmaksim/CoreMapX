package org.coremapx.app.userDirectory

import model.result.showConfigErrorDialog
import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

private val logger = KotlinLogging.logger {}

object UserDirectory {
    val baseUserDirPath = "${System.getProperty("user.home")}/.coremapx"
    private val defaultConfigPath = "app/src/main/resources/Configs/DefaultConfig.gcfg"
    private val directories =
        listOf(
            "$baseUserDirPath/logs",
            "$baseUserDirPath/config",
            "$baseUserDirPath/data",
            "$baseUserDirPath/data/temp",
            "$baseUserDirPath/data/graphs",
        )

    fun init() {
        logger.info { "Checking the existence of a user directory" }
        try {
            createBaseUserDir()
            createUserDirs()
            copyDefaultConfig()
        } catch (ex: Exception) {
            logger.error { "Failed to create directories. Ex: ${ex.message}" }
        }
    }

    fun copyDefaultConfig() {
        val targetConfigPath = "$baseUserDirPath/config/Config.gcfg"
        val targetConfigFile = File(targetConfigPath)

        if (!targetConfigFile.exists()) {
            logger.info { "Copying default config to $targetConfigPath" }
            try {
                Files.copy(
                    Paths.get(defaultConfigPath),
                    Paths.get(targetConfigPath),
                    StandardCopyOption.REPLACE_EXISTING,
                )
                logger.info { "Default config copied successfully" }
            } catch (ex: Exception) {
                logger.error { "Failed to copy default config. Error: ${ex.message}" }
                showConfigErrorDialog("Failed to copy default config. Error: ${ex.message}")
            }
        }
    }

    private fun createBaseUserDir() {
        val baseUserDir = File(baseUserDirPath)
        if (!baseUserDir.exists()) {
            logger.info { "Creating base user directory $baseUserDirPath" }
            Files.createDirectory(Paths.get(baseUserDirPath))
            logger.info { "Created base user directory $baseUserDirPath" }
        }
    }

    private fun createUserDirs() {
        directories.forEach { dirPath ->
            val dir = File(dirPath)
            if (!dir.exists()) {
                logger.info { "Creating directory $dirPath" }
                Files.createDirectory(Paths.get(dirPath))
                logger.info { "Created directory $dirPath" }
            }
        }
    }
}
