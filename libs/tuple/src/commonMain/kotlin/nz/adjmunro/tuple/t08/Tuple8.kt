package nz.adjmunro.tuple.t08

import nz.adjmunro.tuple.t07.Tuple7

public typealias Tuple<A, B, C, D, E, F, G, H> = Tuple8<A, B, C, D, E, F, G, H>

public interface Tuple8<out A, out B, out C, out D, out E, out F, out G, out H> :
    Tuple7<A, B, C, D, E, F, G> {

    public val eighth: H

    public operator fun component8(): H = eighth

}
