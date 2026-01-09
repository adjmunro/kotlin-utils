package nz.adjmunro.util.test.dispatchers.junit5

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import nz.adjmunro.util.dispatchers.InjectDispatchersDsl
import nz.adjmunro.util.test.dispatchers.TestDispatchers
import nz.adjmunro.util.test.common.junit5.AfterCallback
import nz.adjmunro.util.test.common.junit5.BeforeCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

/**
 * A JUnit5 [org.junit.jupiter.api.extension.Extension] that sets a [TestDispatcher][TestDispatcher] as the
 * [MainCoroutineDispatcher][kotlinx.coroutines.MainCoroutineDispatcher] and [Dispatchers.singleton][nz.adjmunro.util.dispatchers.Dispatchers.Companion.singleton]
 * before [each][org.junit.jupiter.api.extension.BeforeEachCallback]/[all][org.junit.jupiter.api.extension.BeforeAllCallback] test(s) and resets it after
 * [each][org.junit.jupiter.api.extension.AfterEachCallback]/[all][org.junit.jupiter.api.extension.AfterAllCallback] test(s).
 *
 * ### Each vs All
 * You can use [`@TestInstance`][TestInstance][`(TestInstance.Lifecycle.PER_CLASS)`][org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS] to enable `BeforeAll` and `AfterAll`
 * behaviour for this extension. *However, this disabled parallel execution of tests in the same class
 * **unless** [`@Execution(CONCURRENT)`][Execution] is also used.*
 *
 * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
 * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
 *
 * @constructor
 * [Standard][DispatchersExtension.Standard]
 * [Unconfined][DispatchersExtension.Unconfined]
 * [create][DispatchersExtension.create]
 *
 * @property testDispatcher The [TestDispatcher][TestDispatcher] to use for testing.
 * @see kotlinx.coroutines.test.advanceUntilIdle
 * @see kotlinx.coroutines.test.runTest
 * @see nz.adjmunro.util.test.common.junit5.AggregateTestExtension
 */
@InjectDispatchersDsl
@OptIn(ExperimentalCoroutinesApi::class)
@Execution(ExecutionMode.CONCURRENT)
public interface DispatchersExtension : BeforeCallback, AfterCallback {

    /** The [TestDispatcher][TestDispatcher] to use for testing. */
    @InjectDispatchersDsl
    public val testDispatcher: TestDispatcher

    override fun before(context: ExtensionContext) {
        // Set the main dispatcher to the test dispatcher
        // Set the Dispatchers' singleton to the test dispatcher
        TestDispatchers.Companion.setMain(dispatcher = testDispatcher)
    }

    override fun after(context: ExtensionContext) {
        // Reset the Dispatchers' singleton to the default
        // Reset the main dispatcher to the default
        TestDispatchers.Companion.resetMain()
    }

    public companion object Companion {
        /**
         * Creates a [DispatchersExtension] with the given [testDispatcher].
         *
         * > *Prefer [DispatchersExtension.Standard][DispatchersExtension.Standard]
         * > or [DispatchersExtension.Unconfined][DispatchersExtension.Unconfined].*
         *
         * ```
         * // JUnit5 Usage:
         * class Test {
         *     @RegisterExtension @JvmField
         *     val dispatcherExtension = DispatchersExtension.create(StandardTestDispatcher())
         * }
         * ```
         *
         * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
         * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
         *
         * @param testDispatcher The [TestDispatcher] to use for the extension.
         * @return A new instance of [DispatchersExtension].
         */
        @JvmStatic
        public fun create(testDispatcher: TestDispatcher): DispatchersExtension {
            return object : DispatchersExtension {
                override val testDispatcher: TestDispatcher = testDispatcher
            }
        }
    }
}
