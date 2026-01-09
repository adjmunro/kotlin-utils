package nz.adjmunro.util.test.log.junit5

import nz.adjmunro.util.log.SystemPrintlnTree
import nz.adjmunro.util.test.common.junit5.AfterCallback
import nz.adjmunro.util.test.common.junit5.BeforeCallback
import org.junit.jupiter.api.extension.ExtensionContext
import timber.log.Timber

/**
 * A JUnit5 Extension that plants a [Timber.Tree] before each test and uproots it after each test.
 *
 * ```kotlin
 * // JUnit 5 Usage:
 * @ExtendWith(TimberExtension::class)
 * class MyTest { ... }
 * ```
 *
 * @see nz.adjmunro.util.test.common.junit5.AggregateTestExtension
 */
public interface TimberExtension : BeforeCallback, AfterCallback {

    /** The [Timber.Tree] to plant for logging. */
    public val tree: Timber.Tree

    override fun before(context: ExtensionContext) {
        Timber.Forest.plant(tree)
    }

    override fun after(context: ExtensionContext) {
        Timber.Forest.uproot(tree)
    }

    /**
     * A [TimberExtension] that uses [SystemPrintlnTree] for logging.
     *
     * ```kotlin
     * // JUnit 5 Usage:
     * @ExtendWith(TimberExtension.Println::class)
     * class MyTest { ... }
     * ```
     */
    public class Println : TimberExtension {
        override val tree: Timber.Tree = SystemPrintlnTree()
    }
}
