package project.build

import com.github.vertical_blank.sqlformatter.SqlFormatter
import com.github.vertical_blank.sqlformatter.core.FormatConfig
import com.github.vertical_blank.sqlformatter.languages.Dialect
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class SqlFormatPlugin : Plugin<Project> {
    private val formatter = SqlFormatter.of(Dialect.PostgreSql)
    private val config = FormatConfig.builder().linesBetweenQueries(2).build()

    override fun apply(target: Project) {
        with(target) {
            val scripts =
                files(layout.projectDirectory.dir("src/main/resources/db/migration")).filter { it.endsWith(".sql") }

            val checkTask = task("sqlFormatCheck") {
                inputs.files(scripts)
                outputs.files(scripts)

                doLast {
                    scripts
                        .map { file -> file to file.readText() }
                        .filterNot { (_, text) -> formatter.format(text, config) == text }
                        .map { (file, _) -> file.path }
                        .toList()
                        .takeIf { it.isNotEmpty() }
                        ?.let { "Following SQL scripts are not properly formatted:\n${it.joinToString("\n")}" }
                        ?.let { throw GradleException(it) }
                }
            }

            val applyTask = task("sqlFormatApply") {
                inputs.files(scripts)
                outputs.files(scripts)

                doLast {
                    scripts.forEach {
                        it.writeText(formatter.format(it.readText(), config))
                    }
                }
            }

            tasks.getByName("spotlessCheck").dependsOn(checkTask)
            tasks.getByName("spotlessApply").dependsOn(applyTask)
        }
    }
}
