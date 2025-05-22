package ipca.aulas.gappacademy.ui.instalacoes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun InstalacoesView(
) {
    val viewModel: InstalacoesViewModel = viewModel()
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
            .padding(16.dp)
    ) {
        InstalacoesViewContent(
            state = state,
            modifier = Modifier
                .background(GappGreen)
        )
    }
}

@Composable
fun InstalacoesViewContent(
    state: State<InstalacaoState>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GappGreen),
        contentAlignment = Alignment.Center
    ) {
        println("Rendering state: ${state.value}")

        if (state.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (state.value.error != null) {
            Text(
                text = "Erro: ${state.value.error}",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Red
            )
        } else if (state.value.instalacoes.isEmpty()) {
            Text(
                text = "Não foram encontradas instalações.",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else {
            LazyColumn {
                items(items = state.value.instalacoes) { instalacao ->
                    InstalacaoRowView(
                        instalacao = instalacao,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}