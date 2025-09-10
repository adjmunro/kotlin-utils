package nz.adjmunro.util.log

import nz.adjmunro.util.log.CallsiteFrame.Companion.EmptyCallsiteFrame
import kotlin.jvm.optionals.getOrNull

public class JvmCallsiteWalker : CallsiteFrame.CallsiteWalker {
    override fun build(): CallsiteFrame {
        val frame = StackWalker
            .getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES)
            .walk { frames ->
                frames.filter { it.fileName !in CallsiteFrame.callstackFilters }
                    .findFirst()
                    .getOrNull()
            }
            ?: return EmptyCallsiteFrame

        return object : CallsiteFrame {
            override val fileName: String = frame.fileName ?: "UnknownFile.kt"
            override val lineNumber: Int = frame.lineNumber
            override val className: String = frame.className
            override val methodName: String = frame.methodName

            override val link: CallsiteFrame.FrameLink = CallsiteFrame.FrameLink(fileName, lineNumber)

            override val stackTraceElement: StackTraceElement
                get() = StackTraceElement(
                    /* declaringClass = */ className,
                    /* methodName = */ methodName,
                    /* fileName = */ fileName,
                    /* lineNumber = */ lineNumber,
                )
        }
    }
}
