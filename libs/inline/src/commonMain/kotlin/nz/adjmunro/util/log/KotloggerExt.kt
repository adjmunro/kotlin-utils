package nz.adjmunro.util.log

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 *  IMPORTANT!
 *  Do NOT use `inline` in this file!
 *  It prevents accurate callsite information from being captured!
 */

/**
 * Log the [caller][T] with the specified [priority].
 *
 * @param priority The priority of the log message.
 * @param predicate A predicate to determine if the logging of the caller should occur (defaults to always `true`).
 * @param message A function to format the caller (defaults to converting the caller to a string).
 */
@KotloggerDsl
public fun <T> T.log( // TODO expect with different defaults
    priority: KotloggerPriority,
    predicate: T.() -> Boolean = { true },
    // TODO pipe in the callsite information either to lambdas or as a parameter
    tag: String = Kotlogger.instance::class.simpleName ?: "Kotlogger",
    throwable: Throwable? = null,
    message: (T) -> Any? = { it },
): T {
    contract { callsInPlace(predicate, InvocationKind.EXACTLY_ONCE) }

    if (predicate()) {
        Kotlogger.instance.logger(priority = priority, tag = tag, message = message(this@log), throwable = throwable)
    }

    return this@log
}

public inline fun <reified T> from(line: Int): String {
    return "${T::class.simpleName}:$line"
}

/** Call the [verbose] logging function with default parameters. */
@KotloggerDsl
public val <T> T.verbose: T get() = verbose()

/** Call the [debug] logging function with default parameters. */
@KotloggerDsl
public val <T> T.debug: T get() = debug()

/** Call the [info] logging function with default parameters. */
@KotloggerDsl
public val <T> T.info: T get() = info()

/** Call the [warn] logging function with default parameters. */
@KotloggerDsl
public val <T> T.warn: T get() = warn()

/** Call the [err] logging function with default parameters. */
@KotloggerDsl
public val <T> T.err: T get() = err()

/** Call the [wtf] logging function with default parameters. */
@KotloggerDsl
public val <T> T.wtf: T get() = wtf()

/**
 * Log the [caller][T] with the [KotloggerPriority.VERBOSE] priority.
 *
 * @param predicate A predicate to determine if the logging of the caller should occur (defaults to always `true`).
 * @param formatter A function to format the caller (defaults to converting the caller to a string).
 */
@KotloggerDsl
public fun <T> T.verbose(
    predicate: T.() -> Boolean = { true },
    formatter: (T) -> Any = { toString() },
): T = log(priority = KotloggerPriority.VERBOSE, predicate = predicate, message = formatter)

/**
 * Log the [caller][T] with the [KotloggerPriority.DEBUG] priority.
 *
 * @param predicate A predicate to determine if the logging of the caller should occur (defaults to always `true`).
 * @param formatter A function to format the caller (defaults to converting the caller to a string).
 */
@KotloggerDsl
public fun <T> T.debug(
    predicate: T.() -> Boolean = { true },
    formatter: (T) -> Any = { toString() },
): T = log(priority = KotloggerPriority.DEBUG, predicate = predicate, message = formatter)

/**
 * Log the [caller][T] with the [KotloggerPriority.INFO] priority.
 *
 * @param predicate A predicate to determine if the logging of the caller should occur (defaults to always `true`).
 * @param formatter A function to format the caller (defaults to converting the caller to a string).
 */
@KotloggerDsl
public fun <T> T.info(
    predicate: T.() -> Boolean = { true },
    formatter: (T) -> Any = { toString() },
): T = log(priority = KotloggerPriority.INFO, predicate = predicate, message = formatter)

/**
 * Log the [caller][T] with the [KotloggerPriority.WARN] priority.
 *
 * @param predicate A predicate to determine if the logging of the caller should occur (defaults to always `true`).
 * @param formatter A function to format the caller (defaults to converting the caller to a string).
 */
@KotloggerDsl
public fun <T> T.warn(
    predicate: T.() -> Boolean = { true },
    formatter: (T) -> Any = { toString() },
): T = log(priority = KotloggerPriority.WARN, predicate = predicate, message = formatter)

/**
 * Log the [caller][T] with the [KotloggerPriority.ERROR] priority.
 *
 * @param predicate A predicate to determine if the logging of the caller should occur (defaults to always `true`).
 * @param formatter A function to format the caller (defaults to converting the caller to a string).
 */
@KotloggerDsl
public fun <T> T.err(
    predicate: T.() -> Boolean = { true },
    formatter: (T) -> Any = { toString() },
): T = log(priority = KotloggerPriority.ERROR, predicate = predicate, message = formatter)

/**
 * Log the [caller][T] with the [KotloggerPriority.ASSERT] priority.
 *
 * @param predicate A predicate to determine if the logging of the caller should occur (defaults to always `true`).
 * @param formatter A function to format the caller (defaults to converting the caller to a string).
 */
@KotloggerDsl
public fun <T> T.wtf(
    predicate: T.() -> Boolean = { true },
    formatter: (T) -> Any = { toString() },
): T = log(priority = KotloggerPriority.ASSERT, predicate = predicate, message = formatter)
