plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(versionFromLibs("jvm")))
    }
}

repositories {
    mavenCentral()
}

configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
    // fix for detached configurations
    applyMavenExclusions(false)
}

dependencies {
    implementation(enforcedPlatform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = versionFromLibs("jvm")
    }
}
