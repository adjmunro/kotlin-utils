package nz.adjmunro.tuple.t07

import nz.adjmunro.tuple.t06.Tuple6

public typealias Tuple<A, B, C, D, E, F, G> = Tuple7<A, B, C, D, E, F, G>

public interface Tuple7<out A, out B, out C, out D, out E, out F, out G> :
    Tuple6<A, B, C, D, E, F> {

    public val seventh: G

    public operator fun component7(): G = seventh

}
