plugins {
    id("com.diffplug.spotless")
    id("project.build.sql-formatter")
}

spotless {
    kotlin {
        ktlint("0.50.0")
        targetExclude("build/generated/**")

        custom("No wildcard imports") {
            if ("\\nimport .*\\*".toRegex().containsMatchIn(it)) {
                throw AssertionError("Wildcard imports, static or otherwise, must not be used")
            }
            it
        }
    }

    json {
        target("src/**/*.json")
        jackson()
    }

    format("misc") {
        target(
            "**/*.gradle.kts",
            "**/*.md",
            "**/.gitignore",
            "**/*.sql",
            "**/*.json"
        )

        trimTrailingWhitespace()
    }
}
