plugins {
    id("project.build.spring-boot-library-conventions")
    id("project.build.fixture-conventions")
}

dependencies {
    api(project(":core"))
}
