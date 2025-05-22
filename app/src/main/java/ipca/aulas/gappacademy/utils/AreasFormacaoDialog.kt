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
fun AreasFormacaoDialog(
    showDialog: MutableState<Boolean>,
    selectedAreas: List<String>,
    onAreasSelected: (List<String>) -> Unit
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
        title = { Text("Selecione as Áreas de Formação") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                AreasFormacaoOptions.options.forEach { area ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = selectedAreas.contains(area),
                            onCheckedChange = { isChecked ->
                                val updatedList = if (isChecked) selectedAreas + area else selectedAreas - area
                                onAreasSelected(updatedList)
                            },
                            colors = CheckboxDefaults.colors(checkedColor = GappGreen, uncheckedColor = Color.Gray)
                        )
                        Text(text = area, style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black), modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    )
}
