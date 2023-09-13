plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("io.spring.dependency-management")
    id("org.springframework.boot")

    id("project.build.main-conventions")
    id("project.build.spotless-conventions")
    id("project.build.detekt-conventions")
    id("project.build.jib-conventions")

    jacoco
}
