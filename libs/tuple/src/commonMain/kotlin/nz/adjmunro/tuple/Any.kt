package nz.adjmunro.tuple

import nz.adjmunro.tuple.t02.Tuple2
import nz.adjmunro.tuple.t03.Tuple3
import nz.adjmunro.tuple.t04.Tuple4
import nz.adjmunro.tuple.t05.Tuple5
import nz.adjmunro.tuple.t06.Tuple6
import nz.adjmunro.tuple.t07.Tuple7
import nz.adjmunro.tuple.t08.Tuple8

public inline fun <A, B, R> Tuple2<A, B>.any(condition: (R) -> Boolean): Boolean where A : R, B : R = condition(first) || condition(second)
public inline fun <A, B, C, R> Tuple3<A, B, C>.any(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R = condition(first) || condition(second) || condition(third)
public inline fun <A, B, C, D, R> Tuple4<A, B, C, D>.any(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R = condition(first) || condition(second) || condition(third) || condition(fourth)
public inline fun <A, B, C, D, E, R> Tuple5<A, B, C, D, E>.any(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R, E : R = condition(first) || condition(second) || condition(third) || condition(fourth) || condition(fifth)
public inline fun <A, B, C, D, E, F, R> Tuple6<A, B, C, D, E, F>.any(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R, E : R, F : R = condition(first) || condition(second) || condition(third) || condition(fourth) || condition(fifth) || condition(sixth)
public inline fun <A, B, C, D, E, F, G, R> Tuple7<A, B, C, D, E, F, G>.any(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R, E : R, F : R, G : R = condition(first) || condition(second) || condition(third) || condition(fourth) || condition(fifth) || condition(sixth) || condition(seventh)
public inline fun <A, B, C, D, E, F, G, H, R> Tuple8<A, B, C, D, E, F, G, H>.any(condition: (R) -> Boolean): Boolean where A : R, B : R, C : R, D : R, E : R, F : R, G : R, H : R = condition(first) || condition(second) || condition(third) || condition(fourth) || condition(fifth) || condition(sixth) || condition(seventh) || condition(eighth)
