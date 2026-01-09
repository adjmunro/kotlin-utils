//package nz.adjmunro.util.log
//
//import android.os.Build
//import android.util.Log
//import androidx.annotation.RequiresApi
//import com.orbitremit.agnostic.nullfold
//import com.orbitremit.agnostic.stringItself
//import com.orbitremit.agnostic.truthy
//import com.orbitremit.core.domain.CallsiteFrame.Companion.EmptyCallsiteFrame
//import com.orbitremit.core.domain.CallsiteFrame.Companion.toCallsiteFrame
//import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.flow.update
//import nz.adjmunro.inline.nullfold
//import nz.adjmunro.inline.stringItself
//import nz.adjmunro.inline.truth
//import nz.adjmunro.util.log.CallsiteFrame.Companion.EmptyCallsiteFrame
//import nz.adjmunro.util.log.CallsiteFrame.Companion.toCallsiteFrame
//import timber.log.Timber
//import kotlin.contracts.ContractBuilder
//import kotlin.contracts.ExperimentalContracts
//import kotlin.contracts.InvocationKind
//import kotlin.contracts.contract
//import kotlin.jvm.optionals.getOrNull
//import kotlin.properties.ReadWriteProperty
//import kotlin.reflect.KProperty
//import kotlin.text.endsWith
//import kotlin.text.orEmpty
//import kotlin.toString
//
///**
// *  IMPORTANT!
// *  Do NOT use `inline` in this file!
// *  It prevents accurate callsite information from being captured!
// */
//

//// TODO: can i use inline if i inject FrameLink?
//// TODO: add user note that you can manually write `filename.kt:lineNumber` to create a link

