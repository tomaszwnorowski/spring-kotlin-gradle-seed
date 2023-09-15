plugins {
    id("project.build.spring-boot-application-conventions")
    id("project.build.test-conventions")
}

dependencies {
    implementation(project(":rest"))
}
