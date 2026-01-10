package nz.adjmunro.tuple.t05

import nz.adjmunro.tuple.t06.Hextuple

public data class Pentuple<out A, out B, out C, out D, out E>(
    override val first: A,
    override val second: B,
    override val third: C,
    override val fourth: D,
    override val fifth: E,
) : Tuple5<A, B, C, D, E> {

    public operator fun <F> plus(sixth: F): Hextuple<A, B, C, D, E, F> = Hextuple(
        first = first,
        second = second,
        third = third,
        fourth = fourth,
        fifth = fifth,
        sixth = sixth,
    )

    public companion object {

        @Suppress("UNCHECKED_CAST")
        public fun <A, B, C, D, E> Iterable<Any?>.asTuple5(): Pentuple<A, B, C, D, E> = Pentuple(
            first = elementAt(index = 0) as A,
            second = elementAt(index = 1) as B,
            third = elementAt(index = 2) as C,
            fourth = elementAt(index = 3) as D,
            fifth = elementAt(index = 4) as E,
        )

        public operator fun <A, B, C, D, E, F> A.plus(
            pentuple: Pentuple<B, C, D, E, F>,
        ): Hextuple<A, B, C, D, E, F> = Hextuple(
            first = this@plus,
            second = pentuple.first,
            third = pentuple.second,
            fourth = pentuple.third,
            fifth = pentuple.fourth,
            sixth = pentuple.fifth,
        )
    }
}
