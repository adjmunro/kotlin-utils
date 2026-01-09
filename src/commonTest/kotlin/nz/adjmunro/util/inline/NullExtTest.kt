package nz.adjmunro.util.inline

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.TestResult

class NullExtTest {
    @Test
    fun `exists should execute block if receiver is not null`(): TestResult = runTest {
        // Given: a non-null receiver
        val receiver = "hello"

        // When: exists is called
        val result = exists(
            receiver = receiver,
            block = { this.uppercase() },
        )

        // Then: block is executed and result is uppercase
        result.shouldBe("HELLO")
    }

    @Test
    fun `exists should return null if receiver is null`(): TestResult = runTest {
        // Given: a null receiver
        val receiver: String? = null

        // When: exists is called
        val result = exists(
            receiver = receiver,
            block = { this.uppercase() },
        )

        // Then: result is null
        result.shouldBe(null)
    }

    @Test
    fun `T_exists should execute block if receiver is not null`(): TestResult = runTest {
        // Given: a non-null receiver
        val receiver = "world"

        // When: exists extension is called
        val result = receiver.exists {
            this.reversed()
        }

        // Then: block is executed and result is reversed
        result.shouldBe("dlrow")
    }

    @Test
    fun `T_exists should return null if receiver is null`(): TestResult = runTest {
        // Given: a null receiver
        val receiver: String? = null

        // When: exists extension is called
        val result = receiver.exists {
            this.reversed()
        }

        // Then: result is null
        result.shouldBe(null)
    }

    @Test
    fun `orElse should return value if not null`(): TestResult = runTest {
        // Given: a non-null receiver
        val receiver = "orElse"

        // When: orElse is called
        val result = receiver.orElse(
            none = { NullPointerException("should not be called") },
        )

        // Then: returns the receiver
        result.shouldBe("orElse")
    }

    @Test
    fun `orElse should call none if receiver is null`(): TestResult = runTest {
        // Given: a null receiver
        val receiver = null

        // When: orElse is called
        val result = receiver.orElse(
            none = { "fallback" },
        )

        // Then: returns fallback value
        result.shouldBe("fallback")
    }

    @Test
    fun `orElse should propagate exception from none if receiver is null`(): TestResult = runTest {
        // Given: a null receiver
        val receiver = null

        // When/Then: orElse throws exception from none
        shouldThrow<NullPointerException> {
            receiver.orElse()
        }
    }
}
