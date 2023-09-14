plugins {
    id("project.build.flyway-jooq-codegen")
}

configure<project.build.FlywayJooqCodegenExtension> {
    migrationsDir.set(project(":db").layout.projectDirectory.dir("src/main/resources/db/migration"))
}
