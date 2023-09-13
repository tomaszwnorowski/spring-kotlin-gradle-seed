package project.test.interceptor

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.InvocationInterceptor
import org.junit.jupiter.api.extension.ReflectiveInvocationContext
import project.test.tag.INTEGRATION_TEST_TAG
import project.test.tag.SMOKE_TEST_TAG
import project.test.tag.UNIT_TEST_TAG
import java.lang.reflect.Constructor

class CheckTestTagInterceptor : InvocationInterceptor {

    override fun <T : Any?> interceptTestClassConstructor(
        invocation: InvocationInterceptor.Invocation<T>,
        invocationContext: ReflectiveInvocationContext<Constructor<T>>,
        extensionContext: ExtensionContext,
    ): T {
        with(extensionContext.tags) {
            check(isNotEmpty()) { message }
            check(any { tag -> executionOrderTags.contains(tag) }) { message }
        }
        return super.interceptTestClassConstructor(invocation, invocationContext, extensionContext)
    }

    companion object {
        private val executionOrderTags = setOf(UNIT_TEST_TAG, SMOKE_TEST_TAG, INTEGRATION_TEST_TAG)
        private val message = "Test class needs to be annotated with one of the tags $executionOrderTags"
    }
}
