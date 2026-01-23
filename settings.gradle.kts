pluginManagement {
    repositories {
        maven(url = "https://central.sonatype.com/repository/maven-snapshots/")
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "sql-delight-flow"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
