package nz.adjmunro.util.inline

import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.TestResult

class FoldExtTest {
    @Test
    fun `boolfold should return truthy when predicate is true`(): TestResult = runTest {
        // Given: a value and a predicate that returns true
        val value = 42

        // When: boolfold is called
        val result = value.boolfold(
            predicate = { this > 0 },
            falsy = { -1 },
            truthy = { 100 }
        )

        // Then: truthy is returned
        result.shouldBe(100)
    }

    @Test
    fun `boolfold should return falsy when predicate is false`(): TestResult = runTest {
        // Given: a value and a predicate that returns false
        val value = 42

        // When: boolfold is called
        val result = value.boolfold(
            predicate = { this < 0 },
            falsy = { -1 },
            truthy = { 100 }
        )

        // Then: falsy is returned
        result.shouldBe(-1)
    }

    @Test
    fun `flatmap should return truthy when predicate is true`(): TestResult = runTest {
        // Given: a value and a predicate that returns true
        val value = 7

        // When: flatmap is called
        val result = value.flatmap(
            predicate = { this % 2 == 1 },
            falsy = { 0 },
            truthy = { this * 2 }
        )

        // Then: truthy is returned
        result.shouldBe(14)
    }

    @Test
    fun `flatmap should return truthy when predicate is true and falsy is default`(): TestResult = runTest {
        // Given: a value and a predicate that returns true
        val value = 7

        // When: flatmap is called
        val result = value.flatmap(
            predicate = { this % 2 == 1 },
            truthy = { this * 2 }
        )

        // Then: truthy is returned
        result.shouldBe(14)
    }

    @Test
    fun `flatmap should return falsy when predicate is false`(): TestResult = runTest {
        // Given: a value and a predicate that returns false
        val value = 8

        // When: flatmap is called
        val result = value.flatmap(
            predicate = { this % 2 == 1 },
            falsy = { this / 2 },
            truthy = { this * 2 }
        )

        // Then: falsy is returned
        result.shouldBe(4)
    }

    @Test
    fun `flatmap should return falsy when predicate is false and truthy is default`(): TestResult = runTest {
        // Given: a value and a predicate that returns false
        val value = 8

        // When: flatmap is called
        val result = value.flatmap(
            predicate = { this % 2 == 1 },
            falsy = { this / 2 }
        )

        // Then: falsy is returned
        result.shouldBe(4)
    }

    @Test
    fun `flatmap should return itself when no truthy or falsy provided`(): TestResult = runTest {
        // Given: a value and a predicate that returns false
        val value = 99

        // When: flatmap is called with default lambdas
        val result = value.flatmap(predicate = { false })

        // Then: returns itself
        result.shouldBe(99)
    }

    @Test
    fun `nullfold should call some if not null, none if null`() {
        // Given
        val value: String? = "hello"
        // When
        val resultSome = value.nullfold(
            none = { "none" },
            some = { it.uppercase() }
        )
        // Then
        resultSome.shouldBe(expected = "HELLO")

        // When
        val nullValue: String? = null
        val resultNone = nullValue.nullfold(
            none = { "none" },
            some = { it.uppercase() }
        )
        // Then
        resultNone.shouldBe(expected = "none")
    }

    @Test
    fun `nullfold should pass NullPointerException to none`() {
        // Given
        val nullValue: String? = null
        // When
        val result = nullValue.nullfold(
            none = { it.message ?: "no message" },
            some = { it }
        )
        // Then
        result.shouldBe(expected = "Nullfold source was null.")
    }

    @Test
    fun `nullfold should call none with NullPointerException if none expects exception`(): TestResult = runTest {
        // Given: a null value
        val nullValue: String? = null

        // When: nullfold is called with none expecting exception
        var exceptionMessage = ""
        val result = nullValue.nullfold(
            none = { exception ->
                exceptionMessage = exception.message ?: "no message"
                "handled"
            },
            some = { it },
        )

        // Then: none is called with NullPointerException and result is handled
        exceptionMessage.shouldBe("Nullfold source was null.")
        result.shouldBe("handled")
    }

    @Test
    fun `nullfold should call some with non-null value and none with null value for different types`(): TestResult = runTest {
        // Given: a non-null Int value
        val intValue: Int? = 42
        // When: nullfold is called
        val resultSome = intValue.nullfold(
            none = { "none" },
            some = { it * 2 },
        )

        // Then: some is called and result is doubled
        resultSome.shouldBe(84)

        // Given: a null Int value
        val nullInt: Int? = null
        // When: nullfold is called
        val resultNone = nullInt.nullfold(
            none = { "none" },
            some = { it * 2 },
        )

        // Then: none is called and result is none
        resultNone.shouldBe("none")
    }

    @Test
    fun `throwfold should call pass when value is not Throwable and throws when value is Throwable`(): TestResult = runTest {
        // Given: a value that is not Throwable
        val value: Any = 123
        // When: throwfold is called
        val resultPass = value.throwfold(
            throws = { "should not be called" },
            pass = { it as Int * 2 },
        )

        // Then: pass is called and result is doubled
        resultPass.shouldBe(246)

        // Given: a value that is Throwable
        val throwableValue: Any = IllegalArgumentException("bad")
        // When: throwfold is called
        val resultThrows = throwableValue.throwfold(
            throws = { it.message ?: "no msg" },
            pass = { "should not be called" },
        )

        // Then: throws is called and result is message
        resultThrows.shouldBe("bad")
    }

    @Test
    fun `throwfold should call throws if Throwable, pass otherwise`() {
        // Given
        val value: Any = IllegalStateException("fail")
        // When
        val resultThrows = value.throwfold(
            throws = { it.message ?: "no msg" },
            pass = { "ok" }
        )
        // Then
        resultThrows.shouldBe(expected = "fail")

        // When
        val okValue: Any = "ok"
        val resultPass = okValue.throwfold(
            throws = { "fail" },
            pass = { it as String }
        )
        // Then
        resultPass.shouldBe(expected = "ok")
    }
}
