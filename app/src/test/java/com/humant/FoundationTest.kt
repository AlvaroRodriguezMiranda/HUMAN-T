package com.humant

import com.humant.core.time.*
import com.humant.data.local.SaveCodec
import com.humant.domain.engine.EconomyEngine
import com.humant.domain.model.*
import java.math.BigInteger
import org.junit.Assert.*
import org.junit.Test

class FoundationTest {
    private val engine = EconomyEngine()
    @Test fun clickCreditsBothCounters() {
        val state = engine.manualClick(GameState())
        assertEquals(BigInteger.ONE, state.currency.available)
        assertEquals(BigInteger.ONE, state.currency.earnedThisRun)
    }
    @Test fun spendingNeverReducesHistory() {
        val state = engine.credit(GameState(), BigInteger.valueOf(1000))
        val bought = engine.spend(state, BigInteger.valueOf(750))!!
        assertEquals(BigInteger.valueOf(250), bought.currency.available)
        assertEquals(BigInteger.valueOf(1000), bought.currency.earnedThisRun)
    }
    @Test fun insufficientFundsLeaveInputUnchanged() {
        val state = GameState()
        assertNull(engine.spend(state, BigInteger.ONE))
        assertEquals(GameState(), state)
    }
    @Test fun valuesBeyondLongRoundTrip() {
        val state = engine.credit(GameState(), BigInteger.TEN.pow(100))
        assertEquals(state, SaveCodec.decode(SaveCodec.encode(state)))
    }
    @Test fun invalidSaveRejected() {
        for (text in listOf("2\n0\n0", "1\n-1\n0", "1\n5\n2", "broken")) {
            assertThrows(IllegalArgumentException::class.java) { SaveCodec.decode(text) }
        }
    }
    @Test fun activeClockExcludesBackgroundAndResumeIsIdempotent() {
        var now = 0L
        val clock = ActiveClock { now }
        clock.resume(); now = 20; clock.resume()
        assertEquals(20L, clock.pause())
        now = 200000
        assertEquals(0L, clock.sample())
        clock.resume(); now += 40
        assertEquals(40L, clock.sample())
    }
    @Test fun remainingTimeOnlyConsumesExplicitActiveTime() {
        assertEquals(40000L, ActiveTimer(60000).advance(20000).remainingMillis)
        assertEquals(0L, ActiveTimer(10).advance(100).remainingMillis)
    }
    @Test fun bossesMustStartWithManualTrial() {
        assertThrows(IllegalArgumentException::class.java) {
            BossDefinition(ContentId("boss.test"), listOf(PhaseDefinition(ContentId("phase.one"), ContentId("quiz"))))
        }
    }
}
