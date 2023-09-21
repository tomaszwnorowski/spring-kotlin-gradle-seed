plugins {
    id("project.build.spring-boot-application-conventions")
    id("project.build.test-conventions")
}

dependencies {
    // main
    implementation(libs.spring.boot.autoconfigure)
    runtimeOnly(project(":rest"))

    // test
    testImplementation(project(":rest"))
    testImplementation(project(":test"))
    testImplementation(libs.spring.boot.starter.webflux)
    testRuntimeOnly(libs.test.testcontainers.core)
    testRuntimeOnly(libs.test.testcontainers.postgresql)
}
