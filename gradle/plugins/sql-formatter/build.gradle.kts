plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.sql.formatter)
}

gradlePlugin {
    plugins {
        create("sql-formatter") {
            id = "project.build.sql-formatter"
            implementationClass = "project.build.SqlFormatPlugin"
        }
    }
}
