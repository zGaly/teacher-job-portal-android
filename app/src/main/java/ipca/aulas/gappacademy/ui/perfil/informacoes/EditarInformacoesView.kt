package ipca.aulas.gappacademy.ui.perfil.informacoes

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ipca.aulas.gappacademy.components.PerfilCustomButton
import ipca.aulas.gappacademy.components.PerfilTextField
import ipca.aulas.gappacademy.models.Docentes
import ipca.aulas.gappacademy.ui.perfil.PerfilViewModel
import ipca.aulas.gappacademy.ui.theme.GappGreen
import ipca.aulas.gappacademy.utils.AreasFormacaoDialog
import ipca.aulas.gappacademy.utils.GrausAcademicosDialog

@Composable
fun EditarInformacoesView(
    profile: Docentes,
    isEditing: MutableState<Boolean>,
    showGrausDialog: MutableState<Boolean>,
    showAreasDialog: MutableState<Boolean>,
    perfilViewModel: PerfilViewModel,
    userId: String,
    context: Context
) {
    var telefone by remember { mutableStateOf(profile.telefone ?: "") }
    var grauAcademico by remember { mutableStateOf(profile.grauAcademico ?: "") }
    var localidade by remember { mutableStateOf(profile.localidade ?: "") }
    var areasFormacao by remember { mutableStateOf(profile.areasFormacao ?: emptyList()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PerfilTextField(
            value = telefone,
            onValueChange = { telefone = it },
            placeholder = "Telefone"
        )

        PerfilTextField(
            value = localidade,
            onValueChange = { localidade = it },
            placeholder = "Localidade"
        )

        PerfilCustomButton(
            text = "Grau Acadêmico",
            onClick = { showGrausDialog.value = true }
        )

        PerfilCustomButton(
            text = "Áreas de Formação",
            onClick = { showAreasDialog.value = true }
        )

        if (showGrausDialog.value) {
            GrausAcademicosDialog(showGrausDialog, grauAcademico) { grauAcademico = it }
        }

        if (showAreasDialog.value) {
            AreasFormacaoDialog(showAreasDialog, areasFormacao) { areasFormacao = it }
        }

        Button(
            onClick = {
                perfilViewModel.updateUserProfile(
                    userId = userId,
                    telefone = telefone,
                    grauAcademico = grauAcademico,
                    localidade = localidade,
                    areasFormacao = areasFormacao
                )

                isEditing.value = false
                Toast.makeText(context, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
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
                .padding(horizontal = 82.dp)
        ) {
            Text(
                text = "Guardar Alterações",
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
