package nz.adjmunro.util.test.bdd

/**
 * An annotation marking members of the [BehaviourDrivenDevelopmentDsl] library.
 *
 * > *No co-dependencies.*
 *
 * @see Given
 * @see When
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.PROPERTY,
)
@DslMarker
public annotation class BehaviourDrivenDevelopmentDsl
