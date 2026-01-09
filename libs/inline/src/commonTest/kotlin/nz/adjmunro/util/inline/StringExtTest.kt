package nz.adjmunro.util.inline

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class StringExtTest {
    @Test
    fun `stringItself should return string representation of value`(): TestResult = runTest {
        // Given: a value of type Int
        val value = 123

        // When: stringItself is called
        val result = stringItself(value = value)

        // Then: returns string representation
        result.shouldBe("123")
    }
    @Test
    fun `stringCaller should return string representation of receiver`(): TestResult = runTest {
        // Given: a value of type Double
        val value = 45.67

        // When: stringCaller is called
        val result = value.stringCaller(ignore = true)

        // Then: returns string representation
        result.shouldBe("45.67")
    }

    @Test
    fun `stringCaller should return string representation of receiver when ignore is default`(): TestResult = runTest {
        // Given: a value of type Double
        val value = 45.67

        // When: stringCaller is called
        val result = value.stringCaller()

        // Then: returns string representation
        result.shouldBe("45.67")
    }


    @Test
    fun `emptyString should always return empty string`(): TestResult = runTest {
        // Given: any value
        val value = "ignored"

        // When: emptyString is called
        val result = emptyString(ignore = value)

        // Then: returns empty string
        result.shouldBe("")
    }

    @Test
    fun `emptyString should always return empty string when ignore is default`(): TestResult = runTest {
        // When: emptyString is called
        val result = emptyString()

        // Then: returns empty string
        result.shouldBe("")
    }

    @Test
    fun `orNull should return null for blank, empty, null, or 'null' string`(): TestResult = runTest {
        // Given: blank, empty, null, and 'null' strings
        val blank = "   "
        val empty = ""
        val nullString: String? = null
        val literalNull = "null"

        // When/Then: orNull returns null for all
        blank.orNull().shouldBe(null)
        empty.orNull().shouldBe(null)
        nullString.orNull().shouldBe(null)
        literalNull.orNull().shouldBe(null)
    }

    @Test
    fun `orNull should return original string for valid content`(): TestResult = runTest {
        // Given: a valid string
        val value = "hello"

        // When: orNull is called
        val result = value.orNull()

        // Then: returns original string
        result.shouldBe("hello")
    }
}
