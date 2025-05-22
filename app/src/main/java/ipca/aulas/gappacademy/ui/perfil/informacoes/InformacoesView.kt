package ipca.aulas.gappacademy.ui.perfil.informacoes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ipca.aulas.gappacademy.models.Docentes
import ipca.aulas.gappacademy.ui.perfil.PerfilRowView
import ipca.aulas.gappacademy.ui.theme.GappGreen


@Composable
fun InformacoesView(
    profile: Docentes,
    isEditing: MutableState<Boolean>,

) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PerfilRowView(label = "E-mail", value = profile.email)
        PerfilRowView(label = "Telefone", value = profile.telefone)
        PerfilRowView(label = "Grau Acadêmico", value = profile.grauAcademico)
        PerfilRowView(label = "Localidade", value = profile.localidade)
        PerfilRowView(
            label = "Áreas Disciplinares",
            value = profile.areasDisciplinares?.joinToString(", ") ?: "Nenhuma"
        )
        PerfilRowView(
            label = "Áreas de Formação",
            value = profile.areasFormacao?.joinToString(", ") ?: "Nenhuma"
        )

        Button(
            onClick = { isEditing.value = true },
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
                text = "Editar Perfil",
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}