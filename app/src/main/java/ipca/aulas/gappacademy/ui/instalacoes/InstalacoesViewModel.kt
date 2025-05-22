package ipca.aulas.gappacademy.ui.instalacoes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import ipca.aulas.gappacademy.models.Instalacao


data class InstalacaoState(
    val instalacoes: List<Instalacao> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class InstalacoesViewModel : ViewModel() {

    private val _state = mutableStateOf(InstalacaoState())
    val state: State<InstalacaoState> get() = _state

    private var listener: ListenerRegistration? = null

    init {
        observeInstalacoes()
    }

    private fun observeInstalacoes() {
        _state.value = _state.value.copy(isLoading = true)

        listener = FirebaseFirestore.getInstance()
            .collection("Instalacoes")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                    return@addSnapshotListener
                }

                val instalacoes = snapshots?.documents?.mapNotNull { doc ->
                    doc.toObject(Instalacao::class.java)?.apply {
                        docId = doc.id
                    }
                } ?: emptyList()

                _state.value = _state.value.copy(
                    isLoading = false,
                    instalacoes = instalacoes,
                    error = null
                )
            }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}