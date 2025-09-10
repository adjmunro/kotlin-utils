package nz.adjmunro.util

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import java.util.stream.IntStream

public sealed interface ImageResource {

    public val data: Any

    @JvmInline
    public value class RemoteUrl(public val url: String) : ImageResource, CharSequence by url {
        override val data: String get() = url

        @RequiresApi(Build.VERSION_CODES.N)
        override fun chars(): IntStream = url.chars()

        @RequiresApi(Build.VERSION_CODES.N)
        override fun codePoints(): IntStream = url.codePoints()
    }

    @JvmInline
    public value class DrawableId(@DrawableRes public val resId: Int) : ImageResource {
        override val data: Int get() = resId
    }

    public companion object {
        public var baseImageUrl: String? = null

        public fun String.asImageResource(baseUrl: String = requireNotNull(baseImageUrl)): RemoteUrl {
            return RemoteUrl(
                url = when {
                    baseUrl in this@asImageResource -> this@asImageResource
                    startsWith(prefix = "/") -> "${baseUrl}${this@asImageResource}"
                    else -> "${baseUrl}/${this@asImageResource}"
                },
            )
        }

        public fun String.png(baseUrl: String = requireNotNull(baseImageUrl)): RemoteUrl {
            return RemoteUrl(
                url = when {
                    baseUrl in this@png -> "${this@png}.png"
                    startsWith(prefix = "/") -> "${baseUrl}${this@png}.png"
                    else -> "${baseUrl}/${this@png}.png"
                },
            )
        }

        public fun String.svg(baseUrl: String = requireNotNull(baseImageUrl)): RemoteUrl {
            return RemoteUrl(
                url = when {
                    baseUrl in this@svg -> "${this@svg}.svg"
                    startsWith(prefix = "/") -> "${baseUrl}${this@svg}.svg"
                    else -> "${baseUrl}/${this@svg}.svg"
                },
            )
        }

        public fun @receiver:DrawableRes Int.asImageResource(): DrawableId {
            return DrawableId(resId = this@asImageResource)
        }
    }
}
