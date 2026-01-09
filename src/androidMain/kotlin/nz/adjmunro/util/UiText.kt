package nz.adjmunro.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import java.util.stream.IntStream

/**
 * An architecture-layer agnostic sealed class that abstracts text data with various wrappers.
 * - A hardcoded string with [DynamicString];
 * - An Android [StringRes] id with [StringResource].
 *
 * *Note: Presentation logic is provided in the presentation layer as `UiText.asString()`
 * extension functions in various `UiTextExt` classes in `:core:ui` and elsewhere.*
 *
 * @see String.asUiText
 * @see Int.asUiText
 */
public sealed interface UiText { // TODO rename DeferredString?

    /**
     * A [UiText] container for a dynamic (hardcoded) string.
     * @param value The literal string value.
     */
    @JvmInline //TODO @Parcelize
    public value class DynamicString(public val value: String) : UiText, /*Parcelable,*/ CharSequence by value {

        @RequiresApi(Build.VERSION_CODES.N)
        override fun chars(): IntStream = value.chars()

        @RequiresApi(Build.VERSION_CODES.N)
        override fun codePoints(): IntStream = value.codePoints()
    }

    /**
     * A [UiText] container for a string resource.
     * @param resId The resource id of the string.
     */
    @JvmInline
    public value class StringResource(@StringRes public val resId: Int) : UiText

    public companion object {

        /** An empty [UiText.DynamicString] resource. */
        public val Empty: DynamicString
            get() = DynamicString(value = "")

        /**
         * An extension function that creates a [UiText] resource from a dynamic string value.
         *
         * @receiver A literal string value.
         * @return A [UiText.DynamicString] instance.
         */
        public val String.asUiText: DynamicString
            get() = DynamicString(value = this)

        /**
         * An extension function that creates a [UiText] resource from a string resource id.
         *
         * @receiver The [StringRes] resource id of the string.
         * @return A [UiText.StringResource] instance.
         */
        public val @receiver:StringRes Int.asUiText: StringResource
            get() = StringResource(resId = this)

        context(context: Context)
        public val UiText.asString: String
            get() = when (this) {
                is DynamicString -> value
                is StringResource -> context.getString(resId)
            }

        context(context: Context)
        public fun UiText.asString(vararg args: Any?): String = when (this) {
            is DynamicString -> value.format(args)
            is StringResource -> context.getString(resId, *args)
        }
        // TODO add compose overloads
    }
}

//private class TestFragment: Fragment() {
//    init {
//        val text = 0.asUiText
//        androidContext { text.asString }
//        requireContext().apply { text.asString }
//    }
//}
