//package nz.adjmunro.util.concurrent
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import androidx.lifecycle.repeatOnLifecycle
//import kotlinx.coroutines.channels.BufferOverflow
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
//import kotlinx.coroutines.channels.ReceiveChannel
//import kotlinx.coroutines.channels.SendChannel
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flowOn
//import kotlinx.coroutines.flow.receiveAsFlow
//import kotlinx.coroutines.withContext
//
///**
// * A [ReceiveChannel] designed for one-off side effects (e.g. navigation, toasts, dialogs) to be consumed
// * by a single observer.
// *
// * @see MutableSideEffectChannel
// * @see MutableSideEffect
// */
//interface SideEffectChannel<T> : ReceiveChannel<T> {
//    /** A [Flow] that emits items sent to the [Channel]. */
//    val flow: Flow<T>
//
//    /**
//     * Collects items from the [Channel] as a cold [Flow].
//     *
//     * @param action The suspend function that processes each emitted item.
//     */
//    suspend fun collect(action: suspend (T) -> Unit)
//}
//
///**
// * A [SendChannel] variant of [SideEffectChannel] that allows element [update].
// *
// * @see SideEffectChannel
// * @see MutableSideEffect
// */
//interface MutableSideEffectChannel<T> : SideEffectChannel<T>, SendChannel<T> {
//    /**
//     * [Send][send] the result of [block] to the [Channel].
//     *
//     * *While this function is intended to look like StateFlow's `update`, we have no way to peek
//     * at the current value of the [Channel] without consuming it.*
//     *
//     * @param block The suspend function that produces the value to be sent to the channel.
//     */
//    suspend fun update(block: suspend () -> T)
//}
//
///**
// * A mutable [SideEffectChannel] implementation using a [Channel] with [RENDEZVOUS] capacity and
// * [BufferOverflow.SUSPEND] strategy.
// *
// * *This [SideEffectChannel.flow] should be collected on [Dispatchers.Immediate] to avoid dropped emissions.*
// *
// * @param T The type of items sent to and received from the channel.
// * @see SideEffectChannel
// * @see MutableSideEffectChannel
// */
//@Suppress("FunctionName")
//fun <T> MutableSideEffect(
//    channel: Channel<T> = Channel(
//        capacity = RENDEZVOUS,
//        onBufferOverflow = BufferOverflow.SUSPEND,
//    ),
//): MutableSideEffectChannel<T> = object : MutableSideEffectChannel<T>, Channel<T> by channel {
//    /**
//     * A [Flow] that emits items sent to the [channel] on [Dispatchers.Immediate] to avoid (very
//     * rare) dropped emission issues on configuration change.
//     */
//    override val flow: Flow<T>
//        get() = channel
//            .receiveAsFlow()
//            .flowOn(context = Dispatchers.Immediate)
//
//    /**
//     * Collects items from the [channel] as a cold [Flow] on [Dispatchers.Immediate] to
//     * avoid (very rare) dropped emission issues on configuration change.
//     */
//    override suspend fun collect(action: suspend (T) -> Unit) {
//        // Collect on Immediate to avoid (very rare) dropped emission issues on configuration change.
//        withContext(context = Dispatchers.Immediate) {
//            flow.collect { element: T -> action(element) }
//        }
//    }
//
//    /**
//     * [Send][send] the result of [block] to the [channel] (on [Dispatchers.Main] to avoid
//     * potentially dropped emissions on configuration change).
//     *
//     * *While this function is intended to look like StateFlow's `update`, we have no way to peek
//     * at the current value of the [Channel] without consuming it.*
//     *
//     * @param block The suspend function that produces the value to be sent to the channel.
//     */
//    override suspend fun update(block: suspend () -> T) {
//        val element: T = block()
//
//        // We want to emit on Main to avoid (very rare) dropped emission issues on configuration change.
//        withContext(context = Dispatchers.Main) { send(element = element) }
//    }
//}
//
///**
// * Observes a [Flow] in a composable, collecting emissions when the lifecycle is at least
// * [Lifecycle.State.STARTED]. The collection is done on [Dispatchers.Immediate] to avoid (very
// * rare) dropped emission issues on configuration change.
// *
// * @param flow The flow to be observed.
// * @param repeatOnLifecycle The lifecycle state at which to start collecting the flow.
// * @param onEvent The lambda to be invoked when an event is emitted.
// */
//@Composable
//fun <T> ObserveImmediateEvent(
//    flow: Flow<T>,
//    repeatOnLifecycle: Lifecycle.State = Lifecycle.State.STARTED,
//    onEvent: suspend (T) -> Unit,
//) {
//    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
//
//    LaunchedEffect(key1 = flow, key2 = lifecycleOwner.lifecycle) {
//        lifecycleOwner.repeatOnLifecycle(state = repeatOnLifecycle) {
//            withContext(context = Dispatchers.Immediate) {
//                flow.collect { onEvent(it) }
//            }
//        }
//    }
//}
