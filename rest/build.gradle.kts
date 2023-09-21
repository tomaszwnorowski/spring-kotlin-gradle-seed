plugins {
    id("project.build.spring-boot-library-conventions")
    id("project.build.test-conventions")
}

dependencies {
    // main
    api(project(":core"))
    api(libs.spring.boot.starter.web)

    implementation(project(":module-api"))
    runtimeOnly(project(":module-embedded"))
    runtimeOnly(libs.json.jackson.kotlin)
    runtimeOnly(libs.json.jackson.datatype.jsr310)

    // test
    testImplementation(testFixtures(project(":module-api")))
}
