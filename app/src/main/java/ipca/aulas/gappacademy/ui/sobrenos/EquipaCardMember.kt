package ipca.aulas.gappacademy.ui.sobrenos

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ipca.aulas.gappacademy.R

@Composable
fun EquipaMembroCircle(nomeFuncao: String, imagemId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Image(
                painter = painterResource(id = imagemId),
                contentDescription = "Foto de $nomeFuncao",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = nomeFuncao.split(" - ")[0],
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
        )

        Text(
            text = nomeFuncao.split(" - ")[1],
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
    }
}

fun getMembroImage(index: Int): Int {
    return when (index) {
        0 -> R.drawable.jose
        1 -> R.drawable.nuno
        2 -> R.drawable.yumi
        else -> R.drawable.gappacademy
    }
}
