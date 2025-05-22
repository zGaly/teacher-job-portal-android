package ipca.aulas.gappacademy.ui.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PerfilRowView(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            color = Color.White,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = value ?: "Não disponível",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            color = Color.Gray,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.7f)
        )
    }
}