package nz.adjmunro.tuple.t02

import nz.adjmunro.tuple.t01.Tuple1

public typealias Tuple<A, B> = Tuple2<A, B>

public interface Tuple2<out A, out B> : Tuple1<A> {

    public val second: B

    public operator fun component2(): B = second

}
