plugins {
    id("project.build.spring-boot-library-conventions")
    id("project.build.test-conventions")
}

dependencies {
    api(project(":core"))

    implementation(project(":module-api"))
    runtimeOnly(project(":module-embedded"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.json.jackson.kotlin)
    implementation(libs.json.jackson.datatype.jsr310)

    implementation(testFixtures(project(":module-api")))
}
