@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.concurrent

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import nz.adjmunro.util.concurrent.async.AsyncDelegate
import nz.adjmunro.util.concurrent.async.AsyncDelegate.Companion.await
import nz.adjmunro.util.concurrent.async.AsyncDelegate.Companion.awaitFirst
import nz.adjmunro.util.concurrent.async.AsyncDelegate.Companion.awaitOrDefault
import nz.adjmunro.util.concurrent.async.AsyncDelegate.Companion.awaitOrThrow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.Flow
import nz.adjmunro.util.dispatchers.Dispatchers
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/** Extension functions for [AsyncDelegate]. */
public object AsyncDelegateExt {

    /** [ViewModel] extension functions for [AsyncDelegate]. */
    public object ViewModelExt {

        /** @see AsyncDelegate.await */
        public fun <T> ViewModel.await(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> T,
        ): AsyncDelegate<T> = viewModelScope.await(
            start = start,
            asyncOn = asyncOn,
            awaitOn = awaitOn,
            block = block,
        )

        /** @see AsyncDelegate.awaitOrThrow */
        public fun <T> ViewModel.awaitOrThrow(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Result<T>,
        ): AsyncDelegate<T> = viewModelScope.awaitOrThrow(
            start = start,
            asyncOn = asyncOn,
            awaitOn = awaitOn,
            block = block,
        )

        /** @see AsyncDelegate.awaitOrDefault */
        public fun <T> ViewModel.awaitOrDefault(
            default: T,
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Result<T>,
        ): AsyncDelegate<T> = viewModelScope.awaitOrDefault(
            default = default,
            start = start,
            asyncOn = asyncOn,
            awaitOn = awaitOn,
            block = block,
        )

        /** @see AsyncDelegate.awaitFirst */
        public fun <T> ViewModel.awaitFirst(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Flow<T>,
        ): AsyncDelegate<Result<T>> = viewModelScope.awaitFirst(
            start = start,
            asyncOn = asyncOn,
            awaitOn = awaitOn,
            block = block,
        )
    }

    /** [LifecycleOwner] extension functions for [AsyncDelegate]. */
    public object LifecycleOwnerExt {

        /** @see AsyncDelegate.await */
        public fun <T> LifecycleOwner.await(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> T,
        ): AsyncDelegate<T> = lifecycleScope.await(
            start = start,
            asyncOn = asyncOn,
            awaitOn = awaitOn,
            block = block,
        )

        /** @see AsyncDelegate.awaitOrThrow */
        public fun <T> LifecycleOwner.awaitOrThrow(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Result<T>,
        ): AsyncDelegate<T> = lifecycleScope.awaitOrThrow(
            start = start,
            asyncOn = asyncOn,
            awaitOn = awaitOn,
            block = block,
        )

        /** @see AsyncDelegate.awaitOrDefault */
        public fun <T> LifecycleOwner.awaitOrDefault(
            default: T,
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Result<T>,
        ): AsyncDelegate<T> = lifecycleScope.awaitOrDefault(
            default = default,
            start = start,
            asyncOn = asyncOn,
            awaitOn = awaitOn,
            block = block,
        )

        /** @see AsyncDelegate.awaitFirst */
        public fun <T> LifecycleOwner.awaitFirst(
            asyncOn: CoroutineContext = EmptyCoroutineContext,
            awaitOn: CoroutineContext = Dispatchers.Default,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Flow<T>,
        ): AsyncDelegate<Result<T>> = lifecycleScope.awaitFirst(
            start = start,
            asyncOn = asyncOn,
            awaitOn = awaitOn,
            block = block,
        )
    }
}
