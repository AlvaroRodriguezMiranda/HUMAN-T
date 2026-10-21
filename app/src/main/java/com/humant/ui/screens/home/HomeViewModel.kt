package com.humant.ui.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.humant.data.local.AtomicGameStore
import com.humant.data.repository.LocalGameRepository
import com.humant.domain.GameRepository
import com.humant.domain.model.GameState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val game: GameState = GameState(), val ready: Boolean = false,
    val saving: Boolean = false, val error: String? = null,
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableState = MutableStateFlow(HomeUiState())
    val state = mutableState.asStateFlow()
    private var repository: GameRepository? = null
    init {
        viewModelScope.launch {
            try {
                val loaded = LocalGameRepository.open(AtomicGameStore(application.filesDir))
                repository = loaded
                loaded.state.collect { game ->
                    mutableState.value = mutableState.value.copy(game = game, ready = true)
                }
            } catch (error: CancellationException) { throw error
            } catch (error: Exception) {
                mutableState.value = HomeUiState(error = "No se pudo cargar la partida. El archivo se conserva.")
            }
        }
    }
    fun click() {
        val repo = repository ?: return
        if (mutableState.value.saving) return
        mutableState.value = mutableState.value.copy(saving = true, error = null)
        viewModelScope.launch {
            try { repo.click()
            } catch (error: CancellationException) { throw error
            } catch (error: Exception) {
                mutableState.value = mutableState.value.copy(error = "No se pudo guardar. Vuelve a intentarlo.")
            } finally { mutableState.value = mutableState.value.copy(saving = false) }
        }
    }
}
