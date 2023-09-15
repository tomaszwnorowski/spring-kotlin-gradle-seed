package project.rest

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import project.module.api.ModuleApi
import project.module.fixture.ModuleFixture.resource
import project.test.tag.IntegrationTest

@IntegrationTest
@WebMvcTest(ResourcesRestController::class)
class ResourcesRestControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockkBean
    private lateinit var api: ModuleApi

    @Test
    fun `GIVEN existing resource id WHEN get resource THEN return resource`() {
        every { api.findById(resource.id) } returns resource

        mvc.perform(get("/public/api/v1/resources/${resource.id.toLowerCase()}"))
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                {
                    "id": "${resource.id.toLowerCase()}"
                }
                    """.trimIndent(),
                ),
            )
    }

    @Test
    fun `GIVEN missing resource id WHEN get resource THEN return not found`() {
        every { api.findById(resource.id) } returns null

        mvc.perform(get("/public/api/v1/resources/${resource.id.toLowerCase()}"))
            .andExpect(status().isNotFound)
    }

    @SpringBootApplication
    private class ResourcesRestControllerTestApplication
}
