plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.compose")
    id("jacoco")
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.ui)

    constraints {
        implementation("org.apache.commons:commons-text:1.11.0")
    }
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("io.mockk:mockk:1.13.10")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)

    reports {
        xml.required = false
        csv.required = false
        html.required = true

        html.outputLocation = file("${layout.buildDirectory}/reports/jacoco/test/html")
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
