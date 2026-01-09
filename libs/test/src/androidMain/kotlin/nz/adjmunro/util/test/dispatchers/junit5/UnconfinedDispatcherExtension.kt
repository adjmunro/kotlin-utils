package nz.adjmunro.util.test.dispatchers.junit5

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import nz.adjmunro.util.dispatchers.InjectDispatchersDsl

/**
 * Create [DispatchersExtension][DispatchersExtension] with
 * [UnconfinedTestDispatcher][UnconfinedTestDispatcher].
 *
 * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
 * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
 *
 * ```kotlin
 * // JUnit5 Usage:
 * @ExtendWith(DispatchersExtension.Unconfined::class)
 * class MyTest { ... }
 * ```
 *
 * @see DispatchersExtension
 * @see UnconfinedTestDispatcher
 * @see kotlinx.coroutines.test.runTest
 */
@InjectDispatchersDsl
@OptIn(ExperimentalCoroutinesApi::class)
public class UnconfinedDispatcherExtension: DispatchersExtension {
    override val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
}
