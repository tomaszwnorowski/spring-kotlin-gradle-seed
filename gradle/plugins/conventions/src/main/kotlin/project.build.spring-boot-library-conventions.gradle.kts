plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("java-library")

    id("io.spring.dependency-management")
    id("project.build.compiler-conventions")
    id("project.build.bom-conventions")
    id("project.build.spotless-conventions")
    id("project.build.detekt-conventions")

    jacoco
}
