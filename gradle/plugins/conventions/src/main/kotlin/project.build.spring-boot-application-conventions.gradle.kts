plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("io.spring.dependency-management")
    id("org.springframework.boot")

    id("project.build.bom-conventions")
    id("project.build.compiler-conventions")
    id("project.build.spotless-conventions")
    id("project.build.detekt-conventions")
    id("project.build.jib-conventions")
}
