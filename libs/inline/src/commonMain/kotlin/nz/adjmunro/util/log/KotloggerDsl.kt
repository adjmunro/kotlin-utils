package nz.adjmunro.util.log

/**
 * An annotation marking members of the [Kotlogger] library.
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.PROPERTY,
)
@DslMarker
public annotation class KotloggerDsl
