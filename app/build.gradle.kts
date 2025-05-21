plugins {
    id("buildlogic.kotlin-application-conventions")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

dependencies {
    implementation(libs.commons.text)
    implementation(libs.kotlin.logging)
    implementation(libs.logback.classic)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation(project(":utilities"))
    testImplementation(kotlin("test"))
}

application {
    mainClass = "org.coremapx.app.AppKt"
}
