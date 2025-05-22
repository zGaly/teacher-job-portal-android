package ipca.aulas.gappacademy.repository

import android.util.Log
import ipca.aulas.gappacademy.models.UCs
import ipca.aulas.gappacademy.utils.FirebaseUtil

object UCsRepository {

    private fun getFirestoreInstance() = FirebaseUtil.db

    fun getUcsBySiglaCurso(
        siglaCurso: String,
        onResult: (List<UCs>, String?) -> Unit)
    {
        getFirestoreInstance().collection("UCS")
            .orderBy("ano")
            .whereEqualTo("siglaCurso", siglaCurso)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    onResult(emptyList(), e.message)
                    return@addSnapshotListener

                    Log.e("UCsRepository", "Erro ao obter UCs", e)
                }
                val ucs = value?.map { doc ->
                    val uc = doc.toObject(UCs::class.java)
                    uc.docId = doc.id
                    uc
                } ?: emptyList()
                val uniqueUCs = ucs.distinctBy { it.uc }
                onResult(uniqueUCs, null)
            }
    }

    fun getUcById(
        ucId: String,
        onResult: (UCs?, String?) -> Unit
    ) {
        println("Observando UC com ID: $ucId")
        getFirestoreInstance().collection("UCS").document(ucId)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    onResult(null, e.message)
                    return@addSnapshotListener
                }
                val uc = value?.toObject(UCs::class.java)?.apply {
                    this.docId = value.id
                }
                onResult(uc, null)
            }
    }
}