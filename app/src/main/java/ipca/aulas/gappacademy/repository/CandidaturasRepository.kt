package ipca.aulas.gappacademy.repository


import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import ipca.aulas.gappacademy.models.Candidaturas
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class CandidaturaRepository {

    suspend fun enviarCandidaturas(nome: String, email: String, contacto: String, cvUri: Uri?, anuncioId: String? = null,): Boolean {
        return try {
            val cvRef = FirebaseStorage.getInstance()
                .reference
                .child("CandidaturasEsp/${UUID.randomUUID()}.pdf")

            cvUri?.let { cvRef.putFile(it).await() }

            val candidatura = Candidaturas().apply {
                this.nome = nome
                this.email = email
                this.contacto = contacto
                this.cvUrl = cvRef.path
                this.date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                this.anuncioId = anuncioId
                this.aceite = false
                this.enviado = true
            }

            FirebaseFirestore.getInstance()
                .collection("Candidaturas")
                .add(candidatura)
                .await()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getInstalacaoNameById(id: String): String {
        return try {
            val firestore = FirebaseFirestore.getInstance()
            val document = firestore.collection("Instalacoes").document(id).get().await()
            document.getString("name") ?: "Local não encontrado"
        } catch (e: Exception) {
            e.printStackTrace()
            "Erro ao carregar local"
        }
    }


    suspend fun enviarCandidaturaAnuncio(nome: String, email: String, contacto: String, cvUri: Uri?, anuncioId: String?): Boolean {
        return try {
            val cvRef = FirebaseStorage.getInstance()
                .reference
                .child("Candidaturas/${UUID.randomUUID()}.pdf")

            cvUri?.let { cvRef.putFile(it).await() }

            val candidatura = Candidaturas().apply {
                this.nome = nome
                this.email = email
                this.contacto = contacto
                this.cvUrl = cvRef.path
                this.date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                this.anuncioId = anuncioId
                this.aceite = false
                this.enviado = true
            }

            FirebaseFirestore.getInstance()
                .collection("Candidaturas")
                .add(candidatura)
                .await()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}