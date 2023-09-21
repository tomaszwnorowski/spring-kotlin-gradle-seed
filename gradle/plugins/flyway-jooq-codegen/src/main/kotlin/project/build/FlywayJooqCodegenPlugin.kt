package project.build

import org.flywaydb.core.Flyway
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.GenerationTool
import org.jooq.codegen.KotlinGenerator
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Target
import org.testcontainers.containers.PostgreSQLContainer

private const val DEFAULT_DOCKER_IMAGE = "postgres:15.0"

interface FlywayJooqCodegenExtension {
    val migrationsDir: DirectoryProperty
    val tables: SetProperty<String>
    val packageName: Property<String>
    val dockerImage: Property<String>
}

class FlywayJooqCodegenPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<FlywayJooqCodegenExtension>("flywayJooqCodegenExtension")
        val outputDir = target.layout.buildDirectory.dir("generated/sources/jooq/main/kotlin")

        val flywayJooqCodegenTask = target.task("flywayJooqCodegen") {
            inputs.dir(extension.migrationsDir)
                .withPropertyName("flyway-migrations")
                .withPathSensitivity(PathSensitivity.RELATIVE)
                .ignoreEmptyDirectories()

            outputs.dir(outputDir)
                .withPropertyName("jooq-target")

            doLast {
                with(extension) {
                    container(dockerImage.getOrElse(DEFAULT_DOCKER_IMAGE)) {
                        migrate(migrationsDir.get())
                        generate(outputDir.get(), tables.get(), packageName.get())
                    }
                }
            }
        }

        target.tasks.withType<KotlinCompile> {
            dependsOn(flywayJooqCodegenTask)
        }
    }

    private fun container(dockerImageName: String, codeBlock: PostgreSQLContainer<*>.() -> Unit) =
        PostgreSQLContainer(dockerImageName)
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("codegen")
            .also { it.start() }
            .use { codeBlock(it) }

    private fun PostgreSQLContainer<*>.migrate(location: Directory) =
        Flyway.configure()
            .dataSource(jdbcUrl, username, password)
            .locations("filesystem:${location.asFile.canonicalPath}")
            .load()
            .migrate()

    private fun PostgreSQLContainer<*>.generate(location: Directory, tables: Set<String>, packageName: String) =
        GenerationTool.generate(
            Configuration()
                .withLogging(Logging.WARN)
                .withJdbc(
                    Jdbc()
                        .withDriver(driverClassName)
                        .withUrl(jdbcUrl)
                        .withUser(username)
                        .withPassword(password)
                )
                .withGenerator(
                    Generator()
                        .withName(KotlinGenerator::class.qualifiedName)
                        .withDatabase(
                            Database()
                                .withExcludes("flyway_schema_history")
                                .withIncludes(tables.joinToString("|"))
                                .withInputSchema("public")
                                .withOutputSchemaToDefault(true)
                        )
                        .withTarget(
                            Target()
                                .withPackageName(packageName)
                                .withDirectory(location.asFile.canonicalPath)
                        )
                )
        )
}
