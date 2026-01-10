package nz.adjmunro.tuple.t03

import nz.adjmunro.tuple.t04.Quadruple

public data class Truple<out A, out B, out C>(
    override val first: A,
    override val second: B,
    override val third: C,
) : Tuple3<A, B, C> {

    public operator fun <D> plus(
        fourth: D,
    ): Quadruple<A, B, C, D> = Quadruple(
        first = first,
        second = second,
        third = third,
        fourth = fourth,
    )

    public companion object {
        public fun <A, B, C> Triple<A, B, C>.asTuple3(): Tuple3<A, B, C> = Truple(
            first = first,
            second = second,
            third = third,
        )

        @Suppress("UNCHECKED_CAST")
        public fun <A, B, C> Iterable<Any?>.asTuple3(): Truple<A, B, C> = Truple(
            first = elementAt(index = 0) as A,
            second = elementAt(index = 1) as B,
            third = elementAt(index = 2) as C,
        )

        public operator fun <A, B, C, D> A.plus(
            triple: Truple<B, C, D>,
        ): Quadruple<A, B, C, D> = Quadruple(
            first = this@plus,
            second = triple.first,
            third = triple.second,
            fourth = triple.third,
        )

        public operator fun <A, B, C, D> A.plus(
            triple: Triple<B, C, D>,
        ): Quadruple<A, B, C, D> = Quadruple(
            first = this@plus,
            second = triple.first,
            third = triple.second,
            fourth = triple.third,
        )

        public operator fun <A, B, C, D> Triple<A, B, C>.plus(
            fourth: D,
        ): Quadruple<A, B, C, D> = Quadruple(
            first = first,
            second = second,
            third = third,
            fourth = fourth,
        )
    }
}
