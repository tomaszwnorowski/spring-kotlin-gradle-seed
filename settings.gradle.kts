pluginManagement {
    includeBuild("gradle/plugins")

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "spring-kotlin-gradle-seed"
include("app")
include("core")
include("db")
include("module-api")
include("module-embedded")
include("test")
