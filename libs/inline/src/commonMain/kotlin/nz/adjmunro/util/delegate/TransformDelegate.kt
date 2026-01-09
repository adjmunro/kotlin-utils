@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.delegate

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @return Creates a [TransformDelegate] with the given `transform` function.
 * @see TransformDelegate
 */
public fun <T, R> ReadOnlyProperty<Any?, T>.map(transform: (T) -> R): TransformDelegate<T, R> {
    return TransformDelegate(sourceDelegate = this@map, transform = transform)
}

/**
 * A higher-order delegate to [transform] the output value of another delegate.
 *
 * *This is equivalent to overriding the generated property accessor function.*
 *
 * ```kotlin
 * // Suppose sourceDelegate is a ReadOnlyProperty<Int> with a value of 42.
 * // And transform: (Int) -> String = { it.toString() }.
 *
 * // Roughly equivalent to: (but with delegation)
 * val a: String
 *     get() = transform(sourceDelegate.getValue(thisRef, property))
 * //        = transform(42)
 * //        = "42"
 *
 * // Example Usage:
 * val b: String by weakReference(42).map { it.toString() } // = "42"
 * ```
 *
 * @param T The type of the source delegate.
 * @param R The type of the transformed output.
 * @param sourceDelegate The source delegate to transform.
 * @property transform The transformation applied to the output of the source delegate.
 *
 * @see TransformDelegate.map
 */
public class TransformDelegate<T, R> internal constructor(
    sourceDelegate: ReadOnlyProperty<Any?, T>,
    private val transform: (T) -> R,
) : ReadOnlyProperty<Any?, R> {

    private val source: T by sourceDelegate

    override fun getValue(thisRef: Any?, property: KProperty<*>): R {
        return transform(source)
    }
}
