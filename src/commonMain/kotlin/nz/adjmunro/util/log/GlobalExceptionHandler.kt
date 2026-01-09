@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.log

import kotlin.system.exitProcess

/**
 * Global exception handler that ensures uncaught exceptions **always** print a
 * stacktrace, and then calls [callback] for any custom uncaught exception handling.

 * @param callback The callback to be called when an uncaught exception is thrown.
 */
public class GlobalExceptionHandler(
    public val callback: (Thread, Throwable) -> Unit = { _, _ -> },
) : Thread.UncaughtExceptionHandler {

    // Store the old uncaught exception handler to call it after this one.
    private val oldExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        // Replace the default uncaught exception handler with this one.
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * Called when an uncaught exception is thrown.
     *
     * @param t The thread that threw the exception.
     * @param e The uncaught exception.
     */
    override fun uncaughtException(t: Thread, e: Throwable) {
        try {
//            Kotlogger.e(t = e, message = "Unhandled Exception!")
            e.printStackTrace()
            callback(t, e)
        } catch (throwable: Throwable) {
//            Kotlogger.e(t = e, message = "Exception thrown when processing uncaught exception!")
            throwable.printStackTrace()
        } finally {
            oldExceptionHandler
                ?.uncaughtException(t, e)
                ?: exitProcess(status = 2)
        }
    }
}
