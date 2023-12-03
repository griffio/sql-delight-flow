import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    application
    id("app.cash.sqldelight") version "2.0.1"
}

group = "griffio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
    testImplementation(kotlin("test"))
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.example")
            dialect("app.cash.sqldelight:sqlite-3-38-dialect:2.0.1")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("MainKt")
}
