package nz.adjmunro.tuple

import nz.adjmunro.tuple.t02.Tuple2
import nz.adjmunro.tuple.t03.Tuple3
import nz.adjmunro.tuple.t04.Tuple4
import nz.adjmunro.tuple.t05.Tuple5
import nz.adjmunro.tuple.t06.Tuple6
import nz.adjmunro.tuple.t07.Tuple7
import nz.adjmunro.tuple.t08.Tuple8

public inline fun <A, B, R> Tuple2<A, B>.none(condition: (R) -> Boolean): Boolean where A : R, B : R = !any(condition)
public inline fun <A, B, C, R> Tuple3<A, B, C>.none(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R = !any(condition)
public inline fun <A, B, C, D, R> Tuple4<A, B, C, D>.none(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R = !any(condition)
public inline fun <A, B, C, D, E, R> Tuple5<A, B, C, D, E>.none(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R, E : R = !any(condition)
public inline fun <A, B, C, D, E, F, R> Tuple6<A, B, C, D, E, F>.none(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R, E : R, F : R = !any(condition)
public inline fun <A, B, C, D, E, F, G, R> Tuple7<A, B, C, D, E, F, G>.none(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R, E : R, F : R, G : R = !any(condition)
public inline fun <A, B, C, D, E, F, G, H, R> Tuple8<A, B, C, D, E, F, G, H>.none(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R, E : R, F : R, G : R, H : R = !any(condition)
