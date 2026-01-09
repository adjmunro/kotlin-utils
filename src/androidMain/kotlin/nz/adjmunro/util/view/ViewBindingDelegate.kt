@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A delegate for view binding in activities.
 *
 * ```kotlin
 * class MainActivity : AppCompatActivity() {
 *    private val binding by viewBinding(MainActivityBinding::inflate)
 * }
 * ```
 */
public inline fun <T : ViewBinding> Activity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}

/**
 * Creates a [ViewBindingDelegate] for obtaining and releasing the [ViewBinding] of the current [Fragment].
 *
 * ```kotlin
 * class ExampleFragment : Fragment() {
 *    private val binding by viewBinding(ExampleFragmentBinding::bind)
 * }
 * ```
 *
 * @param factory A factory function that creates a [ViewBinding] from the [View] of the [Fragment].
 */
public fun <T : ViewBinding> Fragment.viewBinding(
    factory: (View) -> T,
): ViewBindingDelegate<T> = ViewBindingDelegate(
    fragment = this,
    factory = factory,
)

/** A delegate for obtaining and releasing the [ViewBinding] of the current [Fragment]. */
public class ViewBindingDelegate<T : ViewBinding> internal constructor(
    private val fragment: Fragment,
    private val factory: (View) -> T,
) : ReadOnlyProperty<Fragment, T> {

    private var _binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerObserver = Observer<LifecycleOwner?> { owner ->
                if (owner == null) {
                    _binding = null
                }
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val binding = _binding

        if (binding != null && binding.root === thisRef.view) {
            return binding
        }

        val view = thisRef.view
        checkNotNull(view) {
            "Should not attempt to get bindings when the Fragment's view is null."
        }

        return factory(view).also { _binding = it }
    }
}
