package com.example.leetincoginto.googleauth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject



class AuthRepository @Inject constructor() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Sign in with Email & Password
    suspend fun signInWithEmail(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true // ✅ Login successful
        } catch (e: Exception) {
            false // ❌ Login failed
        }
    }

    // Register a new user
    fun signUpWithEmail(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Signup successful!")
                } else {
                    onResult(false, task.exception?.message) // Return error message
                }
            }
    }

    // Logout
    fun signOut() {
        auth.signOut()
    }
}


// Authentication State
sealed class AuthState {
    object Idle : AuthState()
    object CodeSent : AuthState()
    data class Success(val phoneNumber: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
