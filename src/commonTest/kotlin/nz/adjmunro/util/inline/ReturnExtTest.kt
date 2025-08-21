package nz.adjmunro.util.inline

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ReturnExtTest {
    @Test
    fun `itself should return the value itself`(): TestResult = runTest {
        // Given: a String value
        val value = "hello"

        // When: itself is called
        val result = itself(value = value)

        // Then: returns the value itself
        result.shouldBe(value)
    }

    @Test
    fun `caller should return the receiver itself`(): TestResult = runTest {
        // Given: an Int value
        val value = 42

        // When: caller is called
        val result = value.caller(ignore = true)

        // Then: returns the receiver itself
        result.shouldBe(value)
    }

    @Test
    fun `caller should return the receiver itself when ignore is default`(): TestResult = runTest {
        // Given: an Int value
        val value = 99

        // When: caller is called with default ignore
        val result = value.caller()

        // Then: returns the receiver itself
        result.shouldBe(value)
    }

    @Test
    fun `rethrow should throw the provided throwable`(): TestResult = runTest {
        // Given: an IllegalArgumentException
        val throwable = IllegalArgumentException("fail")

        // When/Then: rethrow throws the provided exception
        shouldThrow<IllegalArgumentException> {
            rethrow(throwable = throwable)
        }
    }

    @Test
    fun `nulls should always return null`(): TestResult = runTest {
        // Given: any value
        val value = "ignored"

        // When: nulls is called
        val result = nulls(ignore = value)

        // Then: returns null
        result.shouldBe(null)
    }

    @Test
    fun `nulls should always return null when ignore is default`(): TestResult = runTest {
        // When: nulls is called with default ignore
        val result = nulls()

        // Then: returns null
        result.shouldBe(null)
    }

    @Test
    fun `unit should always return Unit`(): TestResult = runTest {
        // Given: any value
        val value = "ignored"

        // When: unit is called
        val result = unit(ignore = value)

        // Then: returns Unit
        result.shouldBe(Unit)
    }

    @Test
    fun `unit should always return Unit when ignore is default`(): TestResult = runTest {
        // When: unit is called with default ignore
        val result = unit()

        // Then: returns Unit
        result.shouldBe(Unit)
    }

    @Test
    fun `truth should always return true`(): TestResult = runTest {
        // Given: any value
        val value = "anything"

        // When: truth is called
        val result = truth(ignore = value)

        // Then: returns true
        result.shouldBe(true)
    }

    @Test
    fun `truth should always return true when ignore is default`(): TestResult = runTest {
        // When: truth is called with default ignore
        val result = truth()

        // Then: returns true
        result.shouldBe(true)
    }

    @Test
    fun `falsehood should always return false`(): TestResult = runTest {
        // Given: any value
        val value = "anything"

        // When: falsehood is called
        val result = falsehood(ignore = value)

        // Then: returns false
        result.shouldBe(false)
    }

    @Test
    fun `falsehood should always return false when ignore is default`(): TestResult = runTest {
        // When: falsehood is called with default ignore
        val result = falsehood()

        // Then: returns false
        result.shouldBe(false)
    }
}
