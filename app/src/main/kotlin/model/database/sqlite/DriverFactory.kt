package model.database.sqlite

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.coremapx.app.AppLogger.logDebug
import org.coremapx.graph.GraphDatabase
import java.io.File
import java.util.Properties

object DriverFactory {
    fun createDriver(filePath: String): SqlDriver {
        logDebug("Launched createDriver() function from DriverFactory with filePath:$filePath")
        val driver: SqlDriver =
            JdbcSqliteDriver(
                url = "jdbc:sqlite:$filePath",
                properties = Properties(),
            )
        if (!File(filePath).exists()) {
            GraphDatabase.Schema.create(driver)
        }
        return driver
    }
}

fun createDatabase(filePath: String): GraphDatabase {
    logDebug("Launched createDatabase() function from DriverFactory with filePath:$filePath")
    val driver = DriverFactory.createDriver(filePath)
    return GraphDatabase(driver)
}
