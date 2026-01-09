package nz.adjmunro.util.test.common.junit5

import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import kotlin.jvm.optionals.getOrDefault

/**
 * A JUnit5 [Extension][org.junit.jupiter.api.extension.Extension] aggregate interface that provides
 * a [after] method to be called either after [each][AfterEachCallback] test method or after
 * [all][AfterAllCallback] tests in a class, depending on the
 * [lifecycle][Lifecycle] of the
 * [TestInstance][org.junit.jupiter.api.TestInstance].
 *
 * *Configured on [**each**][Lifecycle.PER_METHOD] test by default.
 * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
 *
 * @see BeforeCallback
 * @see AfterAllCallback
 * @see AfterEachCallback
 */
public interface AfterCallback : AfterAllCallback, AfterEachCallback {

    override fun afterAll(context: ExtensionContext) {
        if (context matches Lifecycle.PER_CLASS) after(context)
    }

    override fun afterEach(context: ExtensionContext) {
        if (context matches Lifecycle.PER_METHOD) after(context)
    }
    
    public fun after(context: ExtensionContext)

    private infix fun ExtensionContext.matches(lifecycle: Lifecycle): Boolean =
        testInstanceLifecycle.getOrDefault(defaultValue = Lifecycle.PER_METHOD) == lifecycle
}
