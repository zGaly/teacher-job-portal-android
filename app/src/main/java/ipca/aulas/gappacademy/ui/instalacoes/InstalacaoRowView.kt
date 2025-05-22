package ipca.aulas.gappacademy.ui.instalacoes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ipca.aulas.gappacademy.R
import ipca.aulas.gappacademy.models.Instalacao
import ipca.aulas.gappacademy.utils.FirebaseStorageUtil

@Composable
fun InstalacaoRowView(
    instalacao: Instalacao,
    modifier: Modifier = Modifier
) {
    val storageUtil = FirebaseStorageUtil()
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(instalacao.imgUrl) {
        try {
            if (!instalacao.imgUrl.isNullOrEmpty()) {
                imageUrl = storageUtil.getImageUrl(instalacao.imgUrl!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            imageUrl = null
        } finally {
            isLoading = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.LightGray)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    AsyncImage(
                        model = imageUrl ?: R.drawable.logoipca2,
                        contentDescription = "Imagem da instalação",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = instalacao.name ?: "Nome desconhecido",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Localização: ${instalacao.address ?: "Endereço desconhecido"}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}