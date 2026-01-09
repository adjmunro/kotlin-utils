package nz.adjmunro.util.test.dispatchers.junit4

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import nz.adjmunro.util.dispatchers.InjectDispatchersDsl
import nz.adjmunro.util.test.dispatchers.TestDispatchers
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A JUnit4 Rule that sets a [kotlinx.coroutines.test.TestDispatcher] as the main dispatcher before each test and resets it after each test.
 *
 * ```kotlin
 * // JUnit 4 Usage:
 * class MyClassTest {
 *   @get:Rule
 *   val dispatcherRule = DispatchersRule(StandardTestDispatcher())
 * }
 * ```
 *
 * > You can either use [kotlinx.coroutines.test.UnconfinedTestDispatcher] or [StandardTestDispatcher][kotlinx.coroutines.test.StandardTestDispatcher] as the test dispatcher.
 * > *If using [StandardTestDispatcher][kotlinx.coroutines.test.StandardTestDispatcher], remember to use [advanceUntilIdle][kotlinx.coroutines.test.advanceUntilIdle] in your tests!*
 *
 * @see kotlinx.coroutines.test.StandardTestDispatcher
 * @see kotlinx.coroutines.test.UnconfinedTestDispatcher
 * @see kotlinx.coroutines.test.advanceUntilIdle
 * @see kotlinx.coroutines.test.runTest
 */
@InjectDispatchersDsl
@OptIn(ExperimentalCoroutinesApi::class)
public class DispatchersRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)

        // Set the main dispatcher to the test dispatcher
        // Set the Dispatchers' singleton to the test dispatcher
        TestDispatchers.Companion.setMain(dispatcher = testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)

        // Reset the Dispatchers' singleton to the default
        // Reset the main dispatcher to the default
        TestDispatchers.Companion.resetMain()
    }
}
