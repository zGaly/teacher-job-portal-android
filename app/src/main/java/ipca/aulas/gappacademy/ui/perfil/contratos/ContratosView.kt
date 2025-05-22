package ipca.aulas.gappacademy.ui.perfil.contratos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ipca.aulas.gappacademy.models.ContratoDocenteDetalhado

@Composable
fun ContratosView(
    contratos: List<ContratoDocenteDetalhado>,
    userId: String
) {
    val contratosFiltrados = contratos.filter { it.userId == userId }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (contratosFiltrados.isEmpty()) {
            Text(
                text = "Nenhum contrato encontrado.",
                color = androidx.compose.ui.graphics.Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            contratosFiltrados.forEach { contrato ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = androidx.compose.ui.graphics
                            .Color(
                                0xFF3C6E58
                            )
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Tipo: ${contrato.tipoContrato}",
                            color = androidx.compose.ui.graphics.Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Horas: ${contrato.horas}",
                            color = androidx.compose.ui.graphics.Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Percentagem: ${contrato.percentagem}",
                            color = androidx.compose.ui.graphics.Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            EvolucaoPagamentoGrafico(contratosFiltrados)
        }
    }
}
