plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("java-library")

    id("io.spring.dependency-management")
    id("project.build.main-conventions")
    id("project.build.spotless-conventions")
    id("project.build.detekt-conventions")

    jacoco
}
