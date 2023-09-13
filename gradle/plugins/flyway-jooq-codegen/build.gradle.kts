plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // 1. start/stop postgres in docker container
    implementation(libs.test.testcontainers.core)
    implementation(libs.test.testcontainers.postgresql)
    runtimeOnly(libs.database.postgresql.driver)

    // 2. database migration using flyway
    implementation(libs.database.flyway.core)

    // 3. code generation using jooq
    implementation(libs.database.jooq.codegen)
    implementation(libs.database.jooq.meta)

    // 4. setting kotlin compile task to depend on code generation task
    implementation(libs.build.plugin.kotlin.gradle)
}

gradlePlugin {
    plugins {
        create("flyway-jooq-codegen") {
            id = "project.build.flyway-jooq-codegen"
            implementationClass = "project.build.FlywayJooqCodegenPlugin"
        }
    }
}
