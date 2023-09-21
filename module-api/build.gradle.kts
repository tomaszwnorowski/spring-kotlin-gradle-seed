plugins {
    id("project.build.spring-boot-library-conventions")
    id("project.build.fixture-conventions")
}

dependencies {
    // main
    api(project(":core"))
}
