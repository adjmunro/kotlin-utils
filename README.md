# Kotlin Inline
[Documentation](https://adjmunro.github.io/project-inline/)
> *This was probably a bad idea, but damn if it isn't kinda useful.*

Specifically, this includes:
- Default parameter function references ([`ResultExt.kt`](https://github.com/adjmunro/project-inline/blob/main/src/main/kotlin/nz/adjmunro/inline/ReturnExt.kt), [`StringExt.kt`](https://github.com/adjmunro/project-inline/blob/main/src/main/kotlin/nz/adjmunro/inline/StringExt.kt))
- If-else for function chains to bisect and recombine logic ([`FoldExt.kt`](https://github.com/adjmunro/project-inline/blob/main/src/main/kotlin/nz/adjmunro/inline/FoldExt.kt))
- Type-casting facilities for function chains ([`CastExt.kt`](https://github.com/adjmunro/project-inline/blob/main/src/main/kotlin/nz/adjmunro/inline/CastExt.kt))
  <br><sub>*Doing the little dance with `(ChainUntilNow as? Y)?.` is so awkward everytime >.> <br>You think I plan my brackets for casting ahead of time??*</sub>
- Null-handling context runner and function chain extensions ([`NullExt.kt`](https://github.com/adjmunro/project-inline/blob/main/src/main/kotlin/nz/adjmunro/inline/NullExt.kt))
  <br><sub>No longer do you have to break the chain with `?:`</sub>

## Why?
Originally inspired by the [identity](https://github.com/arrow-kt/arrow/blob/main/arrow-libs/core/arrow-core/src/commonMain/kotlin/arrow/core/predef.kt) function in ArrowKt... 
###### arrow/core/predef.kt
```kotlin
package arrow.core

@Suppress("NOTHING_TO_INLINE")
public inline fun <A> identity(a: A): A = a

/**
 * This is a work-around for having nested nulls in generic code.
 * This allows for writing faster generic code instead of using `Option`.
 * This is only used as an optimisation technique in low-level code,
 * always prefer to use `Option` in actual business code when needed in generic code.
 */
public object EmptyValue {
  @Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
  public inline fun <A> unbox(value: Any?): A =
    fold(value, { null as A }, ::identity)

  public inline fun <T> combine(first: Any?, second: T, combine: (T, T) -> T): T =
    fold(first, { second }, { t: T -> combine(t, second) })

  @Suppress("UNCHECKED_CAST")
  public inline fun <T, R> fold(value: Any?, ifEmpty: () -> R, ifNotEmpty: (T) -> R): R =
    if (value === EmptyValue) ifEmpty() else ifNotEmpty(value as T)
}
```

Paired with some confusion that creating similar functions might offer optimisation benefits (which is probably wrong - if anything, so prolific use of `inline` ought to *increase* your build time, but I've never checked), I originally created an ["InlineExt"](https://github.com/adjmunro/project-inline/blob/main/src/main/kotlin/nz/adjmunro/inline/ReturnExt.kt) to provide various kinds of inline method references to use as default parameters.

That then quickly grew as I added more inline utilities that I felt the standard library was a little lacking. 
