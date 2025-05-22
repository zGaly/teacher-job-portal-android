package ipca.aulas.gappacademy.ui.oportunidades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.aulas.gappacademy.models.Anuncio
import ipca.aulas.gappacademy.repository.CandidaturaRepository

@Composable
fun OportunidadesRowView(
    anuncio: Anuncio,
    modifier: Modifier = Modifier,
    onApplyClick: (Anuncio) -> Unit
) {
    val localName = remember { mutableStateOf("Carregando...") }

    val repository = CandidaturaRepository()

    LaunchedEffect(anuncio.local) {
        localName.value = (anuncio.local?.let { id ->
            repository.getInstalacaoNameById(id)
        } ?: "Local não encontrado").toString()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = localName.value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "UC: ${anuncio.uc ?: "Não Especificado"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Text(
                    "Curso: ${anuncio.nomeCurso ?: "Curso Não Disponível"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Text(
                    text = "Regime: ${anuncio.regime ?: "N/A"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Text(
                    text = "Início: ${anuncio.dataInicio ?: "Data Não Definida"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            if (anuncio.vaga == true) {
                                onApplyClick(anuncio)
                            }
                        },
                        enabled = anuncio.vaga == true,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0F4F33),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Candidatar ao Anúncio",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}