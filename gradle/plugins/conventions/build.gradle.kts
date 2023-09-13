plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // spring boot
    implementation(libs.build.plugin.spring.boot)
    implementation(libs.build.plugin.spring.dependency.management)

    // kotlin
    implementation(libs.build.plugin.kotlin.gradle)
    implementation(libs.build.plugin.kotlin.spring)

    // openapi
    implementation(libs.build.plugin.openapi)

    // docker image
    implementation(libs.build.plugin.jib)

    // static code analysis
    implementation(libs.build.plugin.spotless)
    implementation(libs.build.plugin.detekt)

    // sql
    runtimeOnly(project(":sql-formatter"))
    runtimeOnly(project(":flyway-jooq-codegen"))
}
