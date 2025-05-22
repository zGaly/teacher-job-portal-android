package ipca.aulas.gappacademy.ui.perfil.contratos

import androidx.lifecycle.ViewModel
import ipca.aulas.gappacademy.models.ContratoDocenteDetalhado
import ipca.aulas.gappacademy.repository.ContratosDocentesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContratosDocentesViewModel : ViewModel() {

    private val repository = ContratosDocentesRepository()

    private val _contratosDocentes = MutableStateFlow<List<ContratoDocenteDetalhado>>(emptyList())
    val contratosDocentes: StateFlow<List<ContratoDocenteDetalhado>> = _contratosDocentes.asStateFlow()

    init {
        listenToContratosDocentes()
    }

    fun listenToContratosDocentes() {
        repository.listenToContratosDocentes { contratos ->
            if (contratos.isNotEmpty()) {
                _contratosDocentes.value = contratos
            } else {
                println("Nenhum contrato disponível, mantendo lista anterior.")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.removeListener()
    }
}