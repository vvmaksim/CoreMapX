package org.coremapx.app.userDirectory

import model.result.showConfigErrorDialog
import mu.KotlinLogging
import org.coremapx.app.config.PrivateConfig
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

private val logger = KotlinLogging.logger {}

object UserDirectory {
    private var isTest: Boolean = false
    private val userDirPath
        get() = PrivateConfig.UserDirectory.DIR_PATH

    private val userFontsDirPath
        get() = PrivateConfig.UserDirectory.FONTS_DIR_PATH

    private val directories
        get() = PrivateConfig.UserDirectory.DIRECTORIES

    private const val FONTS_DIRECTORY_PATH = PrivateConfig.AppResources.DEFAULT_FONTS_DIRECTORY_PATH

    fun initTestEnvironment(testPath: String) {
        isTest = true
        PrivateConfig.UserDirectory.HOME_DIR_PATH = testPath
        PrivateConfig.AppResources.setLanguageResourcesPathsForTests()
        createBaseUserDir()
        createUserDirs()
    }

    fun init() {
        logger.info { "Checking the existence of a user directory" }
        try {
            createBaseUserDir()
            createUserDirs()
            copyDefaultConfig()
            copyDefaultFonts()
            copyCustomLanguageTemplate()
        } catch (ex: Exception) {
            logger.error { "Failed to create directories. Ex: ${ex.message}" }
        }
    }

    fun copyDefaultConfig() {
        val targetConfigPath = PrivateConfig.UserDirectory.CONFIG_FILE_PATH
        val targetConfigFile = File(targetConfigPath)

        if (!targetConfigFile.exists()) {
            logger.info { "Copying default config to $targetConfigPath" }
            try {
                val resourceStream = this::class.java.classLoader.getResourceAsStream(PrivateConfig.AppResources.DEFAULT_CONFIG_PATH)
                if (resourceStream != null) {
                    Files.copy(
                        resourceStream,
                        Paths.get(targetConfigPath),
                        StandardCopyOption.REPLACE_EXISTING,
                    )
                    logger.info { "Default config copied successfully" }
                } else {
                    logger.error { "Failed to find default config resource: ${PrivateConfig.AppResources.DEFAULT_CONFIG_PATH}" }
                    showConfigErrorDialog("Failed to find default config resource: ${PrivateConfig.AppResources.DEFAULT_CONFIG_PATH}")
                }
            } catch (ex: Exception) {
                logger.error { "Failed to copy default config. Error: ${ex.message}" }
                showConfigErrorDialog("Failed to copy default config. Error: ${ex.message}")
            }
        }
    }

    fun copyCustomLanguageTemplate() {
        val targetCustomLanguagePath = PrivateConfig.AppResources.CUSTOM_LANGUAGE_PATH
        val targetCustomLanguageFile = File(targetCustomLanguagePath)
        if (!targetCustomLanguageFile.exists()) {
            logger.info { "Copying custom language template to $targetCustomLanguagePath" }
            try {
                if (isTest) {
                    val resourceStream = this::class.java.classLoader.getResourceAsStream(PrivateConfig.AppResources.EN_LANGUAGE_PATH)
                    if (resourceStream != null) {
                        Files.copy(
                            resourceStream,
                            Paths.get(targetCustomLanguagePath),
                            StandardCopyOption.REPLACE_EXISTING,
                        )
                    } else {
                        logger.error { "Failed to find language template resource: ${PrivateConfig.AppResources.EN_LANGUAGE_PATH}" }
                        showConfigErrorDialog("Failed to find language template resource: ${PrivateConfig.AppResources.EN_LANGUAGE_PATH}")
                    }
                } else {
                    Files.copy(
                        Paths.get(PrivateConfig.AppResources.EN_LANGUAGE_PATH),
                        Paths.get(targetCustomLanguagePath),
                        StandardCopyOption.REPLACE_EXISTING,
                    )
                }
                logger.info { "Custom language template copied successfully" }
            } catch (ex: Exception) {
                logger.error { "Failed to copy custom language template. Error: ${ex.message}" }
                showConfigErrorDialog("Failed to copy custom language template. Error: ${ex.message}")
            }
        }
    }

    private fun copyDefaultFonts() {
        tryCopyFont("$FONTS_DIRECTORY_PATH/Rubik-Bold.ttf", "$userFontsDirPath/Font-Bold.ttf")
        tryCopyFont("$FONTS_DIRECTORY_PATH/Rubik-Light.ttf", "$userFontsDirPath/Font-Light.ttf")
        tryCopyFont("$FONTS_DIRECTORY_PATH/Rubik-Medium.ttf", "$userFontsDirPath/Font-Medium.ttf")
        tryCopyFont("$FONTS_DIRECTORY_PATH/Rubik-Regular.ttf", "$userFontsDirPath/Font-Regular.ttf")
    }

    private fun tryCopyFont(
        sourcePath: String,
        targetPath: String,
    ) {
        if (!File(targetPath).exists()) {
            logger.info { "Copying default font $sourcePath to $userFontsDirPath" }
            try {
                val targetFile = File(targetPath)
                targetFile.parentFile?.also { parent ->
                    if (!parent.exists()) {
                        parent.mkdirs()
                        logger.info { "Created parent directory for fonts: ${parent.absolutePath}" }
                    }
                }

                val resourceStream = this::class.java.classLoader.getResourceAsStream(sourcePath)
                if (resourceStream != null) {
                    Files.copy(
                        resourceStream,
                        Paths.get(targetPath),
                        StandardCopyOption.REPLACE_EXISTING,
                    )
                    logger.info { "Default font $targetPath copied successfully" }
                } else {
                    logger.error { "Failed to find font resource: $sourcePath" }
                }
            } catch (ex: Exception) {
                logger.error { "Failed to copy default font from $sourcePath to $targetPath. Error: ${ex.message}" }
            }
        }
    }

    private fun createBaseUserDir() {
        val baseUserDir = File(userDirPath)
        if (!baseUserDir.exists()) {
            baseUserDir.parentFile?.also { parent ->
                if (!parent.exists()) {
                    parent.mkdirs()
                    logger.info { "Created parent directory ${parent.absolutePath}" }
                }
            }

            logger.info { "Creating base user directory $userDirPath" }
            Files.createDirectory(Paths.get(userDirPath))
            logger.info { "Created base user directory $userDirPath" }
        }
    }

    private fun createUserDirs() {
        directories.forEach { dirPath ->
            val dir = File(dirPath)
            if (!dir.exists()) {
                dir.parentFile?.also { parent ->
                    if (!parent.exists()) {
                        parent.mkdirs()
                        logger.info { "Created parent directory ${parent.absolutePath}" }
                    }
                }

                logger.info { "Creating directory $dirPath" }
                Files.createDirectory(Paths.get(dirPath))
                logger.info { "Created directory $dirPath" }
            }
        }
    }
}
