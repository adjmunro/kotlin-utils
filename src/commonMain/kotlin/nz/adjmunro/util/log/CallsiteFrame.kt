package nz.adjmunro.util.log

public interface CallsiteFrame {
    public val fileName: String
    public val lineNumber: Int
    public val className: String
    public val methodName: String
    public val stackTraceElement: StackTraceElement?
    public val link: FrameLink?

    @JvmInline
    public value class FrameLink(public val link: String) {
        public constructor(filename: String, lineNumber: Int, filetype: String = ".kt") : this(
            link = when {
                filename.endsWith(suffix = filetype) -> "$filename:$lineNumber"
                else -> "$filename$filetype:$lineNumber"
            }
        )
        override fun toString(): String = link
    }

    public interface CallsiteWalker {
        public fun build(): CallsiteFrame
    }

    public companion object {
        /** Stack-frame filters to exclude before locating the callsite link. */
        public val callstackFilters: MutableSet<String> = mutableSetOf(
            "KotloggerExt.kt", // Logging extensions for Timber
            "Timber.kt" // Timber's own file // TODO move to Timber-specific implementation
        )

        public val EmptyCallsiteFrame: CallsiteFrame = object : CallsiteFrame {
            override val fileName: String = ""
            override val lineNumber: Int = -1
            override val className: String = ""
            override val methodName: String = ""
            override val stackTraceElement: StackTraceElement? = null
            override val link: FrameLink? = null
        }
    }

}
