plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("io.spring.dependency-management")
    id("org.springframework.boot")

    id("project.build.main-conventions")
    id("project.build.static-code-analysis-conventions")
    id("project.build.jib-conventions")
}
