package nz.adjmunro.util.koin

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


/**
 * A utility function to inject a dependency globally in the application.
 * This is useful for accessing dependencies in a static or top-level context.
 *
 * @param T The type of the dependency to be injected.
 * @return The injected dependency of type T.
 * @throws Throwable This might throw an exception if the dependency cannot be resolved / [Lazy] throws during initialisation.
 */
public inline fun <reified T> globalInject(): T {
    return object : KoinComponent { val value: T by inject() }.value
}
