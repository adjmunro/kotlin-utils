package nz.adjmunro.util.inline

// TODO: Split Kotlin reflection & Java reflection. Java reflection is cheaper, even for name lookups.
// TODO: Reflection Serializer https://kt.academy/article/ak-reflection-class

/**
 * Syntax-sugar for getting the [simple name][kotlin.reflect.KClass.simpleName] of [T]'s class.
 */
public inline val <T> T.simpleKotlinName: String get() = this?.let { it::class.simpleName ?: "Anonymous" } ?: "null"

public inline val <T> T.simpleJavaName: String get() = this?.javaClass?.simpleName ?: "null"
