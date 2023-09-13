plugins {
    id("project.build.spring-boot-application-conventions")
    id("project.build.openapi-conventions")
    id("project.build.flyway-jooq-codegen")
}

repositories {
    mavenCentral()
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

openApiGenerate {
    inputSpec.set("${project.layout.projectDirectory}/src/main/resources/specs/petstore.yaml")
    modelPackage.set("openapi.petstore.model")
}

configure<project.build.FlywayJooqCodegenExtension> {
    packageName.set("jooq.app.codegen")
    tables.add("car")
}
sourceSets {
    main {
        kotlin.srcDir(tasks.named("flywayJooqCodegen"))
        kotlin.srcDir(tasks.named("openApiGenerate"))
    }
}

dependencies {
    implementation(libs.spring.boot.starter.webflux)
}
