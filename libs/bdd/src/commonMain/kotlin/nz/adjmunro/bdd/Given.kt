@file:Suppress("FunctionName")

package nz.adjmunro.bdd

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A simple BDD-style DSL for wrapping a test [fixture].
 *
 * @property Fixture The type of the test fixture to be used in the test.
 * @property fixture The test fixture to be used in the test.
 *
 * @see Given.When
 * @see Given.Then
 */
@JvmInline @BehaviourDrivenDevelopmentDsl
public value class Given<Fixture>(public val fixture: Fixture) {
    /**
     * Alternative [Given] builder constructor for BDD-style DSL.
     *
     * @param block A lambda that returns the [fixture] to be used in the test.
     * @return A [Given] instance containing the [fixture].
     */
    @BehaviourDrivenDevelopmentDsl
    public constructor(block: () -> Fixture) : this(fixture = block())

    /**
     * A BDD-style context runner that take the [Given][nz.adjmunro.util.test.bdd.Given] fixture,
     * and transforms it into a [When][nz.adjmunro.bdd.When] context.
     *
     * > *This function is for triggering some test action and producing a result.*
     *
     * @receiver The current [Given] instance.
     * @param Result The type of the result produced by the [block].
     * @param fixture The fixture to be used in the test.
     * @param block A lambda that uses the [Given] receiver to trigger some test action, and yield a result.
     * @return A [When][nz.adjmunro.bdd.When] instance containing the [fixture][nz.adjmunro.bdd.When.fixture] and [result][nz.adjmunro.bdd.When.result].
     */
    @BehaviourDrivenDevelopmentDsl
    public inline infix fun <Result> When(
        block: Given<Fixture>.() -> Result
    ): When<Fixture, Result> {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        return When(fixture = fixture, result = block())
    }

    /**
     * A BDD-style [apply]-like context runner that provides the [Given][nz.adjmunro.util.test.bdd.Given]
     * receiver to [block], then returns the original receiver.
     *
     * > *This function is for making assertions about the current [Given] instance.*
     *
     * @receiver The current [Given] instance.
     * @param block A lambda for making assertions about the current [Given] instance.
     * @return The receiver [Given] instance for further chaining.
     */
    @BehaviourDrivenDevelopmentDsl
    public inline infix fun Then(
        block: Given<Fixture>.() -> Unit
    ): Given<Fixture> {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        block(this)
        return this
    }
}
