package nz.adjmunro.tuple.t08

public data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(
    override val first: A,
    override val second: B,
    override val third: C,
    override val fourth: D,
    override val fifth: E,
    override val sixth: F,
    override val seventh: G,
    override val eighth: H,
) : Tuple8<A, B, C, D, E, F, G, H> {

    public companion object {

        @Suppress("UNCHECKED_CAST")
        public fun <A, B, C, D, E, F, G, H> Iterable<Any?>.asTuple8(): Octuple<A, B, C, D, E, F, G, H> =
            Octuple(
                first = elementAt(index = 0) as A,
                second = elementAt(index = 1) as B,
                third = elementAt(index = 2) as C,
                fourth = elementAt(index = 3) as D,
                fifth = elementAt(index = 4) as E,
                sixth = elementAt(index = 5) as F,
                seventh = elementAt(index = 6) as G,
                eighth = elementAt(index = 7) as H,
            )

    }
}
