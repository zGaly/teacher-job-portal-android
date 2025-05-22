package ipca.aulas.gappacademy.ui.oportunidades.formularionCandidatura

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.aulas.gappacademy.ui.theme.GappGreen
import kotlinx.coroutines.launch

@Composable
fun FormularioCandidatura(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    viewModel: FormularioCandidaturaViewModel = viewModel()
) {
    val name by viewModel.name
    val email by viewModel.email
    val phone by viewModel.phone
    val resumeUri by viewModel.resumeUri
    val termsAccepted by viewModel.termsAccepted
    val errorMessage by viewModel.errorMessage

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> viewModel.resumeUri.value = uri }
    )

    fun enviarCandidatura() {
        coroutineScope.launch {
            val result = viewModel.enviarCandidaturaEspontanea()
            if (result) {
                onSubmit()
                Toast.makeText(context, "Candidatura enviada com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Candidatura Espontânea",
                    style = MaterialTheme.typography.headlineSmall,
                    color = GappGreen,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModel.name.value = it },
                    label = { Text("Nome Completo") },
                    placeholder = { Text("ex: Marco Lima") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.email.value = it },
                    label = { Text("Email") },
                    placeholder = { Text("ex: mclima@ipca.pt") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { viewModel.phone.value = it },
                    label = { Text("Telefone") },
                    placeholder = { Text("912345678") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { filePickerLauncher.launch("application/pdf") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GappGreen,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Anexar CV (PDF)")
                    }
                    Text(
                        text = resumeUri?.lastPathSegment ?: "Nenhum arquivo selecionado",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = termsAccepted,
                        onCheckedChange = { viewModel.termsAccepted.value = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Aceito os termos e políticas de privacidade.")
                }

                Button(
                    onClick = { enviarCandidatura() },
                    enabled = termsAccepted,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (termsAccepted) GappGreen else Color.Gray,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Enviar candidatura")
                }
            }
        }
    }
}