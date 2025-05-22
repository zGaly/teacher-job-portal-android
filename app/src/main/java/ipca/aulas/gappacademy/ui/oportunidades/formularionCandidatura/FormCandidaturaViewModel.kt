package ipca.aulas.gappacademy.ui.oportunidades.formularionCandidatura

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.aulas.gappacademy.repository.CandidaturaRepository

class FormularioCandidaturaViewModel : ViewModel() {
    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var phone = mutableStateOf("")
    var resumeUri = mutableStateOf<Uri?>(null)
    var termsAccepted = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    private val repository = CandidaturaRepository()

    private fun validatePhoneNumber(phone: String): Boolean {
        val phonePattern = "^\\d{9}$"
        return phone.matches(phonePattern.toRegex())
    }

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    suspend fun enviarCandidaturaEspontanea(): Boolean {
        if (name.value.isEmpty() || email.value.isEmpty() || phone.value.isEmpty() || resumeUri.value == null) {
            errorMessage.value = "Preencha todos os campos corretamente."
            return false
        }

        if (!validatePhoneNumber(phone.value)) {
            errorMessage.value = "Número de telefone deve ter 9 dígitos."
            return false
        }
        if (!validateEmail(email.value)) {
            errorMessage.value = "Email inválido."
            return false
        }

        return try {
            val result = repository.enviarCandidaturas(
                nome = name.value,
                email = email.value,
                contacto = phone.value,
                cvUri = resumeUri.value
            )
            if (result) errorMessage.value = null else errorMessage.value = "Erro ao enviar candidatura."
            result
        } catch (e: Exception) {
            errorMessage.value = "Erro inesperado. Tente novamente."
            false
        }
    }
}