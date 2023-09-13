plugins {
    kotlin("jvm")

    id("org.openapi.generator")
}

openApiGenerate {
    generatorName.set("kotlin")
    outputDir.set(project.layout.buildDirectory.dir("generated/sources/openapi/main/kotlin").get().asFile.canonicalPath)
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "serializationLibrary" to "jackson",
            "sourceFolder" to ""
        )
    )
    globalProperties.put("models", "")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(tasks.getByName("openApiGenerate"))
}
