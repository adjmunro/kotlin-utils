package nz.adjmunro.util.compose

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private inline val Duration.millis: Int
    get() = inWholeMilliseconds.toInt()

@Composable
fun Shimmer(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    brushWidth: Int = 700,
    angle: Float = 270f,
    duration: Duration = 800.milliseconds,
    minDelay: Duration = 0.milliseconds,
    maxDelay: Duration = minDelay,
    easing: Easing = LinearEasing,
    repeatMode: RepeatMode = RepeatMode.Restart,
) {
    // Infinite animation transition
    val transition = rememberInfiniteTransition(label = "ShimmerTransition")

    // This is our real animation for the shimmer highlight
    val animation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (duration.millis + brushWidth).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration.millis,
                delayMillis = if (minDelay == maxDelay) minDelay.millis
                else (minDelay.millis until maxDelay.millis).random(),
                easing = easing,
            ),
            repeatMode = repeatMode,
        ),
        label = "ShimmerAnimation"
    )

    // Highlight colour of varying opacities
    val highlight = remember(color) {
        listOf(
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 1f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 0.3f),
        )
    }

    // Gradient brush used to draw the shimmer effect
    val brush = Brush.linearGradient(
        colors = highlight,
        start = Offset(x = animation.value - brushWidth, y = 0f),
        end = Offset(x = animation.value, y = angle),
    )

    // Modifiable box shape, with spacer inside that applies the shimmer effect in the foreground.
    Box(modifier = modifier) {
        Spacer(modifier = Modifier.matchParentSize().background(brush))
    }
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    color: Color = Color.White,
    brushWidth: Int = 700,
    angle: Float = 270f,
    duration: Duration = 800.milliseconds,
    minDelay: Duration = 0.milliseconds,
    maxDelay: Duration = minDelay,
    easing: Easing = LinearEasing,
    repeatMode: RepeatMode = RepeatMode.Restart,
    content: @Composable BoxScope.() -> Unit
) {
    // Infinite animation transition
    val transition = rememberInfiniteTransition(label = "ShimmerTransition")

    // This is our real animation for the shimmer highlight
    val animation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (duration.millis + brushWidth).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration.millis,
                delayMillis = if (minDelay == maxDelay) minDelay.millis
                else (minDelay.millis until maxDelay.millis).random(),
                easing = easing,
            ),
            repeatMode = repeatMode,
        ),
        label = "ShimmerAnimation"
    )

    // Highlight colour of varying opacities
    val highlight = remember(color) {
        listOf(
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 1f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 0.3f),
        )
    }

    // Gradient brush used to draw the shimmer effect
    val brush = Brush.linearGradient(
        colors = highlight,
        start = Offset(x = animation.value - brushWidth, y = 0f),
        end = Offset(x = animation.value, y = angle),
    )

    // Modifiable box shape, with spacer inside that applies the shimmer effect in the foreground.
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
    ) {
        content()
        Spacer(modifier = Modifier.matchParentSize().background(brush))
    }
}
