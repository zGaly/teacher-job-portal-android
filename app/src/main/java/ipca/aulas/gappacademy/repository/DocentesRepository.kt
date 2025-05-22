package ipca.aulas.gappacademy.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class DocentesRepository {

    private val db = FirebaseFirestore.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun getDocenteReference(userId: String) = db.collection("Docentes").document(userId)

    suspend fun updatePartialDocenteProfile(userId: String, updatedFields: Map<String, Any?>) {
        try {
            val docRef = db.collection("Docentes").document(userId)
            docRef.update(updatedFields).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun uploadProfileImage(userId: String, imageUri: Uri): String? {
        return try {
            val filePath = "DocentesIMG/$userId.jpg"
            val storageRef = FirebaseStorage.getInstance().reference.child(filePath)

            storageRef.putFile(imageUri).await()

            filePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteProfileImage(userId: String) {
        try {
            val storageRef = storage.reference.child("DocentesIMG")
            val allFiles = storageRef.listAll().await()

            val userFiles = allFiles.items.filter { it.name.startsWith(userId) }

            userFiles.forEach { file ->
                println("Deleting file: ${file.name}")
                file.delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getUserName(userId: String): String? {
        return try {
            val document = db.collection("Docentes").document(userId).get().await()
            document.getString("nome")
        } catch (e: Exception) {
            null
        }
    }
}