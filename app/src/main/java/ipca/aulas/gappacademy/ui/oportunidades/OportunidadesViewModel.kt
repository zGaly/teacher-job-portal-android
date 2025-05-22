package ipca.aulas.gappacademy.ui.oportunidades


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import ipca.aulas.gappacademy.models.Anuncio


data class OportunidadesState(
    val anuncios: List<Anuncio> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedAnuncio: Anuncio? = null
)

class OportunidadesViewModel : ViewModel() {
    var state by mutableStateOf(OportunidadesState())
        private set

    private val firestore = FirebaseFirestore.getInstance()

    fun setSelectedAnuncio(anuncio: Anuncio?) {
        state = state.copy(selectedAnuncio = anuncio)
    }

    fun listenToAnuncios() {
        state = state.copy(isLoading = true)

        firestore.collection("Anuncio")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    state = state.copy(error = exception.message, isLoading = false)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val anuncios = snapshot.documents.mapNotNull { document ->
                        document.toObject(Anuncio::class.java)
                    }.filter { it.ativo == true }

                    state = state.copy(anuncios = anuncios, isLoading = false)
                } else {
                    state = state.copy(anuncios = emptyList(), isLoading = false)
                }
            }
    }
}