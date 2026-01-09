package nz.adjmunro.util.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

/**
 * The default implementation of [Dispatchers], used non-test builds.
 */
@InjectDispatchersDsl
public object RuntimeDispatchers: Dispatchers {

    @InjectDispatchersDsl
    public override val default: CoroutineDispatcher
        get() = KotlinDispatchers.Default

    @InjectDispatchersDsl
    public override val immediate: MainCoroutineDispatcher
        get() = main.immediate

    @InjectDispatchersDsl
    public override val io: CoroutineDispatcher
        get() = KotlinDispatchers.IO

    @InjectDispatchersDsl
    public override val main: MainCoroutineDispatcher
        get() = KotlinDispatchers.Main

    @InjectDispatchersDsl
    public override val unconfined: CoroutineDispatcher
        get() = KotlinDispatchers.Unconfined
}
