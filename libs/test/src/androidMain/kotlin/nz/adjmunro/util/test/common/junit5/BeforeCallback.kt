package nz.adjmunro.util.test.common.junit5

import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import kotlin.jvm.optionals.getOrDefault

/**
 * A JUnit5 [Extension][org.junit.jupiter.api.extension.Extension] aggregate interface that provides
 * a [before] method to be called either before [each][BeforeEachCallback] test method or before
 * [all][BeforeAllCallback] tests in a class, depending on the
 * [lifecycle][Lifecycle] of the
 * [TestInstance][org.junit.jupiter.api.TestInstance].
 *
 * *Configured on [**each**][Lifecycle.PER_METHOD] test by default.
 * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
 *
 * @see AfterCallback
 * @see BeforeAllCallback
 * @see BeforeEachCallback
 */
public interface BeforeCallback : BeforeAllCallback, BeforeEachCallback {

    override fun beforeAll(context: ExtensionContext) {
        if (context matches Lifecycle.PER_CLASS) before(context)
    }

    override fun beforeEach(context: ExtensionContext) {
        if (context matches Lifecycle.PER_METHOD) before(context)
    }
    
    public fun before(context: ExtensionContext)

    private infix fun ExtensionContext.matches(lifecycle: Lifecycle): Boolean =
        testInstanceLifecycle.getOrDefault(defaultValue = Lifecycle.PER_METHOD) == lifecycle
}
