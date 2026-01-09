package nz.adjmunro.util.inline

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

/**
 * Maps an [Iterable] in parallel and [awaits][Collection.awaitAll] all results.
 *
 * @param transform Converts each [In] value into an [Out] value.
 * @return A [List] of [Out] results.
 */
@InlineDsl
public suspend fun <In, Out> Iterable<In>.parallelMap(
    transform: suspend CoroutineScope.(In) -> Out,
): List<Out> {
    return supervisorScope {
        map { async { transform(it) } }.awaitAll()
    }
}

/**
 * Maps a [Sequence] in parallel and [awaits][Collection.awaitAll] all results.
 *
 * @param transform Converts each [In] value into an [Out] value.
 * @return A [List] of [Out] results.
 * @see Iterable.parallelMap
 */
@InlineDsl
public suspend fun <In, Out> Sequence<In>.parallelMap(
    transform: suspend CoroutineScope.(In) -> Out,
): List<Out> = toList().parallelMap(transform)
