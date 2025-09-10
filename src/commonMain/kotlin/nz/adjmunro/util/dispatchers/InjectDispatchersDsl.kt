package nz.adjmunro.util.dispatchers

/**
 * An annotation marking members of the [InjectDispatchersDsl] library.
 *
 * - `implementation` using [kotlinx.coroutines]
 * - `compileOnly` integrations with `JUnit4` and `JUnit5`.
 *
 * @see Dispatchers
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.PROPERTY,
)
@DslMarker
public annotation class InjectDispatchersDsl
