package ipca.aulas.gappacademy.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import ipca.aulas.gappacademy.repository.DocentesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val userId: String? = null,
    val userName: String? = null
)

class LoginViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val docentesRepository: DocentesRepository = DocentesRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state

    fun onEmailChange(newValue: String) {
        _state.value = _state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        _state.value = _state.value.copy(password = newValue)
    }

    private fun validateInputs(): Boolean {
        val email = _state.value.email.trim()
        val password = _state.value.password.trim()

        return when {
            email.isBlank() -> {
                _state.update { it.copy(error = "O e-mail é obrigatório.") }
                false
            }
            !isValidEmail(email) -> {
                _state.update { it.copy(error = "O e-mail inserido é inválido.") }
                false
            }
            password.isBlank() -> {
                _state.update { it.copy(error = "A senha é obrigatória.") }
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun login() {
        if (!validateInputs()) return

        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val authResult = auth.signInWithEmailAndPassword(
                    _state.value.email.trim(),
                    _state.value.password.trim()
                ).await()

                authResult.user?.uid?.let { uid ->
                    fetchUserName(uid)
                    _state.update { it.copy(userId = uid, isLoading = false) }
                } ?: _state.update { it.copy(isLoading = false, error = "ID de usuário não encontrado.") }

            } catch (e: Exception) {
                val errorMessage = when {
                    "INVALID_LOGIN_CREDENTIALS" in e.message.orEmpty() -> "E-mail ou senha incorretos."
                    "network error" in e.message.orEmpty() -> "Falha na conexão. Verifique sua internet."
                    "badly formatted" in e.message.orEmpty() -> "Formato de e-mail inválido."
                    else -> "Erro ao fazer login. Tente novamente."
                }
                _state.update { it.copy(isLoading = false, error = errorMessage) }
            }
        }
    }

    private fun fetchUserName(userId: String) {
        viewModelScope.launch {
            try {
                val name = docentesRepository.getUserName(userId)
                _state.value = _state.value.copy(userName = name ?: "Usuário")
            } catch (e: Exception) {
                _state.value = _state.value.copy(userName = "Usuário")
            }
        }
    }
}