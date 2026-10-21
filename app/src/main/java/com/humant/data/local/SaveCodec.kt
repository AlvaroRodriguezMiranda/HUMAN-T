package com.humant.data.local

import com.humant.domain.model.CurrencyState
import com.humant.domain.model.GameState
import java.math.BigInteger

/** v1 stores only the implemented slice. Unknown versions must never be overwritten. */
object SaveCodec {
    fun encode(state: GameState): String = "1\n${state.currency.available}\n${state.currency.earnedThisRun}\n"
    fun decode(text: String): GameState {
        val lines = text.trimEnd().lines()
        require(lines.size == 3 && lines[0] == "1") { "Formato o versión de guardado no compatible" }
        return GameState(CurrencyState(BigInteger(lines[1]), BigInteger(lines[2])))
    }
}
