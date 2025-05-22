package ipca.aulas.gappacademy.ui.cursos.ucs.ucdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.aulas.gappacademy.models.UCs
import ipca.aulas.gappacademy.repository.UCsRepository

data class UcDetailState(
    val uc: UCs? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class UcDetailViewModel : ViewModel() {
    var state = mutableStateOf(UcDetailState())
        private set

    fun loadUcDetails(ucId: String) {
        state.value = state.value.copy(isLoading = true)

        UCsRepository.getUcById(ucId) { uc, error ->
            if (error != null) {
                state.value = state.value.copy(
                    isLoading = false,
                    error = error
                )
            } else {
                state.value = state.value.copy(
                    isLoading = false,
                    uc = uc,
                    error = null
                )
            }
        }
    }
}
