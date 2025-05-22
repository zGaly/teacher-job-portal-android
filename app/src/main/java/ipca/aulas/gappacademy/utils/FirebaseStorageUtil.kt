package ipca.aulas.gappacademy.utils

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class FirebaseStorageUtil {

    private val storage: FirebaseStorage = Firebase.storage

    suspend fun getImageUrl(path: String): String? {
        return try {
            val storageRef = storage.reference.child(path)
            storageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}