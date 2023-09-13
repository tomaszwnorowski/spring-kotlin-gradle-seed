package project.test.tag

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Tag
import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.Inherited

const val INTEGRATION_TEST_ORDER = SMOKE_TEST_ORDER + 1
const val INTEGRATION_TEST_TAG = "integration"

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@ActiveProfiles(INTEGRATION_TEST_TAG)
@Tag(INTEGRATION_TEST_TAG)
@Order(INTEGRATION_TEST_ORDER)
annotation class IntegrationTest
