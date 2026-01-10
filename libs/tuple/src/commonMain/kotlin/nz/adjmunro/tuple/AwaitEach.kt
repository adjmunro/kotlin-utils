package nz.adjmunro.tuple

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.awaitAll
import nz.adjmunro.tuple.t01.Single
import nz.adjmunro.tuple.t01.Single.Companion.asTuple1
import nz.adjmunro.tuple.t02.Duple
import nz.adjmunro.tuple.t02.Duple.Companion.asTuple2
import nz.adjmunro.tuple.t03.Truple
import nz.adjmunro.tuple.t03.Truple.Companion.asTuple3
import nz.adjmunro.tuple.t04.Quadruple
import nz.adjmunro.tuple.t04.Quadruple.Companion.asTuple4
import nz.adjmunro.tuple.t05.Pentuple
import nz.adjmunro.tuple.t05.Pentuple.Companion.asTuple5
import nz.adjmunro.tuple.t06.Hextuple
import nz.adjmunro.tuple.t06.Hextuple.Companion.asTuple6
import nz.adjmunro.tuple.t07.Septuple
import nz.adjmunro.tuple.t07.Septuple.Companion.asTuple7
import nz.adjmunro.tuple.t08.Octuple
import nz.adjmunro.tuple.t08.Octuple.Companion.asTuple8

/** @return Await all asynchronous operations and return a [Single]. */
public suspend fun <A> awaitEach(
    first: Deferred<A>,
): Single<A> = awaitAll(
    deferreds = arrayOf(
        first,
    ),
).asTuple1()

/** @return Await all asynchronous operations and return a [Duple]. */
public suspend fun <A, B> awaitEach(
    first: Deferred<A>,
    second: Deferred<B>,
): Duple<A, B> = awaitAll(
    deferreds = arrayOf(
        first,
        second,
    ),
).asTuple2()

/** @return Await all asynchronous operations and return a [Truple]. */
public suspend fun <A, B, C> awaitEach(
    first: Deferred<A>,
    second: Deferred<B>,
    third: Deferred<C>,
): Truple<A, B, C> = awaitAll(
    deferreds = arrayOf(
        first,
        second,
        third,
    ),
).asTuple3()

/** @return Await all asynchronous operations and return a [Quadruple]. */
public suspend fun <A, B, C, D> awaitEach(
    first: Deferred<A>,
    second: Deferred<B>,
    third: Deferred<C>,
    fourth: Deferred<D>,
): Quadruple<A, B, C, D> = awaitAll(
    deferreds = arrayOf(
        first,
        second,
        third,
        fourth,
    ),
).asTuple4()

/** @return Await all asynchronous operations and return a [Pentuple]. */
public suspend fun <A, B, C, D, E> awaitEach(
    first: Deferred<A>,
    second: Deferred<B>,
    third: Deferred<C>,
    fourth: Deferred<D>,
    fifth: Deferred<E>,
): Pentuple<A, B, C, D, E> = awaitAll(
    deferreds = arrayOf(
        first,
        second,
        third,
        fourth,
        fifth,
    ),
).asTuple5()

/** @return Await all asynchronous operations and return a [Hextuple]. */
public suspend fun <A, B, C, D, E, F> awaitEach(
    first: Deferred<A>,
    second: Deferred<B>,
    third: Deferred<C>,
    fourth: Deferred<D>,
    fifth: Deferred<E>,
    sixth: Deferred<F>,
): Hextuple<A, B, C, D, E, F> = awaitAll(
    deferreds = arrayOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
    ),
).asTuple6()

/** @return Await all asynchronous operations and return a [Septuple]. */
public suspend fun <A, B, C, D, E, F, G> awaitEach(
    first: Deferred<A>,
    second: Deferred<B>,
    third: Deferred<C>,
    fourth: Deferred<D>,
    fifth: Deferred<E>,
    sixth: Deferred<F>,
    seventh: Deferred<G>,
): Septuple<A, B, C, D, E, F, G> = awaitAll(
    deferreds = arrayOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
    ),
).asTuple7()

/** @return Await all asynchronous operations and return an [Octuple]. */
public suspend fun <A, B, C, D, E, F, G, H> awaitEach(
    first: Deferred<A>,
    second: Deferred<B>,
    third: Deferred<C>,
    fourth: Deferred<D>,
    fifth: Deferred<E>,
    sixth: Deferred<F>,
    seventh: Deferred<G>,
    eighth: Deferred<H>,
): Octuple<A, B, C, D, E, F, G, H> = awaitAll(
    deferreds = arrayOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
    ),
).asTuple8()
