package nz.adjmunro.tuple.t03

import nz.adjmunro.tuple.t02.Tuple2

public typealias Tuple<A, B, C> = Tuple3<A, B, C>

public interface Tuple3<out A, out B, out C> : Tuple2<A, B> {

    public val third: C

    public operator fun component3(): C = third

}
