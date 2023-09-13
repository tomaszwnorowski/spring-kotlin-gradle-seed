plugins {
    kotlin("jvm")
}

dependencies {
    implementation(enforcedPlatform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
}
