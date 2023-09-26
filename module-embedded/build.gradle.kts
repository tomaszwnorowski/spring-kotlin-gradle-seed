plugins {
    id("project.build.spring-boot-library-conventions")
    id("project.build.sql-codegen-conventions")
    id("project.build.test-conventions")
}

dependencies {
    // main
    api(project(":module-api"))
    implementation(libs.id.tsid)
    implementation(libs.spring.boot.starter.jooq)
    runtimeOnly(project(":db"))

    // test
    testRuntimeOnly(libs.test.testcontainers.core)
    testRuntimeOnly(libs.test.testcontainers.postgresql)
}

configure<project.build.FlywayJooqCodegenExtension> {
    packageName.set("project.module.embedded.jooq.codegen")
    modulePrefix.set("module")
}

sourceSets {
    main {
        kotlin.srcDir(tasks.named("flywayJooqCodegen"))
    }
}
