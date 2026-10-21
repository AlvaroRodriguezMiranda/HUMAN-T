package com.humant.data.repository

import com.humant.data.local.AtomicGameStore
import com.humant.domain.GameRepository
import com.humant.domain.engine.EconomyEngine
import com.humant.domain.model.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class LocalGameRepository private constructor(
    private val store: AtomicGameStore, initial: GameState,
) : GameRepository {
    private val mutableState = MutableStateFlow(initial)
    override val state = mutableState.asStateFlow()
    private val mutex = Mutex()
    private val economy = EconomyEngine()

    override suspend fun click() = withContext(Dispatchers.IO) {
        mutex.withLock {
            val next = economy.manualClick(mutableState.value)
            store.save(next)
            mutableState.value = next
        }
    }
    companion object {
        suspend fun open(store: AtomicGameStore): LocalGameRepository = withContext(Dispatchers.IO) {
            LocalGameRepository(store, store.load())
        }
    }
}
