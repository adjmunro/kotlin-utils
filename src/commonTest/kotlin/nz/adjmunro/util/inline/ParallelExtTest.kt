package nz.adjmunro.util.inline

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class ParallelExtTest {
    @Test
    fun `parallelMap on Iterable returns correct results in parallel`(): TestResult = runTest {
        // Given: An iterable of integers
        val input = listOf(1, 2, 3, 4)

        // When: parallelMap is called with a transform that delays and doubles the value
        val result = input.parallelMap {
            delay(10)
            it * 2
        }

        // Then: The result should be a list of doubled values
        result.shouldContainExactly(listOf(2, 4, 6, 8))
    }

    @Test
    fun `parallelMap on Sequence return correct results in parallel`(): TestResult = runTest {
        // Given: A sequence of integers
        val input = sequenceOf(5, 6, 7)

        // When: parallelMap is called and results are awaited
        val result: List<Int> = input.parallelMap {
            delay(5)
            it + 1
        }

        // Then: The result should be a list of incremented values
        result.shouldContainExactly(listOf(6, 7, 8))
    }

    @Test
    fun `parallelMap on empty Iterable returns empty list`(): TestResult = runTest {
        // Given: An empty iterable
        val input = emptyList<Int>()

        // When: parallelMap is called
        val result = input.parallelMap { it * 2 }

        // Then: The result should be an empty list
        result.shouldBe(emptyList())
    }

    @Test
    fun `parallelMap on empty Sequence returns empty list`(): TestResult = runTest {
        // Given: An empty sequence
        val input = emptySequence<Int>()

        // When: parallelMap is called
        val result = input.parallelMap { it * 2 }

        // Then: The result should be an empty sequence
        result.iterator().hasNext() shouldBe false
    }
}
