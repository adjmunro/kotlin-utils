package nz.adjmunro.tuple

import nz.adjmunro.tuple.t01.Single
import nz.adjmunro.tuple.t01.Tuple1
import nz.adjmunro.tuple.t02.Duple
import nz.adjmunro.tuple.t02.Tuple2
import nz.adjmunro.tuple.t03.Truple
import nz.adjmunro.tuple.t03.Tuple3
import nz.adjmunro.tuple.t04.Quadruple
import nz.adjmunro.tuple.t04.Tuple4
import nz.adjmunro.tuple.t05.Pentuple
import nz.adjmunro.tuple.t05.Tuple5
import nz.adjmunro.tuple.t06.Hextuple
import nz.adjmunro.tuple.t06.Tuple6
import nz.adjmunro.tuple.t07.Septuple
import nz.adjmunro.tuple.t07.Tuple7
import nz.adjmunro.tuple.t08.Octuple
import nz.adjmunro.tuple.t08.Tuple8

/** @return A [Single] where all types are definitely not `null`, or `null` if any types are `null`.*/
public fun <A> Tuple1<A?>.nullessOrNull(): Single<A & Any>? {
    return Single(
        first = first ?: return null,
    )
}

/** @return A [Duple] where all types are definitely not `null`, or `null` if any types are `null`.*/
public fun <A, B> Tuple2<A?, B?>.nullessOrNull(): Duple<A & Any, B & Any>? {
    return Duple(
        first = first ?: return null,
        second = second ?: return null,
    )
}

/** @return A [Truple] where all types are definitely not `null`, or `null` if any types are `null`.*/
public fun <A, B, C> Tuple3<A?, B?, C?>.nullessOrNull(): Truple<A & Any, B & Any, C & Any>? {
    return Truple(
        first = first ?: return null,
        second = second ?: return null,
        third = third ?: return null,
    )
}

/** @return A [Quadruple] where all types are definitely not `null`, or `null` if any types are `null`.*/
public fun <A, B, C, D> Tuple4<A?, B?, C?, D?>.nullessOrNull(): Quadruple<A & Any, B & Any, C & Any, D & Any>? {
    return Quadruple(
        first = first ?: return null,
        second = second ?: return null,
        third = third ?: return null,
        fourth = fourth ?: return null,
    )
}

/** @return A [Pentuple] where all types are definitely not `null`, or `null` if any types are `null`.*/
public fun <A, B, C, D, E> Tuple5<A?, B?, C?, D?, E?>.nullessOrNull(): Pentuple<A & Any, B & Any, C & Any, D & Any, E & Any>? {
    return Pentuple(
        first = first ?: return null,
        second = second ?: return null,
        third = third ?: return null,
        fourth = fourth ?: return null,
        fifth = fifth ?: return null,
    )
}

/** @return A [Hextuple] where all types are definitely not `null`, or `null` if any types are `null`.*/
public fun <A, B, C, D, E, F> Tuple6<A?, B?, C?, D?, E?, F?>.nullessOrNull(): Hextuple<A & Any, B & Any, C & Any, D & Any, E & Any, F & Any>? {
    return Hextuple(
        first = first ?: return null,
        second = second ?: return null,
        third = third ?: return null,
        fourth = fourth ?: return null,
        fifth = fifth ?: return null,
        sixth = sixth ?: return null,
    )
}

/** @return A [Septuple] where all types are definitely not `null`, or `null` if any types are `null`.*/
public fun <A, B, C, D, E, F, G> Tuple7<A?, B?, C?, D?, E?, F?, G?>.nullessOrNull(): Septuple<A & Any, B & Any, C & Any, D & Any, E & Any, F & Any, G & Any>? {
    return Septuple(
        first = first ?: return null,
        second = second ?: return null,
        third = third ?: return null,
        fourth = fourth ?: return null,
        fifth = fifth ?: return null,
        sixth = sixth ?: return null,
        seventh = seventh ?: return null,
    )
}

/** @return An [Octuple] where all types are definitely not `null`, or `null` if any types are `null`.*/
public fun <A, B, C, D, E, F, G, H> Tuple8<A?, B?, C?, D?, E?, F?, G?, H?>.nullessOrNull(): Octuple<A & Any, B & Any, C & Any, D & Any, E & Any, F & Any, G & Any, H & Any>? {
    return Octuple(
        first = first ?: return null,
        second = second ?: return null,
        third = third ?: return null,
        fourth = fourth ?: return null,
        fifth = fifth ?: return null,
        sixth = sixth ?: return null,
        seventh = seventh ?: return null,
        eighth = eighth ?: return null,
    )
}
