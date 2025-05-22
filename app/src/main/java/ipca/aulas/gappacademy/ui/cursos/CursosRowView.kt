package ipca.aulas.gappacademy.ui.cursos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.aulas.gappacademy.models.UCs

@Composable
fun CursosRowView(
    curso: UCs,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!curso.nomeCurso.isNullOrEmpty()) {
        Card(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(80.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFA6B9A3)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = curso.nomeCurso,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}