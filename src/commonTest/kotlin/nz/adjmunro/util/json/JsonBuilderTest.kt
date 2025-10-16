package nz.adjmunro.util.json

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import nz.adjmunro.util.test.bdd.Given
import java.util.stream.IntStream
import kotlin.test.Test

class JsonBuilderTest {
    // TODO add assertXIsFasterThanY(repetitions: Int) or assertXUsesLessMemoryThanY(repetitions: Int)
    inline val <T> T.log: T
        get() = also { println(this) } // TODO move this to commonMain

    inline fun <T> T.log(block: (T) -> Any): T = also { println(block(this)) } // TODO move this to commonMain

    inline val <K, V> Map<K, V>.pairs: Set<Pair<K, V>> // TODO collections extensions
        get() {
            val result = HashSet<Pair<K, V>>(entries.size)
            for (entry: Map.Entry<K, V> in entries) {
                result += Pair(first = entry.key, second = entry.value)
            }
            return result
        }

    @Test @Suppress("FloatingPointLiteralPrecision")
    fun `jsonOf can construct a normal json block`() {
        Given {
            mapOf(
                "array" to listOf(
                    mapOf(
                        "null" to null,
                        "Boolean" to true,
                        "Double" to 0.12345678901234567890,
                        "Float" to 0.12345678901234567890f,
                        "Int" to 300,
                        "Long" to 400L,
                        "Array" to arrayOf(1, 2, 3),
                        "Char" to 'C',
                        "CharSequence" to TestCharSequence("CharSequence"), // TODO requires toString override?
                        "Enum" to TestEnum.B, // TODO, is this normal?
                        "MapEntry" to (mapOf("A" to "B").entries.first()),
                        "Pair" to ("A" to "B"),
                        "Sequence" to sequenceOf(1, 2, 3),
                        "String" to "value",
                    ),
                ),
            ).log { "fixture: $it" }
        } Then {
            shouldNotThrowAny {
                jsonOf(map = fixture).log { "jsonOf(map): $it" }
            }
        } Then {
            shouldNotThrowAny {
                jsonOf(pairs = fixture.pairs.toTypedArray()).log { "jsonOf(pairs): $it" }
            }
        } When { jsonOf(map = fixture) } Then {
            result.shouldBeEqual(
                expected = "{\"array\": [{\"null\": null, \"Boolean\": true, \"Double\": 0.12345678901234568, \"Float\": 0.12345679, \"Int\": 300, \"Long\": 400, \"Array\": [1, 2, 3], \"Char\": \"C\", \"CharSequence\": \"TestCharSequence(s=CharSequence)\", \"Enum\": \"B\", \"MapEntry\": {\"A\": \"B\"}, \"Pair\": {\"A\": \"B\"}, \"Sequence\": [1, 2, 3], \"String\": \"value\"}]}"
            )
        }
    }

    @Test
    fun `jsonOf throws when array of pairs`() {
        Given {
            arrayOf(
                "MapEntry" to (mapOf("A" to "B").entries.first()), // invalid
                "Pair" to ("A" to "B"), // invalid
            ).log { "fixture: $it" }
        } Then {
            shouldThrow<IllegalArgumentException> {
                jsonOf(pairs = fixture).log { "jsonOf(pairs): $it" }
            }
        } Then {
            shouldThrow<IllegalArgumentException> {
                jsonOf(array = fixture).log { "jsonOf(array): $it" }
            }
        } Then {
            shouldThrow<IllegalArgumentException> {
                jsonOf(iterable = fixture.toList()).log { "jsonOf(iterable): $it" }
            }
        } Then {
            shouldThrow<IllegalArgumentException> {
                jsonOf(sequence = fixture.asSequence()).log { "jsonOf(sequence): $it" }
            }
        }
    }

    enum class TestEnum { A, B, C }
    @Suppress("RedundantOverride")
    @JvmInline value class TestCharSequence(val s: String) : CharSequence by s {
        override fun chars(): IntStream = super.chars()
        override fun codePoints(): IntStream = super.codePoints()

//        override fun toString(): String = s
    }

}
