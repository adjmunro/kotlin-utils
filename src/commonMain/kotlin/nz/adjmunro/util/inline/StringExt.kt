@file:Suppress("NOTHING_TO_INLINE")

package nz.adjmunro.util.inline

/**
 * Syntax-sugar for a lambda that returns the [String] of `it`.
 *
 * ```kotlin
 * fun <T> T.map(transform: (T) -> String): String
 *
 * // Before:
 * map(transform = { "$it" })
 *
 * // After:
 * map(transform = ::stringItself)
 * ```
 *
 * @return String of the first argument passed to the lambda.
 */
@InlineDsl
public inline fun <T> stringItself(value: T): String = value.toString()

/**
 * Syntax-sugar for a lambda that returns the [String] of `this`.
 *
 * ```kotlin
 * fun <T> T.map(transform: T.() -> String): String
 *
 * // Before:
 * map(transform = { "$this" })
 *
 * // After:
 * map(transform = ::stringCaller)
 * ```
 *
 * @return String of the lambda's receiver.
 */
@InlineDsl
public inline fun <T> T.stringCaller(ignore: Any? = null): String = this@stringCaller.toString()

/**
 * Syntax-sugar for a lambda that returns an empty [String].
 *
 * ```kotlin
 * fun <T> T.map(transform: (T) -> String): String
 *
 * // Before:
 * map(transform = { "" })
 *
 * // After:
 * map(transform = ::emptyString)
 * ```
 *
 * @return An empty [String].
 */
@InlineDsl
public inline fun emptyString(ignore: Any? = null): String = ""

/**
 * Ensures that the [String] contains valid content.
 *
 * @return The receiver or `null` if the [String] is [empty][String.isEmpty], [blank][String.isBlank], `null`, or `"null"`.
 */
@InlineDsl
public inline fun String?.orNull(): String? = takeUnless { it.isNullOrBlank() || it == "null" }
