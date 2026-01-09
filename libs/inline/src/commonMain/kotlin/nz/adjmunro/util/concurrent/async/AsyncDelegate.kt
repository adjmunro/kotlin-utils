@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.concurrent.async

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import nz.adjmunro.util.dispatchers.Dispatchers
import nz.adjmunro.util.concurrent.async.AsyncDelegate.Companion.await
import nz.adjmunro.util.concurrent.async.AsyncDelegate.Companion.awaitFirst
import nz.adjmunro.util.concurrent.async.AsyncDelegate.Companion.awaitOrDefault
import nz.adjmunro.util.concurrent.async.AsyncDelegate.Companion.awaitOrThrow
import nz.adjmunro.util.dispatchers.flow.firstAsResult
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A delegate to asynchronously initialise a property from a `suspend` function.
 * Backed by [Deferred], this delegate handles the `async`/`await` operation.
 *
 * *Note: [getValue] is a blocking operation!*
 *
 * @param T The type obtained from [getValue].
 * @param scope [CoroutineScope] for `block`.
 * @param start [CoroutineStart] for `block`.
 * @param asyncContext [CoroutineContext] for [CoroutineScope.async] of `block`.
 * @param awaitContext [CoroutineContext] for [Deferred.await] on [get].
 * @param block The asynchronous operation to run to get the value.
 *
 * @see AsyncDelegate.getValue
 * @see Companion.await
 * @see Companion.awaitOrThrow
 * @see Companion.awaitOrDefault
 * @see Companion.awaitFirst
 * @see CoroutineScope.async
 * @see Deferred.await
 */
public class AsyncDelegate<out T> private constructor(
    scope: CoroutineScope,
    start: CoroutineStart,
    asyncContext: CoroutineContext,
    private val awaitContext: CoroutineContext,
    block: suspend CoroutineScope.() -> T,
) : ReadOnlyProperty<Any?, T> {

    private val deferred: Deferred<T> = scope.async(
        context = asyncContext,
        start = start,
        block = block,
    )

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return runBlocking(context = awaitContext) { deferred.await() }
    }

    public companion object {

        /**
         * @return An [AsyncDelegate] for any [CoroutineScope].
         *
         * @see AsyncDelegate
         */
        public fun <T> CoroutineScope.await(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> T,
        ): AsyncDelegate<T> = AsyncDelegate(
            scope = this@await,
            start = start,
            asyncContext = asyncOn,
            awaitContext = awaitOn,
            block = block,
        )

        /**
         * @return
         * An [AsyncDelegate] that unwraps a [Result]
         * and throws an exception if [Result.isFailure].
         *
         * @throws Throwable The encapsulated exception of the [Result.Failure].
         *
         * @see AsyncDelegate
         * @see Result.getOrThrow
         */
        public fun <T> CoroutineScope.awaitOrThrow(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Result<T>,
        ): AsyncDelegate<T> = AsyncDelegate(
            scope = this@awaitOrThrow,
            start = start,
            asyncContext = asyncOn,
            awaitContext = awaitOn,
            block = { block().getOrThrow() },
        )

        /**
         * @return
         * An [AsyncDelegate] that unwraps a [Result]
         * and returns [default] if [Result.isFailure].
         *
         * @see AsyncDelegate
         * @see Result.getOrDefault
         */
        public fun <T> CoroutineScope.awaitOrDefault(
            default: T,
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Result<T>,
        ): AsyncDelegate<T> = AsyncDelegate(
            scope = this@awaitOrDefault,
            start = start,
            asyncContext = asyncOn,
            awaitContext = awaitOn,
            block = { block().getOrDefault(defaultValue = default) },
        )

        /**
         * @return
         * An [AsyncDelegate] that attempts to collect only the
         * [Flow.first] emission of [block] and returns a [Result] of [T].
         *
         * @see AsyncDelegate
         * @see FlowExt.resultOfFirst
         */
        public fun <T> CoroutineScope.awaitFirst(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Flow<T>,
        ): AsyncDelegate<Result<T>> = AsyncDelegate(
            scope = this@awaitFirst,
            start = start,
            asyncContext = asyncOn,
            awaitContext = awaitOn,
            block = { block().firstAsResult() },
        )
    }

}
