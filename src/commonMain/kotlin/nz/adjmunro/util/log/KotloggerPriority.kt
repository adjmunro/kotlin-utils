package nz.adjmunro.util.log

import nz.adjmunro.util.log.KotloggerPriority.ASSERT
import nz.adjmunro.util.log.KotloggerPriority.DEBUG
import nz.adjmunro.util.log.KotloggerPriority.ERROR
import nz.adjmunro.util.log.KotloggerPriority.INFO
import nz.adjmunro.util.log.KotloggerPriority.NONE
import nz.adjmunro.util.log.KotloggerPriority.VERBOSE
import nz.adjmunro.util.log.KotloggerPriority.WARN

/**
 * Represents the different log levels available in the logging system.
 *
 * - Each log level corresponds to a specific severity of log messages.
 * - [Kotlogger.priority] filters logs based on their priority level according to the [ordinal] value of this enum.
 * - [Kotlogger.throwIfLogPriorityLowerThan] can be used to find and remove debugging logs (hence why [DEBUG] has a lower priority than [VERBOSE]).
 *
 * *These log levels are roughly based on the levels in Android's Log class.*
 *
 * @property NONE The lowest priority level. No logging will be performed.
 * @property DEBUG Detailed information for debugging purposes.
 * @property VERBOSE Very detailed information, typically used for tracing execution.
 * @property INFO Informational messages that highlight the progress of the application.
 * @property WARN Potentially harmful situations.
 * @property ERROR Error conditions that need attention.
 * @property ASSERT The highest priority level. Critical conditions that should never occur.
 */
public enum class KotloggerPriority {
    /**
     * The lowest priority level. No logging will be performed.
     *
     * *Assigning [NONE] to [Kotlogger.priority] will ignore all Kotlogger logs.*
     */
    NONE,

    /** Detailed information for debugging purposes. */
    DEBUG,

    /** Very detailed information, typically used for tracing execution. */
    VERBOSE,

    /** Informational messages that highlight the progress of the application. */
    INFO,

    /** Potentially harmful situations. */
    WARN,

    /** Error conditions that need attention. */
    ERROR,

    /** The highest priority level. Critical conditions that should never occur. */
    ASSERT;

    /** Companion object for multiplatform utility functions related to LogPriority. */
    public companion object
}
