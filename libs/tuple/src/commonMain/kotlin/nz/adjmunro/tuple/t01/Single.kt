package nz.adjmunro.tuple.t01

import nz.adjmunro.tuple.t02.Duple

public data class Single<out A>(override val first: A) : Tuple1<A> {

    public operator fun <A, B> Tuple1<A>.plus(second: B): Duple<A, B> = Duple(
        first = first,
        second = second,
    )

    public companion object {
        public fun <A> A.asTuple1(): Single<A> = Single(
            first = this,
        )

        public fun <A> Iterable<A>.asTuple1(): Single<A> = Single(
            first = elementAt(index = 0),
        )

        public operator fun <A, B> A.plus(single: Tuple1<B>): Duple<A, B> = Duple(
            first = this@plus,
            second = single.first,
        )
    }
}
