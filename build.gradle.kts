plugins {
    kotlin("jvm") version "2.2.21"
    application
    id("app.cash.sqldelight") version "2.3.0-SNAPSHOT"
}

group = "griffio"
version = "1.0-SNAPSHOT"

repositories {
    maven(url = "https://central.sonatype.com/repository/maven-snapshots/")
    mavenCentral()
    google()
}

dependencies {
    implementation("app.cash.sqldelight:sqlite-driver:2.3.0-SNAPSHOT")
    implementation("app.cash.sqldelight:coroutines-extensions:2.3.0-SNAPSHOT")
    testImplementation(kotlin("test"))
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.example")
            dialect("app.cash.sqldelight:sqlite-3-38-dialect:2.3.0-SNAPSHOT")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("MainKt")
}
