package ipca.aulas.gappacademy.ui.oportunidades

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.aulas.gappacademy.ui.theme.GappGreen
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import ipca.aulas.gappacademy.ui.oportunidades.formularionCandidatura.FormularioCandidatura
import ipca.aulas.gappacademy.ui.oportunidades.formularionCandidatura.FormularioCandidaturaAnuncio

@Composable
fun OportunidadesView(
    viewModel: OportunidadesViewModel = viewModel()
) {
    val state = viewModel.state
    var showDialog by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.listenToAnuncios()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
            .padding(16.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (state.error != null) {
            Text(
                text = "Erro: ${state.error}",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.anuncios) { anuncio ->
                    Log.d("UI", "Renderizando anuncio: ${anuncio.nomeCurso}")
                    OportunidadesRowView(
                        anuncio = anuncio,
                        onApplyClick = {
                            showDialog = true
                            viewModel.setSelectedAnuncio(anuncio)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                showDialog = true
                viewModel.setSelectedAnuncio(null)
            },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = GappGreen
            ),
            elevation = ButtonDefaults.buttonElevation(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .padding(horizontal = 32.dp)
        ) {
            Text(
                "Candidatura espontânea",
                style = MaterialTheme
                    .typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = GappGreen,
            )
        }

        if (successMessage != null) {
            LaunchedEffect(successMessage) {
                kotlinx.coroutines.delay(1500)
                successMessage = null
            }
            Text(
                text = successMessage!!,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0xFF28A745), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                state.selectedAnuncio?.let { anuncio ->
                    FormularioCandidaturaAnuncio(
                        anuncio = anuncio,
                        onDismiss = { showDialog = false },
                        onSubmit = {
                            showDialog = false
                        }
                    )
                } ?: run {
                    FormularioCandidatura(
                        onDismiss = { showDialog = false },
                        onSubmit = {
                            showDialog = false
                        }
                    )
                }
            }
        }
    }
}