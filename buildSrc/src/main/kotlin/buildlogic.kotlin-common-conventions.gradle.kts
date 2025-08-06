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

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.1")

    // JUnit 4 dependencies for Compose UI tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.compose.ui:ui-test-junit4:1.6.10")

    // JUnit Vintage Engine to run JUnit 4 tests on JUnit Platform
    testImplementation("org.junit.vintage:junit-vintage-engine:5.10.1")

    // MockK
    testImplementation("io.mockk:mockk:1.13.10")
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
