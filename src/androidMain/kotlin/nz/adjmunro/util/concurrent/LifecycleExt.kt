@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.concurrent

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import nz.adjmunro.util.concurrent.LifecycleExt.collectIn
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Extension functions for Android [Lifecycle] & [LifecycleOwner].
 */
public object LifecycleExt {
    /**
     * Syntax-sugar to [launch] and [Flow.collect] within a [LifecycleOwner.repeatOnLifecycle] block.
     *
     * @param T The type of the [Flow].
     * @param repeatOnLifecycle The [Lifecycle.State] to observe.
     * @param observer The observer to collect the [Flow].
     *
     * @see LifecycleOwner.lifecycleScope
     * @see LifecycleOwner.repeatOnLifecycle
     * @see Flow.collect
     */
    context(lifecycleOwner: LifecycleOwner)
    public fun <T> Flow<T>.collectIn(
        context: CoroutineContext = EmptyCoroutineContext,
        scope: CoroutineScope = lifecycleOwner.lifecycleScope,
        repeatOnLifecycle: Lifecycle.State = Lifecycle.State.STARTED,
        observer: FlowCollector<T>,
    ) {
        lifecycleOwner.lifecycleScope.launch(context) {
            lifecycleOwner.repeatOnLifecycle(state = repeatOnLifecycle) {
                collect(collector = observer)
            }
        }
    }

    /**
     * Syntax-sugar to [launch] and [Flow.collectLatest] within a [LifecycleOwner.repeatOnLifecycle] block.
     *
     * @param T The type of the [Flow].
     * @param repeatOnLifecycle The [Lifecycle.State] to observe.
     * @param observer The observer to collect the [Flow].
     *
     * @see LifecycleOwner.lifecycleScope
     * @see LifecycleOwner.repeatOnLifecycle
     * @see Flow.collectLatest
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    context(lifecycleOwner: LifecycleOwner)
    public fun <T> Flow<T>.repeatOnLifecycle(
        scope: CoroutineScope = lifecycleOwner.lifecycleScope,
        context: CoroutineContext = EmptyCoroutineContext,
        repeatOnLifecycle: Lifecycle.State = Lifecycle.State.STARTED,
        observer: suspend (T) -> Unit,
    ) {
        lifecycleOwner.lifecycleScope.launch(context) {
            lifecycleOwner.repeatOnLifecycle(state = repeatOnLifecycle){
                collectLatest(action = observer)
            }
        }
    }
}

private class F: Fragment() {
    val x = flowOf(1).collectIn(lifecycleScope) {
        println(it)
    }
}
