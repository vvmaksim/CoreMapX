plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation(libs.commons.text)
    implementation(libs.kotlin.logging)
    implementation(libs.logback.classic)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation(project(":utilities"))
    testImplementation(kotlin("test"))
}

application {
    mainClass = "org.coremapx.app.AppKt"
}
