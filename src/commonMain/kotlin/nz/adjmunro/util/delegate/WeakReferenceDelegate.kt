@file:Suppress("Unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.delegate

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Create a new instance of [WeakReferenceDelegate].
 *
 * @param T The type of the weak reference.
 * @param initialValue The initial value of the weak reference.
 * @param onChange The callback to be called when the value of the weak reference changes.
 * @return A new instance of [WeakReferenceDelegate] for [T]?
 */
public fun <T : Any> weakReference(
    initialValue: T? = null,
    onChange: (oldValue: T?, newValue: T?) -> Unit = { _, _ -> },
): WeakReferenceDelegate<T> = WeakReferenceDelegate(
    initialValue = initialValue,
    onChange = onChange,
)

/** A kotlin delegate for [WeakReference]. */
public class WeakReferenceDelegate<T : Any> internal constructor(
    initialValue: T?,
    private val onChange: (oldValue: T?, newValue: T?) -> Unit,
) : ReadWriteProperty<Any?, T?> {

    private var weakReference: WeakReference<T?> = WeakReference(initialValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? = weakReference.get()

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        onChange(weakReference.get(), value)
        weakReference.clear()
        weakReference = WeakReference(value)
    }
}
