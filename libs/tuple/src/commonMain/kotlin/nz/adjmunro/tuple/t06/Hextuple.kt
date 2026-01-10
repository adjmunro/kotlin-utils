package nz.adjmunro.tuple.t06

import nz.adjmunro.tuple.t07.Septuple

public data class Hextuple<out A, out B, out C, out D, out E, out F>(
    override val first: A,
    override val second: B,
    override val third: C,
    override val fourth: D,
    override val fifth: E,
    override val sixth: F,
) : Tuple6<A, B, C, D, E, F> {

    public operator fun <G> plus(seventh: G): Septuple<A, B, C, D, E, F, G> = Septuple(
        first = first,
        second = second,
        third = third,
        fourth = fourth,
        fifth = fifth,
        sixth = sixth,
        seventh = seventh,
    )

    public companion object {

        @Suppress("UNCHECKED_CAST")
        public fun <A, B, C, D, E, F> Iterable<Any?>.asTuple6(): Hextuple<A, B, C, D, E, F> =
            Hextuple(
                first = elementAt(index = 0) as A,
                second = elementAt(index = 1) as B,
                third = elementAt(index = 2) as C,
                fourth = elementAt(index = 3) as D,
                fifth = elementAt(index = 4) as E,
                sixth = elementAt(index = 5) as F,
            )

        public operator fun <A, B, C, D, E, F, G> A.plus(
            hextuple: Hextuple<B, C, D, E, F, G>,
        ): Septuple<A, B, C, D, E, F, G> = Septuple(
            first = this@plus,
            second = hextuple.first,
            third = hextuple.second,
            fourth = hextuple.third,
            fifth = hextuple.fourth,
            sixth = hextuple.fifth,
            seventh = hextuple.sixth
        )
    }
}
