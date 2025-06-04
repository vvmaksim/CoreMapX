plugins {
    id("buildlogic.kotlin-application-conventions")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("app.cash.sqldelight") version libs.versions.sqldelight
}

dependencies {
    implementation(libs.commons.text)
    implementation(libs.kotlin.logging)
    implementation(libs.logback.classic)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.flatlaf)
    implementation(libs.sqldelight.sqlite.driver)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation(project(":utilities"))
    testImplementation(kotlin("test"))
}

sqldelight {
    databases {
        create("GraphDatabase") {
            packageName.set("org.coremapx.graph")
        }
    }
}

application {
    mainClass = "org.coremapx.app.AppKt"
}
