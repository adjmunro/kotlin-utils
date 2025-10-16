package nz.adjmunro.util.json

import nz.adjmunro.util.inline.simpleJavaName

public fun jsonOf(vararg pairs: Pair<String, *>): String {
    return buildString { parseMapToJson(map = pairs.toMap()) }
}

public fun jsonOf(map: Map<String, *>): String {
    return buildString { parseMapToJson(map = map) }
}

@JvmName("jsonOfVararg")
public fun jsonOf(vararg values: Any?): String {
    return buildString { parseArrayToJson(array = values) }
}

@JvmName("jsonOfArray")
public fun jsonOf(array: Array<*>): String {
    return buildString { parseArrayToJson(array = array) }
}

public fun jsonOf(iterable: Iterable<*>): String {
    return buildString { parseIterableToJson(iterable = iterable) }
}

public fun jsonOf(sequence: Sequence<*>): String {
    return buildString { parseSequenceToJson(sequence = sequence) }
}

internal fun <T> StringBuilder.parseValueToJson(value: T) {
    when (value) {
        null -> append("null")
        is Boolean -> append(value)
        is Double -> append(value)
        is Float -> append(value)
        is Int -> append(value)
        is Long -> append(value)
        is Array<*> -> parseArrayToJson(array = value)
        is Char -> parseCharSequenceToJson(value = "$value")
        is CharSequence -> parseCharSequenceToJson(value = value)
        is Enum<*> -> parseCharSequenceToJson(value = value.name)
        is Iterable<*> -> parseIterableToJson(iterable = value)
        is Map.Entry<*, *> -> parseMapToJson(map = mapOf(value.key to value.value))
        is Map<*, *> -> parseMapToJson(map = value)
        is Pair<*, *> -> parseMapToJson(map = mapOf(value))
        is Sequence<*> -> parseSequenceToJson(sequence = value)
        // TODO Serializable, BigDecimal, BigInteger, Datetime/Instance, Duration, UUID, URI, URL etc
        // TODO Json API specific types w/ serialization/integration
        // TODO Text integration, AST syntax validation, JsonApi semantic validation
        // TODO json accessors
        else -> throw IllegalArgumentException("Unsupported value type: ${value.simpleJavaName}")
    }
}

internal fun StringBuilder.parseCharSequenceToJson(value: CharSequence) {
    append("\"${value}\"")
}

internal fun <K, V> StringBuilder.parsePairToJson(key: K, value: V) {
    require(value = key is String) {
        "JsonObject Key '$key' must be a String! Was ${key.simpleJavaName}"
    }

    parseCharSequenceToJson(value = key)
    append(": ")
    parseValueToJson(value = value)
}

internal fun <K, V> StringBuilder.parseMapToJson(map: Map<K, V>) {
    append("{")
    map.entries.forEachIndexed { index: Int, (key: K, value: V) ->
        parsePairToJson(key = key, value = value)
        if (index != map.entries.size - 1) append(", ")
    }
    append("}")
}

internal fun <T> StringBuilder.parseSequenceToJson(sequence: Sequence<T>) {
    append("[")
    sequence.forEachIndexed { index: Int, item: T ->
        this@parseSequenceToJson.parseValueToJson(value = item)
        if (index != sequence.count() - 1) append(", ")
    }
    append("]")
}

internal fun <T> StringBuilder.parseIterableToJson(iterable: Iterable<T>) {
    append("[")
    iterable.forEachIndexed { index: Int, item: T ->
        this@parseIterableToJson.parseValueToJson(value = item)
        if (index != iterable.count() - 1) append(", ")
    }
    append("]")
}

internal fun <T> StringBuilder.parseArrayToJson(array: Array<T>) {
    append("[")
    array.forEachIndexed { index: Int, item: T ->
        this@parseArrayToJson.parseValueToJson(value = item)
        if (index != array.lastIndex) append(", ")
    }
    append("]")
}
