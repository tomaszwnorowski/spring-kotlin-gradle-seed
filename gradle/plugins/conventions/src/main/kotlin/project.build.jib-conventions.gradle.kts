plugins {
    id("com.google.cloud.tools.jib")
}

jib {
    from {
        image = "azul/zulu-openjdk:17.0.8-17.44.15"
    }
    container {
        user = "nobody"
        ports = listOf("8080")
        jvmFlags = listOf(
            "-Dorg.jooq.no-logo=true",
            "-Dorg.jooq.no-tips=true",
            "-Dspring.main.banner-mode=off"
        )
    }
}
