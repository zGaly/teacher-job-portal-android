package ipca.aulas.gappacademy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import ipca.aulas.gappacademy.repository.DocentesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val docentesRepository: DocentesRepository = DocentesRepository(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            login(currentUser.uid)
        }
    }

    fun login(userId: String) {
        viewModelScope.launch {
            println("AuthViewModel: login iniciado para UID: $userId")
            _userId.value = userId
            _userName.value = docentesRepository.getUserName(userId)
            _isLoggedIn.value = true
            println("AuthViewModel: login completo - userId: ${_userId.value}, userName: ${_userName.value}") // DEBUG
        }
    }

    fun logout() {
        viewModelScope.launch {
            auth.signOut()
            _userId.value = null
            _userName.value = null
            _isLoggedIn.value = false
        }
    }
}