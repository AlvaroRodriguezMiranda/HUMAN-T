package com.humant.domain.engine

import com.humant.domain.model.GameState
import com.humant.domain.model.CurrencyState
import java.math.BigInteger

/** Pure rules: no Android, storage, UI or clock. */
class EconomyEngine {
    fun manualClick(state: GameState): GameState = credit(state, BigInteger.ONE)

    fun credit(state: GameState, amount: BigInteger): GameState {
        require(amount.signum() >= 0)
        return state.copy(currency = CurrencyState(
            state.currency.available + amount, state.currency.earnedThisRun + amount))
    }

    /** null means insufficient funds. Purchase ownership will join this same transaction. */
    fun spend(state: GameState, price: BigInteger): GameState? {
        require(price.signum() >= 0)
        if (state.currency.available < price) return null
        return state.copy(currency = state.currency.copy(available = state.currency.available - price))
    }
}
