package org.coremapx.app

import mu.KotlinLogging

object AppLogger {
    private val logger = KotlinLogging.logger("CoreMapX")

    fun logAppStartup() {
        logger.info { "CoreMapX application started" }
    }

    fun logAppShutdown() {
        logger.info { "CoreMapX application shutdown" }
    }

    fun logInfo(message: String) {
        logger.info { message }
    }

    fun logWarning(message: String) {
        logger.warn { message }
    }

    fun logError(message: String) {
        logger.error { message }
    }

    fun logDebug(message: String) {
        logger.debug { message }
    }
}
