package nz.adjmunro.util.test.common.junit5

import io.mockk.clearAllMocks
import io.mockk.mockkClass
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.test.mock.MockProvider

/**
 * JUnit5 [Extension][org.junit.jupiter.api.extension.Extension] that registers a MockK [MockProvider] for Koin.
 *
 * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
 * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
 *
 * @constructor
 * [Relaxed][KoinMockKExtension.Relaxed]
 * [Strict][KoinMockKExtension.Strict]
 * [create][KoinMockKExtension.create]
 *
 * @property relaxed `true` returns simple values for functions that return a value (to avoid specifying mocking behaviour for every case).
 * @property relaxUnitFun `true` allows functions that return `Unit` to be relaxed too.
 * @see BeforeCallback
 * @see AfterCallback
 * @see AggregateTestExtension
 */
public interface KoinMockKExtension: BeforeCallback, AfterCallback {

    /** `true` returns simple values for functions that return a value (to avoid specifying mocking behaviour for every case). */
    public val relaxed: Boolean

    /** `true` allows functions that return `Unit` to be relaxed too. */
    public val relaxUnitFun: Boolean

    override fun before(context: ExtensionContext) {
        MockProvider.register {
            mockkClass(type = it, relaxed = relaxed, relaxUnitFun = relaxUnitFun)
        }
    }

    override fun after(context: ExtensionContext) {
        clearAllMocks()
    }

    /**
     * A [KoinMockKExtension] that uses relaxed mocking.
     *
     * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
     * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
     *
     * ```kotlin
     * // Usage example:
     * @ExtendWith(KoinMockKExtension.Relaxed::class)
     * class MyTest { ... }
     * ```
     */
    public class Relaxed: KoinMockKExtension {
        override val relaxed: Boolean = true
        override val relaxUnitFun: Boolean = true
    }

    /**
     * A [KoinMockKExtension] that uses strict mocking.
     *
     * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
     * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
     *
     * ```kotlin
     * // Usage example:
     * @ExtendWith(KoinMockKExtension.Strict::class)
     * class MyTest { ... }
     * ```
     */
    public class Strict: KoinMockKExtension {
        override val relaxed: Boolean = false
        override val relaxUnitFun: Boolean = false
    }

    public companion object {
        /**
         * Creates a [KoinMockKExtension] with the specified configuration.
         *
         * *Configured on [**each**][org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD] test by default.
         * See [`@TestInstance`][org.junit.jupiter.api.TestInstance] for more information.*
         *
         * ```kotlin
         * // Usage example:
         * @RegisterExtension @JvmField
         * val mocker: KoinMockkExtension = KoinMockKExtension
         *     .create(relaxed = true, relaxUnitFun = false)
         * ```
         *
         * > *Prefer using the [Relaxed] or [Strict] subclasses for common configurations.*
         *
         * @param relaxed `true` returns simple values for functions that return a value (to avoid specifying mocking behaviour for every case).
         * @param relaxUnitFun `true` allows functions that return `Unit` to be relaxed too.
         */
        @JvmStatic
        public fun create(relaxed: Boolean = true, relaxUnitFun: Boolean = true): KoinMockKExtension {
            return object: KoinMockKExtension {
                override val relaxed: Boolean = relaxed
                override val relaxUnitFun: Boolean = relaxUnitFun
            }
        }
    }
}
