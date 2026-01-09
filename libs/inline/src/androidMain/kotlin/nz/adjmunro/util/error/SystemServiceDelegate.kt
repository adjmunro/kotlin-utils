@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package nz.adjmunro.util.error

import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


/**
 * Create a [SystemServiceDelegate] for the given [serviceClass].
 *
 * @param context The context to use for acquiring the system service.
 * @param serviceClass The kotlin class of the system service to acquire.
 * @param minimumApiLevel The minimum API level required to acquire the system service.
 * @return A [SystemServiceDelegate] for the given [serviceClass] or null if below the [minimumApiLevel].
 */
public inline fun <reified T : Any?> systemService(
    context: Context,
    serviceClass: KClass<T & Any>,
    minimumApiLevel: Int,
): SystemServiceDelegate<T?> = object : SystemServiceDelegate<T?> {
    override val service: T? = when {
        Build.VERSION.SDK_INT < minimumApiLevel -> null
        else -> ContextCompat.getSystemService(
            /* context = */ context,
            /* serviceClass = */ serviceClass.java,
        )
    }
}

/**
 * Create a [SystemServiceDelegate] for the given [serviceClass].
 *
 * @param context The context to use for acquiring the system service.
 * @param serviceClass The kotlin class of the system service to acquire.
 * @return A [SystemServiceDelegate] for the given [serviceClass] which is definitely not null.
 */
@Suppress("UNCHECKED_CAST")
public inline fun <reified T> systemService(
    context: Context,
    serviceClass: KClass<T & Any>,
): SystemServiceDelegate<T & Any> = object : SystemServiceDelegate<T & Any> {
    override val service: T & Any = ContextCompat.getSystemService(
        /* context = */ context,
        /* serviceClass = */ serviceClass.java,
    ) as (T & Any)
}

/** Delegate for accessing system services. */
public interface SystemServiceDelegate<T : Any?> : ReadOnlyProperty<Any?, T> {

    /** The system service instance, or null if unable to acquire. */
    public val service: T

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = service
}
