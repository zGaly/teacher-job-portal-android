package ipca.aulas.gappacademy.utils


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun GrausAcademicosDialog(
    showDialog: MutableState<Boolean>,
    selectedGrau: String,
    onGrauSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            Button(
                onClick = { showDialog.value = false },
                colors = ButtonDefaults.buttonColors(containerColor = GappGreen)
            ) {
                Text("Confirmar", color = Color.White)
            }
        },
        title = { Text("Selecione o Grau Acadêmico") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                GrausAcademicosOptions.options.forEach { grau ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = selectedGrau == grau,
                            onCheckedChange = { isChecked -> if (isChecked) onGrauSelected(grau) },
                            colors = CheckboxDefaults.colors(checkedColor = GappGreen, uncheckedColor = Color.Gray)
                        )
                        Text(text = grau, style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black), modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    )
}
