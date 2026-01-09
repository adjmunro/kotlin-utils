@file:Suppress("NOTHING_TO_INLINE", "unused")

package nz.adjmunro.util.inline

/**
 * Syntax-sugar for a lambda that returns `it`.
 *
 * ```kotlin
 * fun <T> T.map(transform: (T) -> T): T
 *
 * // Before:
 * map(transform = { it })
 *
 * // After:
 * map(transform = ::itself)
 * ```
 *
 * @return The first argument passed to the lambda.
 */
@InlineDsl
public inline fun <T> itself(value: T): T = value

/**
 * Syntax-sugar for a lambda that returns `this`.
 *
 * ```kotlin
 * fun <T> T.map(transform: T.() -> T): T
 *
 * // Before:
 * map(transform = { this })
 *
 * // After:
 * map(transform = ::caller)
 * ```
 *
 * @return The receiver of the lambda.
 */
@InlineDsl
public inline fun <T> T.caller(ignore: Any? = null): T = this@caller

/**
 * Syntax-sugar for a lambda that throws `it` (provided `it` is a [Throwable]).
 *
 * ```kotlin
 * fun <T> T.map(transform: (T) -> T): T
 *
 * // Before:
 * map(transform = { throw it })
 *
 * // After:
 * map(transform = ::rethrow)
 * ```
 *
 * @throws [Throwable] passed as the first argument to the lambda.
 */
@InlineDsl
public inline fun rethrow(throwable: Throwable): Nothing = throw throwable

/**
 * Syntax-sugar for a lambda that returns `null`.
 *
 * ```kotlin
 * fun <T> T.map(transform: (T) -> T?): T?
 *
 * // Before:
 * map(transform = { null })
 *
 * // After:
 * map(transform = ::nulls)
 * ```
 *
 * @return `null`.
 */
@InlineDsl
public inline fun nulls(ignore: Any? = null): Nothing? = null

/**
 * Syntax-sugar for a lambda that returns [Unit].
 *
 * ```kotlin
 * fun <T> T.map(transform: (T) -> Unit): Unit
 *
 * // Before:
 * map(transform = { /* do nothing */ })
 *
 * // After:
 * map(transform = ::unit)
 * ```
 *
 * @return [Unit].
 */
@InlineDsl
public inline fun unit(ignore: Any? = null): Unit = Unit

/**
 * Syntax-sugar for a lambda that always returns `true`.
 *
 * ```kotlin
 * fun <T> T.filter(predicate: (T) -> Boolean): Boolean
 *
 * // Before:
 * filter(predicate = { true })
 *
 * // After:
 * filter(predicate = ::truth)
 * ```
 *
 * @return `true`.
 */
@InlineDsl
public inline fun truth(ignore: Any? = null): Boolean = true

/**
 * Syntax-sugar for a lambda that always returns `false`.
 *
 * ```kotlin
 * fun <T> T.filter(predicate: (T) -> Boolean): Boolean
 *
 * // Before:
 * filter(predicate = { false })
 *
 * // After:
 * filter(predicate = ::falsehood)
 * ```
 *
 * @return `false`.
 */
@InlineDsl
public inline fun falsehood(ignore: Any? = null): Boolean = false
