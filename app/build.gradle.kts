plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation(libs.commons.text)
    implementation(libs.kotlin.logging)
    implementation(libs.logback.classic)
    implementation(project(":utilities"))
}

application {
    mainClass = "org.coremapx.app.AppKt"
}
