package ipca.aulas.gappacademy.ui.cursos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.aulas.gappacademy.models.UCs
import ipca.aulas.gappacademy.repository.CursosRepository
import kotlinx.coroutines.flow.*

data class CursoState(
    val cursos: List<UCs> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class CursosViewModel : ViewModel() {

    private val _state = MutableStateFlow(CursoState())
    val state: StateFlow<CursoState> = _state

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _cursos = MutableStateFlow<List<UCs>>(emptyList())
    val filteredCursos: StateFlow<List<UCs>> = _searchText
        .combine(_cursos) { text, cursos ->
            if (text.isBlank()) {
                cursos
            } else {
                cursos.filter { it.nomeCurso?.contains(text, ignoreCase = true) == true }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getAll() {
        _state.value = _state.value.copy(isLoading = true)

        CursosRepository.getAll { cursos, error ->
            if (error != null) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = error
                )
            } else {
                _state.value = _state.value.copy(isLoading = false)
                _cursos.value = cursos.filter { it.nomeCurso != null }
            }
        }
    }

    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }
}