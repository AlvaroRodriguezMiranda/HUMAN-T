package com.humant.core.time

fun interface MonotonicClock { fun nowMillis(): Long }

/** A foreground session, deliberately never restored from wall time. */
class ActiveClock(private val clock: MonotonicClock) {
    private var last: Long? = null
    fun resume() { if (last == null) last = clock.nowMillis() }
    fun sample(): Long {
        val previous = last ?: return 0
        val now = clock.nowMillis()
        last = now
        return (now - previous).coerceAtLeast(0)
    }
    fun pause(): Long = sample().also { last = null }
}

data class ActiveTimer(val remainingMillis: Long) {
    init { require(remainingMillis >= 0) }
    fun advance(activeMillis: Long): ActiveTimer {
        require(activeMillis >= 0)
        return copy(remainingMillis = (remainingMillis - activeMillis.coerceAtMost(remainingMillis)))
    }
}
