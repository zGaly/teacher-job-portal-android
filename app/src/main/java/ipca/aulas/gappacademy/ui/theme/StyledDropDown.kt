package ipca.aulas.gappacademy.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

@Composable
fun StyledDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (String) -> Unit,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    var menuSize by remember { mutableStateOf(IntSize.Zero) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .wrapContentSize()
            .onGloballyPositioned { coordinates ->
                menuSize = coordinates.size
            }
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f), RoundedCornerShape(8.dp))
    ) {
        StyledDropdownMenuItem("Oportunidades") { onItemClick("oportunidades") }
        HorizontalDivider()
        StyledDropdownMenuItem("Cursos") { onItemClick("cursos") }
        HorizontalDivider()
        StyledDropdownMenuItem("Instalações") { onItemClick("instalacoes") }
        HorizontalDivider()
        StyledDropdownMenuItem("Sobre Nós") { onItemClick("sobrenos") }
        HorizontalDivider()
        StyledDropdownMenuItem(if (isLoggedIn) "Perfil" else "Login") {
            onItemClick(if (isLoggedIn) "perfil" else "login")
        }
    }
}

@Composable
fun StyledDropdownMenuItem(text: String, onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(12.dp)
            )
        },
        onClick = onClick
    )
}