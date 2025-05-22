package ipca.aulas.gappacademy.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ipca.aulas.gappacademy.R
import kotlinx.coroutines.launch

@Composable
fun FirebaseImage(
    modifier: Modifier = Modifier,
    path: String? = null,
    contentDescription: String? = null,
    uploadImage: suspend (Uri) -> String?,
    onImageUploaded: ((String) -> Unit)? = null
) {
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val errorPlaceholder = painterResource(id = R.drawable.logoipca2)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                isLoading = true
                val uploadedUrl = uploadImage(it)
                if (uploadedUrl != null) {
                    imageUrl = uploadedUrl
                    onImageUploaded?.invoke(uploadedUrl) // Retorna o URL carregado
                }
                isLoading = false
            }
        }
    }

    LaunchedEffect(path) {
        if (!path.isNullOrEmpty()) {
            imageUrl = path
            isLoading = false
        }
    }

    Box(
        modifier = modifier.clickable { imagePickerLauncher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                error = errorPlaceholder
            )
        }
    }
}