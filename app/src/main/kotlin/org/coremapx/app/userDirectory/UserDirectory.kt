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
    val baseUserFontsDirPath = "$baseUserDirPath/config/fonts"
    private const val DEFAULT_CONFIG_PATH = "app/src/main/resources/Configs/DefaultConfig.gcfg"
    private const val DEFAULT_FONTS_DIRECTORY_PATH = "app/src/main/resources/fonts"
    private val directories =
        listOf(
            "$baseUserDirPath/logs",
            "$baseUserDirPath/config",
            "$baseUserDirPath/config/fonts",
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
            copyDefaultFonts()
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
                    Paths.get(DEFAULT_CONFIG_PATH),
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

    private fun copyDefaultFonts() {
        tryCopyFont("$DEFAULT_FONTS_DIRECTORY_PATH/Rubik-Bold.ttf", "$baseUserFontsDirPath/Font-Bold.ttf")
        tryCopyFont("$DEFAULT_FONTS_DIRECTORY_PATH/Rubik-Light.ttf", "$baseUserFontsDirPath/Font-Light.ttf")
        tryCopyFont("$DEFAULT_FONTS_DIRECTORY_PATH/Rubik-Medium.ttf", "$baseUserFontsDirPath/Font-Medium.ttf")
        tryCopyFont("$DEFAULT_FONTS_DIRECTORY_PATH/Rubik-Regular.ttf", "$baseUserFontsDirPath/Font-Regular.ttf")
    }

    private fun tryCopyFont(
        sourcePath: String,
        targetPath: String,
    ) {
        if (!File(targetPath).exists()) {
            logger.info { "Copying default font $sourcePath to $baseUserFontsDirPath" }
            try {
                Files.copy(
                    Paths.get(sourcePath),
                    Paths.get(targetPath),
                    StandardCopyOption.REPLACE_EXISTING,
                )
                logger.info { "Default font $targetPath copied successfully" }
            } catch (ex: Exception) {
                logger.error { "Failed to copy default font from $sourcePath to $targetPath. Error: ${ex.message}" }
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
