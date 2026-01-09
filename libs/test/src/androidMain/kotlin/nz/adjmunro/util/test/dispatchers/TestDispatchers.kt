package nz.adjmunro.util.test.dispatchers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import nz.adjmunro.util.dispatchers.InjectDispatchers
import nz.adjmunro.util.dispatchers.InjectDispatchersDsl
import nz.adjmunro.util.dispatchers.KotlinDispatchers
import nz.adjmunro.util.dispatchers.RuntimeDispatchers

/**
 * A simple implementation of [InjectDispatchers] that uses a single [TestDispatcher] for all dispatcher types.
 *
 * This is useful for testing, where you want to ensure that all coroutines run on the same dispatcher,
 * making it easier to control and predict their behavior.
 *
 * ```kotlin
 * // Before tests:
 * InjectDispatchers.setMain(testDispatcher)
 *
 * // After tests:
 * InjectDispatchers.resetMain()
 * ```
 *
 * @property dispatcher The [TestDispatcher] to be used for all dispatcher types.
 *
 * @see InjectDispatchers
 * @see kotlinx.coroutines.test.TestDispatcher
 */
@InjectDispatchersDsl @JvmInline
public value class TestDispatchers(
    public val dispatcher: TestDispatcher,
) : InjectDispatchers {
    @InjectDispatchersDsl
    override val default: TestDispatcher
        get() = dispatcher
    @InjectDispatchersDsl
    override val immediate: TestDispatcher
        get() = dispatcher
    @InjectDispatchersDsl
    override val io: TestDispatcher
        get() = dispatcher
    @InjectDispatchersDsl
    override val main: TestDispatcher
        get() = dispatcher
    @InjectDispatchersDsl
    override val unconfined: TestDispatcher
        get() = dispatcher

    public companion object {
        /**
         * Equivalent to [KotlinDispatchers.setMain], but also assigns all injectable dispatchers to the same [TestDispatcher].
         *
         * This is used in testing scenarios to ensure that all coroutines run on a controlled dispatcher.
         *
         * ```kotlin
         * // Before tests:
         * TestDispatchers.setMain(testDispatcher)
         * ```
         *
         * @param dispatcher The [TestDispatcher] to set as the main dispatcher.
         *
         * @see kotlinx.coroutines.test.setMain
         * @see InjectDispatchers
         */
        @InjectDispatchersDsl
        @OptIn(ExperimentalCoroutinesApi::class)
        public fun setMain(dispatcher: TestDispatcher) {
            // Set the main dispatcher to the test dispatcher for kotlinx.coroutines
            KotlinDispatchers.setMain(dispatcher = dispatcher)

            // Set the Dispatchers' singleton to the test dispatcher for other injectable dispatchers
            InjectDispatchers.singleton = TestDispatchers(dispatcher = dispatcher)
        }

        /**
         * Equivalent to [KotlinDispatchers.resetMain], but also resets the injectable dispatchers to [RuntimeDispatchers].
         *
         * This is used in testing scenarios to clean up after tests, ensuring that the main dispatcher and injectable dispatchers
         * are returned to their default states.
         *
         * ```kotlin
         * // After tests:
         * TestDispatchers.resetMain()
         * ```
         *
         * @see kotlinx.coroutines.test.resetMain
         * @see InjectDispatchers
         */
        @InjectDispatchersDsl
        @OptIn(ExperimentalCoroutinesApi::class)
        public fun resetMain() {
            // Reset the Dispatchers' singleton to the default for other injectable dispatchers
            InjectDispatchers.singleton = RuntimeDispatchers

            // Reset the main dispatcher to the default for kotlinx.coroutines
            KotlinDispatchers.resetMain()
        }
    }
}
