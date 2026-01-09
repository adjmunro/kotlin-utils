//package nz.adjmunro.util.log
//
//import android.annotation.SuppressLint
//import android.os.Build
//import android.util.Log
//import timber.log.Timber
//import java.io.PrintStream
//import kotlin.jvm.optionals.getOrNull
//
//sealed interface OrbitDebugTree {
//    /**
//     * A [timber.log.Timber] tree that logs messages to the system's standard output and error streams.
//     *
//     * *Useful in tests.*
//     */
//    class SystemPrintln(val maxMessageLength: Int = 100) : Timber.DebugTree() {
//        @SuppressLint("NewApi")
//        @Suppress("ReplacePrintlnWithLogging")
//        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//            // Fallback tag to a default value if `null`, "null", or blank.
//            val tag: String = if (Build.VERSION.SDK_INT == 0 || Build.VERSION.SDK_INT > 33) callsiteStackFrameLink()
//            else tag.takeUnless { it.isNullOrBlank() || it == "null" }.fallback { "OrbitDebugTree" }
//
//            // Determine the output stream based on the log priority.
//            val stream: PrintStream = when(priority) {
//                Log.WARN, Log.ERROR, Log.ASSERT -> System.err
//                else -> System.out
//            }
//
//            // Format the log level based on the priority.
//            val level: String = when (priority) {
//                Log.VERBOSE -> "[VERBOSE] "
//                Log.DEBUG -> "[DEBUG] "
//                Log.INFO -> "[INFO] "
//                Log.WARN -> "[WARN] "
//                Log.ERROR -> "[ERROR] "
//                else -> ""
//            }
//
//            if (message.length < maxMessageLength) {
//                // Print the log message to the appropriate stream.
//                stream.println("$level$tag: $message")
//            } else {
//                // Copied directly from Timber's DebugTree implementation (& modified slightly).
//                // Split by line, then ensure each line can fit into Log's maximum length.
//                var i = 0
//                val length = message.length
//                while (i < length) {
//                    var newline = message.indexOf(char = '\n', startIndex = i)
//                    newline = if (newline != -1) newline else length
//                    do {
//                        val end = newline.coerceAtMost(maximumValue = i + maxMessageLength)
//                        val part = message.substring(i, end)
//                        // Print the log message to the appropriate stream.
//                        stream.println("$level$tag: $part")
//                        i = end
//                    } while (i < newline)
//                    i++
//                }
//            }
//
//            // If there is a throwable, print its message and stack trace to the stream.
//            t?.let {
//                stream.println("$level$tag: Got ${it.message}")
//                it.printStackTrace()
//            }
//        }
//    }
//
//    /**
//     * A [Timber] tree that logs messages to the Android log system.
//     *
//     * *Use in ordinary environments.*
//     */
//    class AndroidLog : Timber.DebugTree() {
//        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//            if (Build.VERSION.SDK_INT > 33) super.log(
//                priority = priority,
//                tag = tag,
//                message = "${callsiteStackFrameLink()} $message",
//                t = t
//            ) else super.log(priority, tag, message, t)
//        }
//    }
//
//    companion object {
//
//        /** Stack-frame filters to exclude before locating the callsite link. */
//        private val callstackFilters: Set<String> = setOf(
//            "OrbitDebugTree.kt", // must match file name
//            "KotloggerExt.kt", // Logging extensions for Timber
//            "Timber.kt" // Timber's own file
//        )
//
//        /**
//         * Returns a string representing the callsite stack frame link.
//         *
//         * This function uses the `StackWalker` API to find the first stack frame that is not filtered
//         * by the `callstackFilters` set. It returns a string in the format "fileName:lineNumber".
//         *
//         * Requires API level 34 (Android 14) or higher.
//         */
//        @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//        internal fun callsiteStackFrameLink(): String = StackWalker
//            .getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES)
//            .walk { frames ->
//                frames
//                    .filter { it.fileName !in callstackFilters }
//                    .findFirst()
//                    .getOrNull()
//                    ?.let { "${it.fileName}:${it.lineNumber}" }
//                    ?: "Unknown"
//            }
//    }
//}
