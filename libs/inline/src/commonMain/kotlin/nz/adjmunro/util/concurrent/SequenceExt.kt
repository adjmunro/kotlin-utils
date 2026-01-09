package nz.adjmunro.util.concurrent

/**
 * Filter [Sequence], such that all elements are also present in [other].
 *
 * @param other [Iterable] of [T] to filter for.
 */
public fun <T> Sequence<T>.filterIn(other: Iterable<T>): Sequence<T> = other.toSet()
    .let { set: Set<T> -> this@filterIn.filter { element: T -> element in set } }

/**
 * Filter out and element of [other] from the [Sequence].
 * @param other [Iterable] of [T] to filter out.
 */
public fun <T> Sequence<T>.filterOut(other: Iterable<T>): Sequence<T> = minus(elements = other.toSet())
