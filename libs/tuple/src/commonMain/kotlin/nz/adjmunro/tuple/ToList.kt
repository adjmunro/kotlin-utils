package nz.adjmunro.tuple

import nz.adjmunro.tuple.t01.Tuple1
import nz.adjmunro.tuple.t02.Tuple2
import nz.adjmunro.tuple.t03.Tuple3
import nz.adjmunro.tuple.t04.Tuple4
import nz.adjmunro.tuple.t05.Tuple5
import nz.adjmunro.tuple.t06.Tuple6
import nz.adjmunro.tuple.t07.Tuple7
import nz.adjmunro.tuple.t08.Tuple8

public fun <A, R> Tuple1<A>.toList(): List<R> where A : R = listOf(first)
public fun <A, B, R> Tuple2<A, B>.toList(): List<R> where A : R, B : R = listOf(first, second)
public fun <A, B, C, R> Tuple3<A, B, C>.toList(): List<R> where A : R, B : R, C : R = listOf(first, second, third)
public fun <A, B, C, D, R> Tuple4<A, B, C, D>.toList(): List<R> where A : R, B : R, C : R, D : R = listOf(first, second, third, fourth)
public fun <A, B, C, D, E, R> Tuple5<A, B, C, D, E>.toList(): List<R> where A : R, B : R, C : R, D : R, E : R = listOf(first, second, third, fourth, fifth)
public fun <A, B, C, D, E, F, R> Tuple6<A, B, C, D, E, F>.toList(): List<R> where A : R, B : R, C : R, D : R, E : R, F : R = listOf(first, second, third, fourth, fifth, sixth)
public fun <A, B, C, D, E, F, G, R> Tuple7<A, B, C, D, E, F, G>.toList(): List<R> where A : R, B : R, C : R, D : R, E : R, F : R, G : R = listOf(first, second, third, fourth, fifth, sixth, seventh)
public fun <A, B, C, D, E, F, G, H, R> Tuple8<A, B, C, D, E, F, G, H>.toList(): List<R> where A : R, B : R, C : R, D : R, E : R, F : R, G : R, H : R = listOf(first, second, third, fourth, fifth, sixth, seventh, eighth)
