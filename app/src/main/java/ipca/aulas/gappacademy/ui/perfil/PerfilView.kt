package ipca.aulas.gappacademy.ui.perfil

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ipca.aulas.gappacademy.R
import ipca.aulas.gappacademy.ui.perfil.contratos.ContratosDocentesViewModel
import ipca.aulas.gappacademy.ui.perfil.contratos.ContratosView
import ipca.aulas.gappacademy.ui.perfil.informacoes.EditarInformacoesView
import ipca.aulas.gappacademy.ui.perfil.informacoes.InformacoesView
import ipca.aulas.gappacademy.ui.theme.GappGreen
import ipca.aulas.gappacademy.utils.FirebaseStorageUtil

@Composable
fun PerfilView(
    userId: String,
    userName: String,
    perfilViewModel: PerfilViewModel = viewModel(),
    contratosViewModel: ContratosDocentesViewModel = viewModel(),
) {
    val userProfile by perfilViewModel.userProfile.collectAsState()
    val isLoading by perfilViewModel.isLoading.collectAsState()
    val contratosDocentes by contratosViewModel.contratosDocentes.collectAsState()
    val isEditing = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val showAreasDialog = remember { mutableStateOf(false) }
    val showGrausDialog = remember { mutableStateOf(false) }
    var previewImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isImageLoading by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (isEditing.value) {
            previewImageUri = uri
            uri?.let { perfilViewModel.uploadAndDeleteOldImage(userId, it) }
        }
    }

    var selectedTab by remember { mutableStateOf("informacoes") }

    LaunchedEffect(Unit) {
        perfilViewModel.loadUserProfile(userId)
        contratosViewModel.listenToContratosDocentes()
    }

    LaunchedEffect(userProfile?.fotoPerfil) {
        val storageUtil = FirebaseStorageUtil()
        try {
            userProfile?.fotoPerfil?.let {
                imageUrl = storageUtil.getImageUrl(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            imageUrl = null
        } finally {
            isImageLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(GappGreen)
            .padding(16.dp),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            userProfile?.let { profile ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(Color.White, CircleShape)
                    ) {
                        if (isImageLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(40.dp)
                            )
                        } else if (previewImageUri != null) {
                            AsyncImage(
                                model = previewImageUri,
                                contentDescription = "Pré-visualização da Foto de Perfil",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else if (!imageUrl.isNullOrEmpty()) {
                            Crossfade(targetState = imageUrl, label = "Foto Perfil") { img ->
                                AsyncImage(
                                    model = img ?: R.drawable.userplaceholder,
                                    contentDescription = "Foto de Perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        } else {
                            AsyncImage(
                                model = R.drawable.userplaceholder,
                                contentDescription = "Foto de Perfil Placeholder",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        if (isEditing.value) {

                            IconButton(
                                onClick = { launcher.launch("image/*") },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .background(Color.White, shape = CircleShape)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Editar Foto",
                                    tint = GappGreen,
                                    modifier = Modifier.size(34.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = profile.nome ?: userName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, MaterialTheme.shapes.medium)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        listOf("Informações" to "informacoes", "Contratos" to "contratos").forEach { (label, tab) ->
                            Button(
                                onClick = { selectedTab = tab },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedTab == tab) GappGreen else Color.Transparent
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(36.dp),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Text(label, color = if (selectedTab == tab) Color.White else GappGreen)
                            }
                        }
                    }

                    when (selectedTab) {
                        "informacoes" -> {
                            if (!isEditing.value) {
                                InformacoesView(
                                    profile = profile,
                                    isEditing = isEditing
                                )
                            } else {
                                EditarInformacoesView(
                                    profile = profile,
                                    isEditing = isEditing,
                                    showGrausDialog = showGrausDialog,
                                    showAreasDialog = showAreasDialog,
                                    perfilViewModel = perfilViewModel,
                                    userId = userId,
                                    context = context,
                                )
                            }
                        }

                        "contratos" -> {
                            val contratosFiltrados =
                                contratosDocentes.filter { it.userId == userId }
                            ContratosView(contratosFiltrados, userId)
                        }
                    }
                }
            } ?: run {
                Text("Erro ao carregar dados do perfil", color = Color.Red)
            }
        }
    }
}