package nz.adjmunro.util.inline

/**
 * Syntax-sugar equivalent to a [with] block that only executes if the [receiver] is not `null`.
 *
 * ```kotlin
 * // Before
 * with(receiver ?: return) { uppercase() }
 *
 * // After
 * exists(receiver) { uppercase() }
 * ```
 *
 * @param T The type of the receiver.
 * @param R The type of the return value.
 * @param receiver The nullable receiver to check for existence.
 * @param block The block to execute if the receiver is not `null`.
 * @return The result of the block if the receiver is not `null`, otherwise `null`.
 */
@InlineDsl @JvmName("withIfExists")
public inline fun <T, R> exists(receiver: T?, block: (T & Any).() -> R): R? {
    return receiver.nullfold(none = ::nulls, some = block)
}

/**
 * Syntax-sugar equivalent to a [run] block that only executes if the receiver is not `null`.
 *
 * ```kotlin
 * // Before
 * receiver?.run { uppercase() }
 *
 * // After
 * receiver.exists { uppercase() }
 * ```
 *
 * @param T The type of the receiver.
 * @param R The type of the return value.
 * @param block The block to execute if the receiver is not `null`.
 * @return The result of the block if the receiver is not `null`, otherwise `null`.
 */
@InlineDsl @JvmName("runIfExists")
public inline infix fun <T, R> T?.exists(block: (T & Any).() -> R): R? {
    return nullfold(none = ::nulls, some = block)
}

/**
 * Syntax-sugar for `null`-case of [nullfold] that throws a [NullPointerException] by default.
 *
 * > *This mainly exists for the scenario where you would use a block and/or still want to
 * > continue function chaining.*
 *
 * ```kotlin
 * // Before -- awkward run block / breaks function chaining
 * val len = result?.length ?: run {
 *     state.update { it.copy(error = "Result was null") }
 *     return@someFunction
 * }
 * println(len.toString())
 *
 * // Before -- awkward brackets to continue function chain
 * val len = (result?.length ?: run {
 *     state.update { it.copy(error = "Result was null") }
 *     return@run -1
 * }).let { println(it.toString()) }
 *
 * // After
 * result?.length.orElse {
 *     state.update { it.copy(error = "Result was null") }
 *     return@someFunction // or return@orElse -1
 * }.let { println(it.toString()) }
 */
@InlineDsl
public inline infix fun <T : Any> T?.orElse(none: (NullPointerException) -> T = ::rethrow): T {
    return nullfold(none = none, some = ::itself)
}
