package project.module.embedded

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import project.core.ResourceId
import project.module.api.EphemeralModuleResource
import project.test.tag.IntegrationTest

@IntegrationTest
@SpringBootTest(
    classes = [ModuleEmbeddedConfiguration::class],
    properties = [
        "spring.datasource.url=jdbc:tc:postgresql:15.0://integration",
        "logging.level.org.jooq.tools.LoggerListener=DEBUG",
    ],
)
class ModuleEmbeddedTest(@Autowired private val module: ModuleEmbedded) {

    @Test
    fun `GIVEN valid command WHEN save THEN insert and return persisted resource`() {
        // given
        val command = EphemeralModuleResource("test")

        // when
        val resource = module.save(command)

        // then
        assertAll(
            { assertThat(resource.name).isEqualTo(command.name) },
            { assertThat(resource.id).isNotNull() },
        )
    }

    @Test
    fun `GIVEN existing resource id WHEN findById THEN return resource`() {
        // given
        val command = EphemeralModuleResource("test")

        // when
        val resource = module.save(command)

        // then
        assertThat(module.findById(resource.id)).isEqualTo(resource)
    }

    @Test
    fun `GIVEN not existing resource id WHEN findById THEN return null`() {
        // given
        val resourceId = ResourceId.generate()

        // when
        val resource = module.findById(resourceId)

        // then
        assertThat(resource).isNull()
    }
}
