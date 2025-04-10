plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    implementation(libs.kotlin.logging)
    implementation(libs.logback.classic)
}
