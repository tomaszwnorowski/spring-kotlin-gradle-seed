plugins {
    id("project.build.spring-boot-application-conventions")
    id("project.build.test-conventions")
}

dependencies {
    implementation(libs.spring.boot.starter.jooq)
    implementation(libs.database.flyway.core)

    implementation(libs.sql.formatter)

    runtimeOnly(libs.database.postgresql.driver)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.test.testcontainers.core)
    testImplementation(libs.test.testcontainers.postgresql)
}

//openApiGenerate {
//    inputSpec.set("${project.layout.projectDirectory}/src/main/resources/specs/petstore.yaml")
//    modelPackage.set("openapi.petstore.model")
//}
//

dependencies {
    implementation(libs.spring.boot.starter.webflux)
}
