package nz.adjmunro.tuple.t05

import nz.adjmunro.tuple.t04.Tuple4

public typealias Tuple<A, B, C, D, E> = Tuple5<A, B, C, D, E>

public interface Tuple5<out A, out B, out C, out D, out E> : Tuple4<A, B, C, D> {

    public val fifth: E

    public operator fun component5(): E = fifth

}
