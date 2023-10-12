package project

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import project.rest.CreateRestResource
import project.rest.RestResource
import project.test.tag.SmokeTest

@SmokeTest
@SpringBootTest(
    properties = [
        "spring.datasource.url=jdbc:tc:postgresql:15.0://smoke",
        "logging.level.org.jooq.tools.LoggerListener=DEBUG",
    ],
    webEnvironment = WebEnvironment.RANDOM_PORT,
)
@AutoConfigureWebTestClient
class ProjectSmokeTest {

    @Autowired
    private lateinit var http: WebTestClient

    @LocalServerPort
    private var port: Int = 0

    @Test
    fun `should return newly created resource`() {
        // given
        val uri = "http://localhost:$port/public/api/v1/resources"

        // when
        val postResource = http.post()
            .uri(uri)
            .bodyValue(CreateRestResource("rest"))
            .exchange()
            .expectStatus().isOk
            .expectBody(RestResource::class.java)
            .returnResult()
            .responseBody!!

        // and
        val getResource = http.get()
            .uri("$uri/${postResource.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody(RestResource::class.java)
            .returnResult()
            .responseBody!!

        // then
        assertThat(getResource).isEqualTo(postResource)
    }
}
