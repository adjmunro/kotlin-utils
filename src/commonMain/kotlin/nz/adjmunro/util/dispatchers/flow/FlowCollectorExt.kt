package nz.adjmunro.util.dispatchers.flow

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.any
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import nz.adjmunro.util.dispatchers.RuntimeDispatchers.default

/**
 * Terminal [Flow] operator, that collects the first element of type [T].
 *
 * @param T The type of the elements emitted by the [Flow].
 * @return The first element of type [T] emitted by the [Flow].
 * @throws NoSuchElementException if the flow did not contain any elements of type [T].
 */
public suspend inline fun <reified T> Flow<*>.first(): T = first { it is T } as T

public suspend inline fun <reified T> Flow<*>.firstOrNull(): T? = firstOrNull { it is T } as? T

public suspend inline fun <reified T> Flow<*>.firstOrElse(default: () -> T): T? = firstOrNull<T>() ?: default()

public suspend fun <T> Flow<T>.firstOrElse(
    default: () -> T,
    predicate: suspend (T) -> Boolean,
): T? = firstOrNull(predicate = predicate) ?: default()

public suspend fun <T> Flow<T>.first(n: Int): List<T> = take(count= n).toList(destination = mutableListOf())

/**
 * Terminal [Flow] operator, that encapsulates the first collection attempt as a [Result].
 *
 * @throws CancellationException if the current coroutine is cancelled.
 * @throws Error if an [Error] is thrown during collection.
 * @param T The type of the elements emitted by the [Flow].
 * @return The [Result] of [Flow.first].
 */
public suspend fun <T> Flow<T>.firstAsResult(): Result<T> {
    return try { Result.success(value = first()) } catch (e: Throwable) { // todo catch?
        if (e is CancellationException) currentCoroutineContext().ensureActive()
        if (e is Error) throw e
        Result.failure(exception = e)
    }
}

/**
 * Terminal [Flow] operator, to [launchIn] a scope and collect [onEach].
 *
 * ```kotlin
 * // Equivalent to:
 * flow.onEach { block(it) }.launchIn(scope = scope)
 * scope.launch { flow.collect { block(it) } }
 * ```
 *
 * @param T The type of the elements emitted by the [Flow].
 * @param scope The [CoroutineScope] to launch the collection in.
 * @param block The action to perform on each element.
 */
public fun <T> Flow<T>.collectIn(
    scope: CoroutineScope,
    block: FlowCollector<T>,
): Job = scope.launch { collect(collector = block) }

public fun <T> Flow<T>.collectLatestIn(
    scope: CoroutineScope,
    block: suspend (value: T) -> Unit,
): Job = scope.launch { collectLatest(action = block) }

public fun <T> Flow<T>.window(n: Int = 2): Flow<List<T>> = flow {
    require(value = n > 1) { "Window size must be greater than 1." }

    with(receiver = ArrayDeque<T>(initialCapacity = n)) {
        collect { value: T ->
            //
            if (size >= n) removeFirst()

            addLast(element = value)

            if (size == n) emit(value = toList())
        }
    }
}
