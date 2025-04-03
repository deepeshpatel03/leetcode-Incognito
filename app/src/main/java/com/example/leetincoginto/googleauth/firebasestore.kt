package com.example.leetincoginto.googleauth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class User(
    val userUID: String = "",
    val username: String = "",
    val leetcodeId: String = "",
    val friends: List<String> = emptyList()
)

class firestoreUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private val usersCollection = firestore.collection("users")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    // Get current user UID
    fun getCurrentUserUID(): String? {
        return auth.currentUser?.uid
    }

    // 1 Add a new user
    suspend fun addUser(username: String, leetcodeId: String): Boolean {
        val userUID = getCurrentUserUID() ?: return false

        return try {
            val user = User(userUID, username, leetcodeId)
            usersCollection.document(userUID).set(user).await()
            Log.d("Firestore", "User added: $userUID")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Error adding user", e)
            false
        }
    }

    // 2⃣ Get user details
    suspend fun getUser(userUID: String): User? {
        return try {
            val document = usersCollection.document(userUID).get().await()
            document.toObject<User>()
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching user", e)
            null
        }
    }

    // 3️ Update user's LeetCode ID
    suspend fun updateLeetCodeId(userUID: String, newLeetcodeId: String): Boolean {
        return try {
            usersCollection.document(userUID).update("leetcodeId", newLeetcodeId).await()
            Log.d("Firestore", "Updated LeetCode ID for $userUID")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Error updating LeetCode ID", e)
            false
        }
    }

    // 4️ Delete user
    suspend fun deleteUser(userUID: String): Boolean {
        return try {
            usersCollection.document(userUID).delete().await()
            Log.d("Firestore", "User deleted: $userUID")
            true
        } catch (e: Exception) {
            Log.e("Firestore", "Error deleting user", e)
            false
        }
    }

    // 5️ Add a friend by username
    suspend fun addFriend(userUID: String, friendUsername: String): Boolean {
        return try {
            val userRef = usersCollection.document(userUID)
            val userSnapshot = userRef.get().await()

            if (userSnapshot.exists()) {
                val currentFriends = userSnapshot.toObject<User>()?.friends ?: emptyList()
                if (!currentFriends.contains(friendUsername)) {
                    val updatedFriends = currentFriends + friendUsername
                    userRef.update("friends", updatedFriends).await()
                    Log.d("Firestore", "Added friend: $friendUsername to $userUID")
                    return true
                }
            }
            false
        } catch (e: Exception) {
            Log.e("Firestore", "Error adding friend", e)
            false
        }
    }

    // 6️ Remove a friend by username
    suspend fun removeFriend(userUID: String, friendUsername: String): Boolean {
        return try {
            val userRef = usersCollection.document(userUID)
            val userSnapshot = userRef.get().await()

            if (userSnapshot.exists()) {
                val currentFriends = userSnapshot.toObject<User>()?.friends ?: emptyList()
                if (currentFriends.contains(friendUsername)) {
                    val updatedFriends = currentFriends - friendUsername
                    userRef.update("friends", updatedFriends).await()
                    Log.d("Firestore", "Removed friend: $friendUsername from $userUID")
                    return true
                }
            }
            false
        } catch (e: Exception) {
            Log.e("Firestore", "Error removing friend", e)
            false
        }
    }

    // 7️Get all friends (list of usernames)
    // 7️ Get all friends (list of usernames)
    suspend fun getFriends(userUID: String): List<String> {
        return try {
            val document = usersCollection.document(userUID).get().await()
            document.toObject<User>()?.friends ?: emptyList()
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching friends", e)
            emptyList()
        }
    }

}
