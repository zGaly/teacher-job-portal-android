package ipca.aulas.gappacademy.ui.uc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.aulas.gappacademy.models.UCs
import ipca.aulas.gappacademy.repository.UCsRepository
import kotlinx.coroutines.flow.*

data class UcState(
    val ucs: List<UCs> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class UcViewModel : ViewModel() {

    private val _state = MutableStateFlow(UcState())
    val state: StateFlow<UcState> = _state

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _ucs = MutableStateFlow<List<UCs>>(emptyList())
    val filteredUcs: StateFlow<List<UCs>> = _searchText
        .combine(_ucs) { text, ucs ->
            if (text.isBlank()) {
                ucs
            } else {
                ucs.filter { it.uc?.contains(text, ignoreCase = true) == true }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun loadUcs(siglaCurso: String) {
        _state.value = _state.value.copy(isLoading = true)

        UCsRepository.getUcsBySiglaCurso(siglaCurso) { ucs, error ->
            if (error != null) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = error
                )
            } else {
                _state.value = _state.value.copy(isLoading = false)
                _ucs.value = ucs.filter { it.uc != null }
            }
        }
    }

    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }
}