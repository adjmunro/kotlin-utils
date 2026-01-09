package nz.adjmunro.util.test.log.junit4

import nz.adjmunro.util.log.SystemPrintlnTree
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import timber.log.Timber

/**
 * A JUnit4 Rule that plants a [Timber.Tree] before each test and uproots it after each test.
 *
 * ```kotlin
 * // JUnit 4 Usage:
 * class MyClassTest {
 *   @get:Rule
 *   val timberRule = TimberRule()
 * }
 * ```
 */
public class TimberRule(
    public val tree: Timber.Tree = SystemPrintlnTree(),
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Timber.Forest.plant(tree)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Timber.Forest.uproot(tree)
    }
}
