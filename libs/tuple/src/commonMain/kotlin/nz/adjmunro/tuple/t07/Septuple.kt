package nz.adjmunro.tuple.t07

import nz.adjmunro.tuple.t08.Octuple

public data class Septuple<out A, out B, out C, out D, out E, out F, out G>(
    override val first: A,
    override val second: B,
    override val third: C,
    override val fourth: D,
    override val fifth: E,
    override val sixth: F,
    override val seventh: G,
) : Tuple7<A, B, C, D, E, F, G> {

    public operator fun <H> plus(eighth: H): Octuple<A, B, C, D, E, F, G, H> = Octuple(
        first = first,
        second = second,
        third = third,
        fourth = fourth,
        fifth = fifth,
        sixth = sixth,
        seventh = seventh,
        eighth = eighth,
    )

    public companion object {

        @Suppress("UNCHECKED_CAST")
        public fun <A, B, C, D, E, F, G> Iterable<Any?>.asTuple7(): Septuple<A, B, C, D, E, F, G> =
            Septuple(
                first = elementAt(index = 0) as A,
                second = elementAt(index = 1) as B,
                third = elementAt(index = 2) as C,
                fourth = elementAt(index = 3) as D,
                fifth = elementAt(index = 4) as E,
                sixth = elementAt(index = 5) as F,
                seventh = elementAt(index = 6) as G,
            )

        public operator fun <A, B, C, D, E, F, G, H> A.plus(
            septuple: Septuple<B, C, D, E, F, G, H>,
        ): Octuple<A, B, C, D, E, F, G, H> = Octuple(
            first = this@plus,
            second = septuple.first,
            third = septuple.second,
            fourth = septuple.third,
            fifth = septuple.fourth,
            sixth = septuple.fifth,
            seventh = septuple.sixth,
            eighth = septuple.seventh,
        )
    }
}
