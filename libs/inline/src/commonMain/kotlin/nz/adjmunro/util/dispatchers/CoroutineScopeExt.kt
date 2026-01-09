package nz.adjmunro.util.dispatchers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import nz.adjmunro.util.dispatchers.Dispatchers.Companion.Default
import nz.adjmunro.util.dispatchers.Dispatchers.Companion.foreverScope
import kotlin.coroutines.CoroutineContext

/**
 * A local [CoroutineScope] that is not tied to any lifecycle.
 *
 * *This is useful for ending a Job with [join][Job.join]/[cancel][Job.cancel] or
 * for launching coroutines that outlive a parent scope.*
 *
 * @see localScope
 */
public inline val localScope: CoroutineScope get() = localScope()

/**
 * Creates a local [CoroutineScope] that is not tied to any lifecycle.
 *
 * *This is useful for ending a Job with [join][Job.join]/[cancel][Job.cancel] or
 * for launching coroutines that outlive a parent scope.*
 *
 * @param context [CoroutineContext] to use for the scope. Defaults to a [SupervisorJob] and the [Default] dispatcher.
 * @return A [CoroutineScope] that can be used for launching coroutines.
 * @see Dispatchers.foreverScope
 */
public fun localScope(context: CoroutineContext = SupervisorJob() + Default): CoroutineScope {
    return CoroutineScope(context)
}

/**
 * Used for cleanup operations that should not be cancelled.
 *
 * ## Use with caution!
 * Doing this is very risky as you lose control of the coroutine's execution.
 * - You won’t be able to stop those operations in tests.
 * - An endless loop that uses delay won’t be able to cancel anymore.
 * - Collecting a Flow within it makes the Flow non-cancellable from the outside.
 *
 * These problems can lead to subtle and very hard to debug bugs,
 * which is why you **should only** use it for
 * [cleanup](https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad).
 *
 * *Prefer using [localScope] or [foreverScope] for operations that should not be cancelled.*
 *
 * @see NonCancellable
 */
public suspend fun <T> cleanupContext(block: suspend CoroutineScope.() -> T): T {
    return withContext(context = NonCancellable, block = block)
}