//
//@OptIn(ExperimentalContracts::class)
//private fun <T> T.logItself(
//    priority: Int,
//    predicate: T.() -> Boolean = ::truth,
//    formatter: CallsiteFrameFormatter<T> = ::stringItself
//): T {
//    contract { callsInPlace(predicate, InvocationKind.EXACTLY_ONCE) }
//
//    when {
//        !predicate() -> return this@logItself
//
//        // If the object is null, log it as [null] with the callsite information.
//        this == null -> Timber.log(
//            priority = priority,
//            message = "[null] ${formatter(EmptyCallsiteFrame, this@logItself)} $LOGCAT_FILTER",
//        )
//
//        // StackWalker is expensive! So only use on debug builds (and are running on Android API >34).
//        BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 34 -> with(receiver = callsiteWalker()) {
//            Timber.tag(tag = link?.toString().orEmpty()).log(
//                priority = priority,
//                message = "${formatter(this@logItself)} $LOGCAT_FILTER"
//            )
//        }
//
//        // Fallback for Android API < 34 or non-debug builds.
//        else -> Timber.log(
//            priority = priority,
//            message = "${formatter(EmptyCallsiteFrame, this@logItself)} $LOGCAT_FILTER"
//        )
//    }
//
//    return this@logItself
//}
//
////@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//private fun callsiteWalker(): CallsiteFrame = StackWalker
//    .getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES)
//    .walk { frames ->
//        frames.filter { it.fileName != "LogUtils.kt" } // todo check packed path? or voluntarily inject?
//            .findFirst()
//            .getOrNull()
//            .toCallsiteFrame()
//    }
//
//public interface CallsiteFrame {
//    public val fileName: String
//    public val lineNumber: Int
//    public val className: String
//    public val methodName: String
//    public val link: FrameLink?
//    public val stackTraceElement: StackTraceElement?
//
//    public companion object Companion {
//        public val EmptyCallsiteFrame: CallsiteFrame = object : CallsiteFrame {
//            override val fileName: String = ""
//            override val lineNumber: Int = -1
//            override val className: String = ""
//            override val methodName: String = ""
//            override val link: FrameLink? = null
//            override val stackTraceElement: StackTraceElement? = null
//        }
//
////        @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//        public fun StackWalker.StackFrame?.toCallsiteFrame(): CallsiteFrame = nullfold(none = { EmptyCallsiteFrame }) {
//            object : CallsiteFrame {
//                override val fileName: String = it.fileName ?: "UnknownFile.kt"
//                override val lineNumber: Int = it.lineNumber
//                override val className: String = it.className
//                override val methodName: String = it.methodName
//
//                override val link: FrameLink = FrameLink(fileName, lineNumber)
//                override val stackTraceElement: StackTraceElement
//                    get() = StackTraceElement(
//                        /* declaringClass = */ className,
//                        /* methodName = */ methodName,
//                        /* fileName = */ fileName,
//                        /* lineNumber = */ lineNumber,
//                    )
//            }
//        }
//    }
//}
//
//@JvmInline
//public value class FrameLink(public val link: String) {
//    public constructor(filename: String, lineNumber: Int, filetype: String = ".kt") : this(
//        link = when {
//            filename.endsWith(suffix = filetype) -> "$filename:$lineNumber"
//            else -> "$filename$filetype:$lineNumber"
//        }
//    )
//    override fun toString(): String = link
//}
//
//public interface Tracer<in BackingField, IntermediateType, out ActualWrapper> {
//    public val trace: ActualWrapper
//    public val transform: (BackingField) -> IntermediateType
//    public val get: CallsiteFrame.(IntermediateType) -> Any
//}
//
//public interface MutableTracer<in BackingField, IntermediateType, out ActualWrapper>: Tracer<BackingField, IntermediateType, ActualWrapper> {
//    public val set: CallsiteFrame.(IntermediateType, IntermediateType) -> Any
//}
//
//@OptIn(ExperimentalForInheritanceCoroutinesApi::class) // This is fine, since we delegate, not override.
//public class MutableStateFlowTracer<T, R>(
//    override val trace: MutableStateFlow<T>,
//    override val get: CallsiteFrame.(R) -> Any,
//    override val set: CallsiteFrame.(R, R) -> Any,
//    override val transform: (T) -> R,
//): MutableTracer<T, R, MutableStateFlow<T>>, MutableStateFlow<T> by trace {
//    public fun update(block: (T) -> T) {
//        trace.update { old: T -> block(old).debug { new: T -> set(transform(old), transform(new)) } }
//    }
//
//    public suspend fun collect(block: suspend (T) -> T) {
//        trace.collect { value: T -> block(value).debug { get(transform(value)) } }
//    }
//}
//
//@OptIn(ExperimentalForInheritanceCoroutinesApi::class) // This is fine, since we delegate, not override.
//public class StateFlowTracer<T, R>(
//    override val trace: StateFlow<T>,
//    override val get: CallsiteFrame.(R) -> Any,
//    override val transform: (T) -> R,
//): Tracer<T, R, StateFlow<T>>, StateFlow<T> by trace {
//
//    public fun map(block: suspend (T) -> T): Flow<T> = trace.map { value: T ->
//        block(value).debug { get(transform(value)) }
//    }
//
//    public fun onEach(block: suspend (T) -> T): Flow<T> = trace.onEach { value: T ->
//        block(value).debug { get(transform(value)) }
//    }
//
//    public suspend fun collect(block: suspend (T) -> T): Nothing = trace.collect { value: T ->
//        block(value).debug { get(transform(value)) }
//    }
//}
//
////@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
////class StateFlowTracer<T>(val trace: StateFlow<T>): Tracer<T>, StateFlow<T> by trace
////@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
////class MutableSharedFlowTracer<T>(val trace: MutableSharedFlow<T>): Tracer<T>, MutableSharedFlow<T> by trace
////@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
////class SharedFlowTracer<T>(val trace: SharedFlow<T>): Tracer<T>, SharedFlow<T> by trace
////class FlowTracer<T>(val trace: Flow<T>): Tracer<T>, Flow<T> by trace
////class FlowCollectorTracer<T>(val trace: FlowCollector<T>): Tracer<T>, FlowCollector<T> by trace
////class SendChannelTracer<T>(val trace: SendChannel<T>) : Tracer<T>, SendChannel<T> by trace
////class ReceiveChannelTracer<T>(val trace: Channel<T>) : Tracer<T>, Channel<T> by trace
////class ChannelTracer<T>(val trace: Channel<T>) : Tracer<T>, Channel<T> by trace
//
//
//@JvmName("trace_MutableStateFlow_String")
//public fun <T> MutableStateFlow<T>.trace(
//    get: CallsiteFrame.(String) -> Any = { "$methodName() -> $it" },
//    set: CallsiteFrame.(String, String) -> Any = { old, new -> "$methodName($old -> $new)" },
//    transform: (T) -> String = ::stringItself,
//): MutableStateFlowTracer<T, String> = MutableStateFlowTracer(
//    trace = this@trace,
//    transform = transform,
//    get = get,
//    set = set,
//)
//
//@JvmName("trace_MutableStateFlow")
//public fun <T, R> MutableStateFlow<T>.trace(
//    get: CallsiteFrame.(R) -> Any = { "$methodName() -> $it" },
//    set: CallsiteFrame.(R, R) -> Any = { old, new -> "$methodName($old -> $new)" },
//    transform: (T) -> R,
//): MutableStateFlowTracer<T, R> = MutableStateFlowTracer(
//    trace = this@trace,
//    transform = transform,
//    get = get,
//    set = set,
//)
//
//@JvmName("trace_StateFlow_String")
//public fun <T> StateFlow<T>.trace(
//    get: CallsiteFrame.(String) -> Any = { "$methodName() -> $it" },
//    transform: (T) -> String = ::stringItself,
//): StateFlowTracer<T, String> = StateFlowTracer(
//    trace = this@trace,
//    transform = transform,
//    get = get,
//)
//
//@JvmName("trace_StateFlow")
//public fun <T, R> StateFlow<T>.trace(
//    get: CallsiteFrame.(R) -> Any = { "$methodName() -> $it" },
//    transform: (T) -> R,
//): StateFlowTracer<T, R> = StateFlowTracer(
//    trace = this@trace,
//    transform = transform,
//    get = get,
//)
//
//public inline fun <T> trace(
//    crossinline initialValue: () -> T,
//) : ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
//
//    private var field: T = initialValue()
//
//    public override fun getValue(thisRef: Any, property: KProperty<*>): T = field
//
//    public override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
//        field = value
//    }
//
//}
//
//private class Test {
//    val string by trace { "Initial Value" }
//    val stateflow by trace { MutableStateFlow("Initial StateFlow Value") }
//
//    init {
//        stateflow.update {
//            "Updated StateFlow Value".debug { "Updating state flow with value: $it" }
//        }
//    }
//}
