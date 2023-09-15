package project.module.embedded

import io.hypersistence.tsid.TSID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import project.module.api.Resource
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
    fun `GIVEN valid resource WHEN save THEN insert and return persisted resource`() {
        // given
        val resource = Resource(TSID.fast())

        // when
        val saved = module.save(resource)

        // then
        assertThat(saved).isEqualTo(resource)
    }

    @Test
    fun `GIVEN existing resource id WHEN findById THEN return resource`() {
        // given
        val resourceId = TSID.fast()
        // and
        module.save(Resource(resourceId))

        // when
        val resource = module.findById(resourceId)

        // then
        assertThat(resource).isNotNull
        assertThat(resource!!.id).isEqualTo(resourceId)
    }

    @Test
    fun `GIVEN not existing resource id WHEN findById THEN return null`() {
        // given
        val resourceId = TSID.fast()

        // when
        val resource = module.findById(resourceId)

        // then
        assertThat(resource).isNull()
    }
}
