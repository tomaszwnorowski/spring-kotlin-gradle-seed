plugins {
    id("project.build.spring-boot-library-conventions")
    id("project.build.sql-codegen-conventions")
    id("project.build.test-conventions")
}

dependencies {
    api(project(":module-api"))

    implementation(project(":db"))

    testImplementation(libs.test.testcontainers.core)
    testImplementation(libs.test.testcontainers.postgresql)
}

configure<project.build.FlywayJooqCodegenExtension> {
    packageName.set("project.module.embedded.jooq.codegen")
    tables.add("resource")
}

sourceSets {
    main {
        kotlin.srcDir(tasks.named("flywayJooqCodegen"))
    }
}
