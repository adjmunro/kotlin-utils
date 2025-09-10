package nz.adjmunro.util.log

import android.util.Log

/**
 * Converts Kotlogger's [KotloggerPriority] into Android's [Log][android.util.Log] level.
 */
@KotloggerDsl
public inline val KotloggerPriority.asAndroid: Int
    get() = when (this) {
        KotloggerPriority.ASSERT -> Log.ASSERT
        KotloggerPriority.ERROR -> Log.ERROR
        KotloggerPriority.WARN -> Log.WARN
        KotloggerPriority.INFO -> Log.INFO
        KotloggerPriority.DEBUG -> Log.DEBUG
        KotloggerPriority.VERBOSE, KotloggerPriority.NONE -> Log.VERBOSE
    }

/**
 * Converts Android's [Log][Log] level into Kotlogger's [KotloggerPriority].
 *
 * *Invalid levels will return [KotloggerPriority.NONE].*
 */
@KotloggerDsl
public fun KotloggerPriority.Companion.fromAndroidLog(level: Int): KotloggerPriority = when (level) {
    Log.ASSERT -> KotloggerPriority.ASSERT
    Log.ERROR -> KotloggerPriority.ERROR
    Log.WARN -> KotloggerPriority.WARN
    Log.INFO -> KotloggerPriority.INFO
    Log.DEBUG -> KotloggerPriority.DEBUG
    Log.VERBOSE -> KotloggerPriority.VERBOSE
    else -> KotloggerPriority.NONE
}

/**
 * Converts Kotlogger's [KotloggerPriority] into Koin's log [Level][org.koin.core.logger.Level].
 */
@KotloggerDsl
public inline val KotloggerPriority.asKoin: org.koin.core.logger.Level
    get() = when (this) {
        KotloggerPriority.ASSERT, KotloggerPriority.ERROR -> org.koin.core.logger.Level.ERROR
        KotloggerPriority.WARN -> org.koin.core.logger.Level.WARNING
        KotloggerPriority.INFO -> org.koin.core.logger.Level.INFO
        KotloggerPriority.DEBUG, KotloggerPriority.VERBOSE -> org.koin.core.logger.Level.DEBUG
        KotloggerPriority.NONE -> org.koin.core.logger.Level.NONE
    }

/**
 * Converts Koin's log [Level][org.koin.core.logger.Level] into Kotlogger's [KotloggerPriority].
 */
@KotloggerDsl
public inline val org.koin.core.logger.Level.asKotlogger: KotloggerPriority
    get() = when (this) {
        org.koin.core.logger.Level.ERROR -> KotloggerPriority.ERROR
        org.koin.core.logger.Level.WARNING -> KotloggerPriority.WARN
        org.koin.core.logger.Level.INFO -> KotloggerPriority.INFO
        org.koin.core.logger.Level.DEBUG -> KotloggerPriority.DEBUG
        org.koin.core.logger.Level.NONE -> KotloggerPriority.NONE
    }
