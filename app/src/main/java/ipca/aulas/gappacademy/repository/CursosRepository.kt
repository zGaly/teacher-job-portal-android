package ipca.aulas.gappacademy.repository

import ipca.aulas.gappacademy.models.UCs
import ipca.aulas.gappacademy.utils.FirebaseUtil

object CursosRepository {

    fun getAll(onResult: (List<UCs>, String?) -> Unit) {
        val db = FirebaseUtil.db
        db.collection("UCS")
            .orderBy("nomeCurso")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    onResult(emptyList(), e.message)
                    return@addSnapshotListener
                }

                val ucs = ArrayList<UCs>()
                for (doc in value!!) {
                    val uc = doc.toObject(UCs::class.java)
                    uc.docId = doc.id
                    ucs.add(uc)
                }

                val uniqueUcs = ucs.distinctBy { it.nomeCurso }
                onResult(uniqueUcs, null)
            }
    }
}