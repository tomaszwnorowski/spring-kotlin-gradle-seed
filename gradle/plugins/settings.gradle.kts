dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}

rootProject.name = "plugins"

include("conventions")
include("flyway-jooq-codegen")
include("sql-formatter")
