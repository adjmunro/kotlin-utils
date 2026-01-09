package nz.adjmunro.util.test.dispatchers.junit5

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import nz.adjmunro.util.dispatchers.InjectDispatchersDsl

/**
 * Create [DispatchersExtension][DispatchersExtension] with
 * [StandardTestDispatcher][StandardTestDispatcher].
 *
 * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
 * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
 *
 * ```kotlin
 * // JUnit5 Usage:
 * @ExtendWith(DispatchersExtension.Standard::class)
 * class MyTest { ... }
 * ```
 *
 * > *When using [StandardTestDispatcher], remember to use [advanceUntilIdle][kotlinx.coroutines.test.advanceUntilIdle] in your tests!*
 *
 * @see DispatchersExtension
 * @see StandardTestDispatcher
 * @see kotlinx.coroutines.test.advanceUntilIdle
 * @see kotlinx.coroutines.test.runTest
 */
@InjectDispatchersDsl
public class StandardDispatcherExtension: DispatchersExtension {
    override val testDispatcher: TestDispatcher = StandardTestDispatcher()
}
