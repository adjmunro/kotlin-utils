package nz.adjmunro.util.log

public interface Kotlogger {

    public fun logger(priority: KotloggerPriority, tag: String, message: Any?, throwable: Throwable? = null)

    public companion object {
        /**
         * The global instance of [Kotlogger].
         *
         * *Please assign this to a [Kotlogger] implementation before using any [KotloggerDsl] logging methods!*
         */
        @KotloggerDsl
        public lateinit var instance: Kotlogger

        /** A unique string added to the end of your log statement to help isolate your logs from the rest! */
        @KotloggerDsl
        public var uniqueTail: String = "###"

        /**
         * The global priority level.
         *
         * *A [log]'s priority must be greater than or equal to the [Enum.ordinal] of this [KotloggerPriority] value.
         * Any "lower priority log" (literally, in terms of ordinal value) will be ignored.*
         */
        @KotloggerDsl
        public var priority: KotloggerPriority = KotloggerPriority.VERBOSE

        /**
         * Throw for every [log] call that has a priority lower than this value.
         *
         * *Should be used to find and remove debugging logs when finished.*
         */
        @KotloggerDsl
        public var throwIfLogPriorityLowerThan: KotloggerPriority = KotloggerPriority.NONE
    }
}
