package ipca.aulas.gappacademy.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import ipca.aulas.gappacademy.models.ContratoDocenteDetalhado

class ContratosDocentesRepository {

    private val db = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null

    fun listenToContratosDocentes(onUpdate: (List<ContratoDocenteDetalhado>) -> Unit) {
        listener?.remove()

        listener = db.collection("ContratosDocentes")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    println("Erro ao escutar contratos: ${error.message}")
                    return@addSnapshotListener
                }

                if (snapshot == null || snapshot.isEmpty) {
                    println("Nenhum contrato encontrado.")
                    onUpdate(emptyList())
                    return@addSnapshotListener
                }

                val contratosDetalhados = mutableListOf<ContratoDocenteDetalhado>()

                snapshot.documents.forEach { document ->
                    val userId = document.getString("docente") ?: return@forEach
                    val tipoContratoId = document.getString("tipoContrato") ?: return@forEach
                    val detalhesContrato = document.getString("detalhesContrato") ?: return@forEach
                    val anoLetivo = document.getString("anoLetivo") ?: return@forEach

                    println("Buscando contrato para docente: $userId")

                    db.collection("Contratos").document(tipoContratoId).get()
                        .addOnSuccessListener { contratoSnapshot ->
                            if (!contratoSnapshot.exists()) {
                                println("Contrato $tipoContratoId não encontrado no Firestore!")
                                return@addOnSuccessListener
                            }

                            val tipoContrato =
                                if (tipoContratoId.startsWith("professor")) "Professor" else "Assistente"
                            val detalhesHoras = contratoSnapshot.get(detalhesContrato) as? List<String>

                            val horas = detalhesHoras?.getOrNull(0) ?: "Desconhecido"
                            val percentagem = detalhesHoras?.getOrNull(1) ?: "0%"

                            println("Contrato encontrado -> Docente: $userId, Tipo: $tipoContrato, Horas: $horas, Percentagem: $percentagem, Ano Letivo: $anoLetivo")

                            contratosDetalhados.add(
                                ContratoDocenteDetalhado(
                                    userId = userId,
                                    tipoContrato = tipoContrato,
                                    horas = horas,
                                    percentagem = percentagem,
                                    anoLetivo = anoLetivo
                                )
                            )

                            if (contratosDetalhados.size == snapshot.size()) {
                                onUpdate(contratosDetalhados)
                            }
                        }
                }
            }
    }

    fun removeListener() {
        listener?.remove()
        listener = null
    }
}