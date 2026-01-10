package nz.adjmunro.tuple.t01

public typealias Tuple<A> = Tuple1<A>

public interface Tuple1<out A> {

    public val first: A

    public operator fun component1(): A = first

}
