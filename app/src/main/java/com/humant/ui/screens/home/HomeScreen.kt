package com.humant.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(state: HomeUiState, onClick: () -> Unit) {
    Scaffold { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).safeDrawingPadding()
                .verticalScroll(rememberScrollState()).padding(24.dp).widthIn(max = 600.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text("HUMAN'T", style = MaterialTheme.typography.headlineLarge)
            Text("Etapa 1 · Bicho espacial")
            Text("Saldo: ${state.game.currency.available} PH", style = MaterialTheme.typography.titleLarge)
            Text("Históricos de la partida: ${state.game.currency.earnedThisRun} PH")
            Text("1 PH/clic · 0 PH/s")
            Surface(tonalElevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("PHILL", style = MaterialTheme.typography.headlineMedium)
                    Text("Acabo de aterrizar. Ya necesito vacaciones.")
                    Text("Sprite pendiente del concepto aprobado", style = MaterialTheme.typography.labelSmall)
                }
            }
            Button(onClick = onClick, enabled = state.ready && !state.saving,
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
                Text(if (!state.ready) "Cargando…" else "Ensayar ser humano · +1 PH")
            }
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Text("Prototipo inicial · Tienda, bosses y prestigio en preparación")
        }
    }
}
