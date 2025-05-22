package ipca.aulas.gappacademy.ui.sobrenos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.State
import ipca.aulas.gappacademy.R

data class SobreNosState(
    val titulo: String = "Sobre Nós",
    val descricao: String = "A GappAcademy é um projeto desenvolvido pela empresa Gapp, composto por uma jovem equipa de programadores apaixonados pela inovação e tecnologia. " +
            "Fundada por José Galinha e Nuno Domingues, a empresa dedica-se ao desenvolvimento de soluções digitais. " +
            "A nossa COO, Yumi Maeno, assegura a excelência operacional em todos os projetos.",
    val equipa: List<String> = listOf(
        "José Galinha - CoFounder",
        "Nuno Domingues - CoFounder",
        "Yumi Maeno - COO"
    ),
    val botaoTexto: String = "Contacte-nos",
    val backgroundColor: Color = Color(0xFF2E423B),
    val logoGapp: Int = R.drawable.gapp,
    val logoAcademy: Int = R.drawable.gappacademy,
    val paddingTopLogo: Int = 32,
    val paddingBottomLogo: Int = 16,
    val logoGappSize: Int = 150,
    val logoAcademySize: Int = 100,
    val spacingMedium: Int = 16,
    val spacingLarge: Int = 24,
)

class SobreNosViewModel : ViewModel() {
    private val _state = mutableStateOf(SobreNosState())
    val state: State<SobreNosState> = _state
}