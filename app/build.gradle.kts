plugins {
    id("project.build.spring-boot-application-conventions")
    id("project.build.test-conventions")
}

dependencies {
    implementation(project(":rest"))

    testImplementation(project(":test"))
    testImplementation(libs.spring.boot.starter.webflux)
    testImplementation(libs.test.testcontainers.core)
    testImplementation(libs.test.testcontainers.postgresql)
}
