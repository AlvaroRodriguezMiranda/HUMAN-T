package com.humant.domain

import com.humant.domain.model.GameState
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {
    val state: StateFlow<GameState>
    suspend fun click()
}
