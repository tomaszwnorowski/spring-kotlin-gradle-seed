package project.test.tag

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Tag
import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.Inherited

const val SMOKE_TEST_ORDER = UNIT_TEST_ORDER + 1
const val SMOKE_TEST_TAG = "smoke"

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@ActiveProfiles(SMOKE_TEST_TAG)
@Tag(SMOKE_TEST_TAG)
@Order(SMOKE_TEST_ORDER)
annotation class SmokeTest
