package org.coremapx.app

import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

private val logger = KotlinLogging.logger {}

object UserDirectory {
    private val baseUserDirPath = "${System.getProperty("user.home")}/.coremapx"
    private val directories = listOf(
        "$baseUserDirPath/logs",
        "$baseUserDirPath/config",
        "$baseUserDirPath/data",
        "$baseUserDirPath/data/temp"
    )

    fun init() {
        logger.info { "Checking the existence of a user directory" }
        try {
            createBaseUserDir()
            createUserDirs()
        } catch (ex: Exception) {
            logger.error { "Failed to create directories. Ex: ${ex.message}" }
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
