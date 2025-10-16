@file:Suppress("FunctionName")

package nz.adjmunro.util.test.bdd

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A simple BDD-style DSL for wrapping a test [fixture] and its [result], which can then be
 * provided as a scope context to other functions.
 *
 * @property Fixture The type of the test fixture to be used in the test.
 * @property Result The type of the result produced by the test.
 * @property fixture The test fixture to be used in the test.
 * @property result The result of the test.
 *
 * @see Given
 * @see When.When
 * @see When.Then
 */
@BehaviourDrivenDevelopmentDsl
public data class When<Fixture, Result>(
    val fixture: Fixture,
    val result: Result,
) {
    /**
     * A BDD-style **context switcher** that takes the [Given][Given] fixture,
     * and *transforms it into a new* [When][nz.adjmunro.util.test.bdd.When] context.
     *
     * > *This function is for triggering some test action using the original
     * > [Given] scope and producing a new [When] result, [R].*
     *
     * @receiver The current [When] instance, which contains the original [fixture]. The receiver's [result] is ignored.
     * @param R The ***new*** type of the result produced by the [block].
     * @param block A lambda that uses the [Given] receiver to trigger some test action, and yield a result of type [R].
     * @return A new [When][nz.adjmunro.util.test.bdd.When] instance containing the original [fixture][When.fixture] and the new [result][When.result] of type [R].
     */
    @Suppress("NotConstructor")
    @BehaviourDrivenDevelopmentDsl
    public inline infix fun <R> When(
        block: Given<Fixture>.() -> R
    ): When<Fixture, R> {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        return When(fixture = fixture, result = block(Given(fixture)))
    }

    /**
     * A BDD-style [apply]-like context runner that provides the [When][nz.adjmunro.util.test.bdd.When]
     * receiver to [block], then returns the original receiver.
     *
     * > *This function is for making assertions about the current [When] instance.*
     *
     * @receiver The current [When] instance.
     * @param block A lambda for making assertions about the current [When] instance.
     * @return The receiver [When] instance for further chaining.
     */
    @BehaviourDrivenDevelopmentDsl
    public inline infix fun Then(
        block: When<Fixture, Result>.() -> Unit
    ): When<Fixture, Result> {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        block(this)
        return this
    }
}
