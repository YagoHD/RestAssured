import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent


plugins {
    kotlin("jvm") version "1.7.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.6.20")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("io.rest-assured:kotlin-extensions:5.0.1")
    testImplementation(kotlin("script-runtime"))
    implementation ("com.google.code.gson:gson:2.8.5")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}