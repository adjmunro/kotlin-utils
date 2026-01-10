package nz.adjmunro.tuple.t06

import nz.adjmunro.tuple.t05.Tuple5

public typealias Tuple<A, B, C, D, E, F> = Tuple6<A, B, C, D, E, F>

public interface Tuple6<out A, out B, out C, out D, out E, out F> : Tuple5<A, B, C, D, E> {

    public val sixth: F

    public operator fun component6(): F = sixth

}
