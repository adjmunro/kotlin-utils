@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.concurrent.async

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import java.util.concurrent.CancellationException

/**
 * Extension functions for [Deferred] (async/await).
 */
public object DeferredExt {

    /**
     * @return [Result] of [Deferred.await].
     *
     * @see Deferred.await
     */
    public suspend fun <T> Deferred<T>.awaitResult(): Result<T> {
        return try {
            Result.success(await())
        } catch (e: CancellationException) {
            // Rethrows if the current coroutine was cancelled
            currentCoroutineContext().ensureActive()

            // If this line executes, the exception is the result of `await` itself
            Result.failure(e)
        }
    }

}
