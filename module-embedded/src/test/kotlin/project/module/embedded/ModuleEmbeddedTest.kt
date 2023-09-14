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
    fun someTest() {
        // given
        val resource = Resource(TSID.fast())

        // when
        module.save(resource)

        // then
        assertThat(module.findById(resource.id)).isEqualTo(resource)
    }
}
