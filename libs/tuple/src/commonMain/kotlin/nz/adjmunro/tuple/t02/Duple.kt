package nz.adjmunro.tuple.t02

public data class Duple<out A, out B>(
    override val first: A,
    override val second: B,
) : Tuple2<A, B> {

    public operator fun <C> plus(third: C): Triple<A, B, C> = Triple(
        first = first,
        second = second,
        third = third,
    )

    public companion object {
        public fun <A, B> Pair<A, B>.asTuple2(): Tuple2<A, B> = Duple(
            first = first,
            second = second,
        )

        @Suppress("UNCHECKED_CAST")
        public fun <A, B> Iterable<Any?>.asTuple2(): Duple<A, B> = Duple(
            first = elementAt(index = 0) as A,
            second = elementAt(index = 1) as B,
        )

        public operator fun <A, B, C> A.plus(pair: Duple<B, C>): Triple<A, B, C> = Triple(
            first = this,
            second = pair.first,
            third = pair.second,
        )

        public operator fun <A, B, C> A.plus(pair: Pair<B, C>): Triple<A, B, C> = Triple(
            first = this,
            second = pair.first,
            third = pair.second,
        )

        public operator fun <A, B, C> Pair<A, B>.plus(third: C): Triple<A, B, C> = Triple(
            first = first,
            second = second,
            third = third,
        )
    }
}
