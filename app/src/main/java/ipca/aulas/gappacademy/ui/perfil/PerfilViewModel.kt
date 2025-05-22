package ipca.aulas.gappacademy.ui.perfil

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.aulas.gappacademy.models.Docentes
import ipca.aulas.gappacademy.repository.DocentesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {
    private val repository = DocentesRepository()

    private val _userProfile = MutableStateFlow<Docentes?>(null)
    val userProfile: StateFlow<Docentes?> = _userProfile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadUserProfile(userId: String) {
        _isLoading.value = true
        val docRef = repository.getDocenteReference(userId)

        docRef.get().addOnSuccessListener { snapshot ->
            if (snapshot != null && snapshot.exists()) {
                _userProfile.value = snapshot.toObject(Docentes::class.java)
            } else {
                _userProfile.value = null
            }
            _isLoading.value = false
        }.addOnFailureListener {
            _userProfile.value = null
            _isLoading.value = false
        }
    }

    fun uploadAndDeleteOldImage(userId: String, imageUri: Uri): String? {
        var uploadedPath: String? = null
        viewModelScope.launch {
            try {
                val currentPhotoPath = _userProfile.value?.fotoPerfil

                val newPath = repository.uploadProfileImage(userId, imageUri)

                if (!newPath.isNullOrEmpty()) {
                    repository.updatePartialDocenteProfile(userId, mapOf("fotoPerfil" to newPath))

                    _userProfile.value = _userProfile.value?.copy(fotoPerfil = newPath)
                    uploadedPath = newPath

                    if (!currentPhotoPath.isNullOrEmpty() && currentPhotoPath != newPath) {
                        repository.deleteProfileImage(currentPhotoPath)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return uploadedPath
    }

    fun updateUserProfile(
        userId: String,
        telefone: String?,
        grauAcademico: String?,
        localidade: String?,
        areasFormacao: List<String>?,
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val updatedFields = mapOf(
                    "telefone" to telefone,
                    "grauAcademico" to grauAcademico,
                    "localidade" to localidade,
                    "areasFormacao" to areasFormacao,
                    "fotoPerfil" to _userProfile.value?.fotoPerfil
                ).filterValues { it != null }

                repository.updatePartialDocenteProfile(userId, updatedFields)

                _userProfile.value = _userProfile.value?.copy(
                    telefone = telefone ?: _userProfile.value?.telefone,
                    grauAcademico = grauAcademico ?: _userProfile.value?.grauAcademico,
                    localidade = localidade ?: _userProfile.value?.localidade,
                    areasFormacao = areasFormacao ?: _userProfile.value?.areasFormacao
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}