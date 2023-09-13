plugins {
    id("project.build.spring-boot-library-conventions")
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.spring.boot.starter.test)
}
