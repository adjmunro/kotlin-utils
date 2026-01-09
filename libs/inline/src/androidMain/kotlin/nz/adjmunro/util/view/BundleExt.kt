package nz.adjmunro.util.view

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat

/** Kotlin Extensions for [Bundle]. */
public object BundleExt {

    /** Get a [Parcelable] from the [Bundle] or returns null. */
    public inline fun <reified T : Parcelable> Bundle?.parcelableOrNull(key: String): T? = this?.let {
        BundleCompat.getParcelable(
            /* in = */ it,
            /* key = */ key,
            /* clazz = */ T::class.java,
        )
    }

    /** Get a [Parcelable] from the [Bundle] or return a [default] value. */
    public inline fun <reified T : Parcelable> Bundle?.parcelableOrDefault(key: String, default: T): T =
        parcelableOrNull(key = key) ?: default
}
