package ipca.aulas.gappacademy.ui.cursos.ucs.ucdetail

import UcDetailRowView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun UcDetailView(
    ucId: String,
    viewModel: UcDetailViewModel = viewModel(),
) {
    val state = viewModel.state.value

    LaunchedEffect(ucId) {
        viewModel.loadUcDetails(ucId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    Text(
                        text = "Carregando...",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                state.error != null -> {
                    Text(
                        text = "Erro: ${state.error}",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                state.uc != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = state.uc.uc ?: "Detalhes da UC",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(48.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            contentPadding = PaddingValues(top = 32.dp)
                        ) {
                            item {
                                val formattedAno = state.uc.ano?.let {
                                    it.replace("1a", "1º ano")
                                        .replace("2a", "2º ano")
                                } ?: "N/A"
                                UcDetailRowView(label = "Ano", value = formattedAno)
                            }
                            item {
                                UcDetailRowView(label = "Código da UC", value = state.uc.codUC)
                            }
                            item {
                                UcDetailRowView(label = "Horas semanais 1º Semestre", value = state.uc.horas1s?.toString())
                            }
                            item {
                                UcDetailRowView(label = "Horas semanais 2º Semestre", value = state.uc.horas2s?.toString())
                            }
                            item {
                                UcDetailRowView(label = "Horas Totais", value = state.uc.horasTotais?.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}
