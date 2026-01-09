package nz.adjmunro.util.inline

/**
 * Annotation to mark functions and types as members of the Inline DSL library.
 */
@Target(
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
)
@DslMarker @PublishedApi
internal annotation class InlineDsl
