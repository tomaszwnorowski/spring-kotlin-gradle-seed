plugins {
    id("project.build.spring-boot-library-conventions")
}

dependencies {
    // main
    api(libs.spring.boot.starter.jooq)
    runtimeOnly(libs.database.flyway.core)
    runtimeOnly(libs.database.postgresql.driver)
}
