package org.coremapx.app.userDirectory

import model.result.showConfigErrorDialog
import org.coremapx.app.AppLogger.logError
import org.coremapx.app.AppLogger.logInfo
import org.coremapx.app.config.PrivateConfig
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

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
        try {
            isTest = true
            PrivateConfig.UserDirectory.HOME_DIR_PATH = testPath
            PrivateConfig.AppResources.setLanguageResourcesPathsForTests()
            createBaseUserDir()
            createUserDirs()
        } catch (ex: Exception) {
            logError("Failed to create test environment. Error: $ex")
        }
    }

    fun init() {
        logInfo("Checking the existence of a user directory")
        try {
            createBaseUserDir()
            createUserDirs()
            copyDefaultConfig()
            copyDefaultFonts()
            copyCustomLanguageTemplate()
        } catch (ex: Exception) {
            logError("Failed to create app environment. Error: $ex")
        }
    }

    fun copyDefaultConfig() {
        val targetConfigPath = PrivateConfig.UserDirectory.CONFIG_FILE_PATH
        val targetConfigFile = File(targetConfigPath)

        if (!targetConfigFile.exists()) {
            logInfo("Copying default config to $targetConfigPath")
            try {
                val resourceStream = this::class.java.classLoader.getResourceAsStream(PrivateConfig.AppResources.DEFAULT_CONFIG_PATH)
                if (resourceStream != null) {
                    Files.copy(
                        resourceStream,
                        Paths.get(targetConfigPath),
                        StandardCopyOption.REPLACE_EXISTING,
                    )
                    logInfo("Default config copied successfully")
                } else {
                    logError("Failed to find default config resource: ${PrivateConfig.AppResources.DEFAULT_CONFIG_PATH}")
                    showConfigErrorDialog("Failed to find default config resource: ${PrivateConfig.AppResources.DEFAULT_CONFIG_PATH}")
                }
            } catch (ex: Exception) {
                logError("Failed to copy default config. Error: $ex")
                showConfigErrorDialog("Failed to copy default config. Error: $ex")
            }
        }
    }

    fun copyCustomLanguageTemplate() {
        val targetCustomLanguagePath = PrivateConfig.AppResources.CUSTOM_LANGUAGE_PATH
        val targetCustomLanguageFile = File(targetCustomLanguagePath)
        if (!targetCustomLanguageFile.exists()) {
            logInfo("Copying custom language template to $targetCustomLanguagePath")
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
                        logError("Failed to find language template resource: ${PrivateConfig.AppResources.EN_LANGUAGE_PATH}")
                        showConfigErrorDialog("Failed to find language template resource: ${PrivateConfig.AppResources.EN_LANGUAGE_PATH}")
                    }
                } else {
                    Files.copy(
                        Paths.get(PrivateConfig.AppResources.EN_LANGUAGE_PATH),
                        Paths.get(targetCustomLanguagePath),
                        StandardCopyOption.REPLACE_EXISTING,
                    )
                }
                logInfo("Custom language template copied successfully")
            } catch (ex: Exception) {
                logError("Failed to copy custom language template. Error: $ex")
                showConfigErrorDialog("Failed to copy custom language template. Error: $ex")
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
            logInfo("Copying default font $sourcePath to $userFontsDirPath")
            try {
                val targetFile = File(targetPath)
                targetFile.parentFile?.also { parent ->
                    if (!parent.exists()) {
                        parent.mkdirs()
                        logInfo("Created parent directory for fonts: ${parent.absolutePath}")
                    }
                }

                val resourceStream = this::class.java.classLoader.getResourceAsStream(sourcePath)
                if (resourceStream != null) {
                    Files.copy(
                        resourceStream,
                        Paths.get(targetPath),
                        StandardCopyOption.REPLACE_EXISTING,
                    )
                    logInfo("Default font $targetPath copied successfully")
                } else {
                    logError("Failed to find font resource: $sourcePath")
                }
            } catch (ex: Exception) {
                logError("Failed to copy default font from $sourcePath to $targetPath. Error: $ex")
            }
        }
    }

    private fun createBaseUserDir() {
        val baseUserDir = File(userDirPath)
        if (!baseUserDir.exists()) {
            baseUserDir.parentFile?.also { parent ->
                if (!parent.exists()) {
                    parent.mkdirs()
                    logInfo("Created parent directory ${parent.absolutePath}")
                }
            }

            logInfo("Creating base user directory $userDirPath")
            Files.createDirectory(Paths.get(userDirPath))
            logInfo("Created base user directory $userDirPath")
        }
    }

    private fun createUserDirs() {
        directories.forEach { dirPath ->
            val dir = File(dirPath)
            if (!dir.exists()) {
                dir.parentFile?.also { parent ->
                    if (!parent.exists()) {
                        parent.mkdirs()
                        logInfo("Created parent directory ${parent.absolutePath}")
                    }
                }

                logInfo("Creating directory $dirPath")
                Files.createDirectory(Paths.get(dirPath))
                logInfo("Created directory $dirPath")
            }
        }
    }
}
