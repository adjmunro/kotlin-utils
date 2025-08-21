package nz.adjmunro.util.inline

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class CastExtTest {
    @Test
    fun `castOrThrow should cast when type matches, throw otherwise`(): TestResult = runTest {
        // Given: a value of type Any
        val value = "string"

        // When: castOrThrow is called with matching type
        val result = value.castOrThrow<String>()

        // Then: returns cast value
        result.shouldBe("string")

        // When/Then: castOrThrow is called with non-matching type and throws
        val intValue = 42
        shouldThrow<ClassCastException> {
            intValue.castOrThrow<String>()
        }
    }

    @Test
    fun `castOrNull should cast when type matches, return null otherwise`(): TestResult = runTest {
        // Given: a value of type Any?
        val value = "string"

        // When: castOrNull is called with matching type
        val result = value.castOrNull<String>()

        // Then: returns cast value
        result.shouldBe("string")

        // When: castOrNull is called with non-matching type
        val intValue: Any? = 42
        val nullResult = intValue.castOrNull<String>()

        // Then: returns null
        nullResult.shouldBe(null)

        // When: castOrNull is called with null value
        val nullValue: Any? = null
        val nullResult2 = nullValue.castOrNull<String>()

        // Then: returns null
        nullResult2.shouldBe(null)
    }

    @Test
    fun `castOrElse should cast when type matches, return default otherwise`(): TestResult = runTest {
        // Given: a value of type Any?
        val value = "string"

        // When: castOrElse is called with matching type
        val result = value.castOrElse<String> {
            "default"
        }

        // Then: returns cast value
        result.shouldBe("string")

        // When: castOrElse is called with non-matching type
        val intValue = 42
        val elseResult = intValue.castOrElse<String> {
            "default"
        }

        // Then: returns default value
        elseResult.shouldBe("default")

        // When: castOrElse is called with null value
        val nullValue = null
        val elseResult2 = nullValue.castOrElse<String> {
            "default"
        }

        // Then: returns default value
        elseResult2.shouldBe("default")
    }

    @Test
    fun `castOrElse should propagate exception from default block if type does not match`(): TestResult = runTest {
        // Given: a value of type Any? that does not match
        val value = 42

        // When/Then: castOrElse throws exception from default block
        shouldThrow<IllegalStateException> {
            value.castOrElse<String> {
                throw IllegalStateException("fail")
            }
        }
    }

    @Test
    fun `on should execute block when type matches, do nothing otherwise`(): TestResult = runTest {
        // Given: a value of type Any
        val value = "hello"

        // When: on is called with matching type
        var called = false
        value.on(instanceOf = String::class) {
            called = true
        }

        // Then: block is executed
        called.shouldBe(true)

        // When: on is called with non-matching type
        called = false
        value.on(instanceOf = Int::class) {
            called = true
        }

        // Then: block is not executed
        called.shouldBe(false)
    }

    @Test
    fun `castOrThrow should not throw ClassCastException for incompatible types with generics`(): TestResult = runTest {
        // Given: a value of type List<Int>
        val value = listOf(1, 2, 3)

        // When/Then: castOrThrow is called with incompatible generic type
        shouldNotThrow<ClassCastException> {
            value.castOrThrow<List<String>>() // (inner) type-erasure lol
        }
    }

    @Test
    fun `castOrNull should return receiver for incompatible types with generics`(): TestResult = runTest {
        // Given: a value of type List<Int>
        val value = listOf(1, 2, 3)

        // When: castOrNull is called with incompatible generic type
        val result = value.castOrNull<List<String>>()

        // Then: returns null
        result.shouldBe(value) // (inner) type-erasure lol
    }
}
