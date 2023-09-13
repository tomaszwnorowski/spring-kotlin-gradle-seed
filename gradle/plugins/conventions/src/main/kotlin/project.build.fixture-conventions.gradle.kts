plugins {
    id("java-test-fixtures")
}

dependencies {
    testFixtures(enforcedPlatform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
}
