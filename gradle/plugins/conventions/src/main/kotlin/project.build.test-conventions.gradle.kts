plugins {
    id("project.build.main-conventions")

    jacoco
}

dependencies {
    testImplementation(platform(dependencyFromLibs("bom-testcontainers")))
    testImplementation(project(":test"))
}

val test = tasks.named("test", Test::class) {
    useJUnitPlatform()
    systemProperty("junit.jupiter.testclass.order.default", "org.junit.jupiter.api.ClassOrderer\$OrderAnnotation")
    systemProperty("junit.jupiter.extensions.autodetection.enabled", "true")
}
