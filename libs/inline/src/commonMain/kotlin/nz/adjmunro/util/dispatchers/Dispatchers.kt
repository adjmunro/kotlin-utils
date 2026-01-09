package nz.adjmunro.util.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/** A typealias for [kotlinx.coroutines.Dispatchers] to avoid name clashes. */
@InjectDispatchersDsl
public typealias KotlinDispatchers = kotlinx.coroutines.Dispatchers

/** A typealias for [nz.adjmunro.util.dispatchers.Dispatchers] to avoid name clashes. */
@InjectDispatchersDsl
public typealias InjectDispatchers = Dispatchers

/**
 * An injectable facade for providing various [kotlinx.coroutines.CoroutineDispatcher] instances in aggregate.
 *
 * *See the junit4 `DispatchersRule` or junit5 `DispatchersExtension` for testing.*
 *
 * [Coroutine Best Practices](https://developer.android.com/kotlin/coroutines/coroutines-best-practices)
 *
 * @property Dispatchers.default
 * @property Dispatchers.immediate
 * @property Dispatchers.io
 * @property Dispatchers.main
 * @property Dispatchers.unconfined
 *
 * @see KotlinDispatchers
 * @see Dispatchers.singleton
 */
@InjectDispatchersDsl
public interface Dispatchers {

    /**
     * The default dispatcher for CPU-intensive work.
     *
     * Use this for tasks that require a lot of computation and can be parallelized.
     *
     * > You generally **do not** need to use this dispatcher, because it is the default for
     * > launching coroutines ***except from `viewModelScope`***.
     *
     * > You usually only need to use this when a `suspend` function you control, calls a
     * > non-suspend function that performs expensive tasks. *Remember to use
     * > [ensureActive][kotlinx.coroutines.ensureActive] before doing any blocking function call.*
     *
     * > ***Having said that,*** I find it generally advisable to use this for most
     * > `viewModelScope` coroutines to avoid doing work on the main thread, especially when
     * > collecting flows or calling `suspend` functions from the data layer (which inherits
     * > the scope). *Light & purely UI-related tasks should still use [main] or [immediate].*
     *
     * *This dispatcher is optimized for CPU-intensive tasks and uses a
     * shared pool of threads that are used to execute tasks in parallel.*
     *
     *
     * @see kotlinx.coroutines.Dispatchers.Default
     */
    @InjectDispatchersDsl
    public val default: CoroutineDispatcher

    /**
     * The immediate dispatcher for tasks that should run immediately on the main thread.
     *
     * Use this for tasks that need to be executed immediately without waiting for the next frame.
     *
     * @see kotlinx.coroutines.MainCoroutineDispatcher.immediate
     */
    @InjectDispatchersDsl
    public val immediate: CoroutineDispatcher

    /**
     * The IO dispatcher for offloading blocking IO tasks.
     *
     * Use this for tasks that involve network or disk IO operations.
     *
     * > You generally **do not** need to use this dispatcher, as most libraries for IO operations
     * > will have already used it internally to make their blocking calls "main-safe".
     *
     * > You **only** need to use this when a `suspend` function you control, calls a
     * > non-suspend function that performs IO work. *Remember to use
     * > [ensureActive][kotlinx.coroutines.ensureActive] before doing any blocking function call.*
     *
     * *Unlike [default], this dispatcher is optimized to share a large
     * pool of dormant polling threads waiting for a response.*
     *
     * @see kotlinx.coroutines.Dispatchers.IO
     */
    @InjectDispatchersDsl
    public val io: CoroutineDispatcher

    /**
     * The main dispatcher for UI-related tasks.
     *
     * Use this for tasks that need to interact with the UI or run on the main thread (e.g. old Java threaded tasks).
     *
     * > You generally **do not** need to use this dispatcher, as expensive tasks
     * > should be offloaded to [default] or [io]. This is the default dispatcher used by `viewModelScope`.
     *
     * @see kotlinx.coroutines.Dispatchers.Main
     * @see kotlinx.coroutines.MainCoroutineDispatcher
     */
    @InjectDispatchersDsl
    public val main: CoroutineDispatcher

    /**
     * The unconfined dispatcher for tasks that can run on any thread.
     *
     * Use this for tasks that do not require a specific thread context.
     *
     * *This dispatcher is not recommended for most tasks, as it can risk unpredictable behavior.*
     *
     * @see kotlinx.coroutines.Dispatchers.Unconfined
     */
    @InjectDispatchersDsl
    public val unconfined: CoroutineDispatcher

    /** Static members for accessing the current [singleton] instance of [Dispatchers]. */
    @InjectDispatchersDsl
    public companion object {
        /**
         * The singleton instance of [Dispatchers].
         *
         * *Dependency injection should point to this instance, as it
         * needs to be malleable for test injections.*
         *
         * > Use the junit4 `DispatchersRule` or junit5 `DispatchersExtension` to
         * > assign a `TestDispatcher` version of this provider when testing.
         */
        @InjectDispatchersDsl
        public var singleton: Dispatchers = RuntimeDispatchers

        /** Static access to the [default] dispatcher of the current [singleton]. */
        @InjectDispatchersDsl
        public val Default: CoroutineDispatcher get() = singleton.default

        /** Static access to the [immediate] dispatcher of the current [singleton]. */
        @InjectDispatchersDsl
        public val Immediate: CoroutineDispatcher get() = singleton.immediate

        /** Static access to the [io] dispatcher of the current [singleton]. */
        @InjectDispatchersDsl
        public val IO: CoroutineDispatcher get() = singleton.io

        /** Static access to the [main] dispatcher of the current [singleton]. */
        @InjectDispatchersDsl
        public val Main: CoroutineDispatcher get() = singleton.main

        /** Static access to the [unconfined] dispatcher of the current [singleton]. */
        @InjectDispatchersDsl
        public val Unconfined: CoroutineDispatcher get() = singleton.unconfined

        /**
         * A [CoroutineScope] that is not tied to any lifecycle and will only
         * be cancelled when the application is destroyed.
         *
         * *The most likely use case for this is for a `Repository` to briefly outlive its `ViewModel`
         * in order to finish caching data or informing the remote data source of a change.*
         *
         * [Operations that shouldn't be cancelled](https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad)
         *
         * ## Do NOT use this if:
         * 1. Your operation can be scoped to either `viewModelScope` or `lifecycleScope`
         * *(which is almost always the case!)*, to ensure it is cancelled with its lifecycle owner.
         * 2. Your operation should be locally cancellable. In this case, prefer a local property
         * assigned to [localScope].
         * 3. Your operation *must* outlive the application. Use `WorkManager` for that.
         */
        @InjectDispatchersDsl
        public val foreverScope: CoroutineScope by lazy {
            CoroutineScope(context = SupervisorJob() + Default)
        }
    }

}
