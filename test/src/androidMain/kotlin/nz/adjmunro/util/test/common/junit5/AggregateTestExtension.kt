package nz.adjmunro.util.test.common.junit5

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import nz.adjmunro.util.log.SystemPrintlnTree
import nz.adjmunro.util.test.dispatchers.junit5.DispatchersExtension
import nz.adjmunro.util.test.log.junit5.TimberExtension
import org.junit.jupiter.api.extension.ExtensionContext
import timber.log.Timber

/**
 * JUnit5 [Extension][org.junit.jupiter.api.extension.Extension] that aggregates:
 * - [DispatchersExtension][DispatchersExtension] for managing test dispatchers.
 * - [KoinMockKExtension][KoinMockKExtension] for mocking dependencies with MockK.
 * - [TimberExtension][TimberExtension] for logging with Timber.
 *
 * > *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
 * > See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
 *
 * ```kotlin
 * // JUnit5 Usage (for alternative configurations):
 * @ExtendWith(AggregateTestExtension::class)
 * class MyTest { ... }
 * ```
 *
 * @param DispatchersExtension.testDispatcher
 * @param KoinMockKExtension.relaxed
 * @param KoinMockKExtension.relaxUnitFun
 * @param TimberExtension.tree
 */
@OptIn(ExperimentalCoroutinesApi::class)
public class AggregateTestExtension(
    override val relaxed: Boolean = true,
    override val relaxUnitFun: Boolean = true,
    override val tree: Timber.Tree = SystemPrintlnTree(),
    override val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : DispatchersExtension, KoinMockKExtension, TimberExtension {

    override fun before(context: ExtensionContext) {
        // Set the main dispatcher & Dispatchers' singleton to the test dispatcher
        super<DispatchersExtension>.before(context)

        // Plant the Timber tree for logging
        super<TimberExtension>.before(context)

        // Register MockK as the mock provider for Koin
        super<KoinMockKExtension>.before(context)
    }

    override fun after(context: ExtensionContext) {
        // Clear all mocks created by MockK
        super<KoinMockKExtension>.after(context)

        // Remove the Timber tree
        super<TimberExtension>.after(context)

        // Reset the Dispatchers' singleton & main dispatcher to the default
        super<DispatchersExtension>.after(context)
    }

    public companion object {
        /**
         * Creates an [AggregateTestExtension] with custom parameters.
         *
         * > Prefer [ExtendWith][org.junit.jupiter.api.extension.ExtendWith] when using default parameters.
         * > *See [AggregateTestExtension][AggregateTestExtension] class documentation.*
         *
         * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
         * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
         *
         * ```kotlin
         * // JUnit5 Usage (for alternative configurations):
         * class MyTest {
         *     @RegisterExtension @JvmField
         *     val aggregateTestExtension = AggregateTestExtension.create(
         *         relaxed = true,
         *         relaxUnitFun = true,
         *         tree = SystemPrintlnTree(),
         *         testDispatcher = UnconfinedTestDispatcher(),
         *     )
         * }
         * ```
         *
         * @param DispatchersExtension.testDispatcher
         * @param KoinMockKExtension.relaxed
         * @param KoinMockKExtension.relaxUnitFun
         * @param TimberExtension.tree
         * @return An instance of [AggregateTestExtension] configured with the provided parameters.
         */
        @JvmStatic
        public fun create(
            relaxed: Boolean = true,
            relaxUnitFun: Boolean = true,
            tree: Timber.Tree = SystemPrintlnTree(),
            testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
        ): AggregateTestExtension = AggregateTestExtension(
            relaxed = relaxed,
            relaxUnitFun = relaxUnitFun,
            tree = tree,
            testDispatcher = testDispatcher
        )
    }
}
