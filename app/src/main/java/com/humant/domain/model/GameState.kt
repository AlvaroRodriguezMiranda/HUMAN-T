package com.humant.domain.model

import java.math.BigInteger

/** Integer PH, unbounded in magnitude; save as decimal strings. */
data class CurrencyState(
    val available: BigInteger = BigInteger.ZERO,
    val earnedThisRun: BigInteger = BigInteger.ZERO,
) {
    init { require(available.signum() >= 0 && earnedThisRun >= available) }
}

data class GameState(val currency: CurrencyState = CurrencyState())
