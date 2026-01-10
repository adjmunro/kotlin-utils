package nz.adjmunro.tuple.t04

import nz.adjmunro.tuple.t05.Pentuple

public data class Quadruple<out A, out B, out C, out D>(
    override val first: A,
    override val second: B,
    override val third: C,
    override val fourth: D,
) : Tuple4<A, B, C, D> {

    public operator fun <E> plus(fifth: E): Pentuple<A, B, C, D, E> = Pentuple(
        first = first,
        second = second,
        third = third,
        fourth = fourth,
        fifth = fifth,
    )

    public companion object {

        @Suppress("UNCHECKED_CAST")
        public fun <A, B, C, D> Iterable<Any?>.asTuple4(): Quadruple<A, B, C, D> = Quadruple(
            first = elementAt(index = 0) as A,
            second = elementAt(index = 1) as B,
            third = elementAt(index = 2) as C,
            fourth = elementAt(index = 3) as D,
        )

        public operator fun <A, B, C, D, E> A.plus(
            quadruple: Quadruple<B, C, D, E>,
        ): Pentuple<A, B, C, D, E> = Pentuple(
            first = this@plus,
            second = quadruple.first,
            third = quadruple.second,
            fourth = quadruple.third,
            fifth = quadruple.fourth,
        )
    }
}
