package project.test.tag

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Tag
import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.Inherited

const val UNIT_TEST_ORDER = 0
const val UNIT_TEST_TAG = "unit"

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@ActiveProfiles(UNIT_TEST_TAG)
@Tag(UNIT_TEST_TAG)
@Order(UNIT_TEST_ORDER)
annotation class UnitTest
