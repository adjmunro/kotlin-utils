package nz.adjmunro.tuple.t04

import nz.adjmunro.tuple.t03.Tuple3

public typealias Tuple<A, B, C, D> = Tuple4<A, B, C, D>

public interface Tuple4<out A, out B, out C, out D> : Tuple3<A, B, C> {

    public val fourth: D

    public operator fun component4(): D = fourth

}
