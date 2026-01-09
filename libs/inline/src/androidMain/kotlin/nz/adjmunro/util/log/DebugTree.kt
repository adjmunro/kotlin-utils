//package nz.adjmunro.util.log
//
//import android.util.Log
//import java.io.PrintStream
//
//public class DebugTree : Tree() {
//    @Suppress("ReplacePrintlnWithLogging")
//    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//        // Fallback tag to a default value if `null`, "null", or blank.
//        val tag: String = tag
//            .takeUnless { it.isNullOrBlank() || it == "null" }
//            .fallback { "OrbitDebugTree" }
//
//        // Determine the output stream based on the log priority.
//        val stream: PrintStream = when(priority) {
//            Log.WARN, Log.ERROR, Log.ASSERT -> System.err
//            else -> System.out
//        }
//
//        // Format the log level based on the priority.
//        val level: String = when (priority) {
//            Log.VERBOSE -> "[VERBOSE] "
//            Log.DEBUG -> "[DEBUG] "
//            Log.INFO -> "[INFO] "
//            Log.WARN -> "[WARN] "
//            Log.ERROR -> "[ERROR] "
//            else -> ""
//        }
//
//        // Print the log message to the appropriate stream.
//        stream.println("$level$tag: $message")
//
//        // If there is a throwable, print its message and stack trace to the stream.
//        t?.let {
//            stream.println("$level$tag: Got ${it.message}")
//            it.printStackTrace()
//        }
//    }
//}
