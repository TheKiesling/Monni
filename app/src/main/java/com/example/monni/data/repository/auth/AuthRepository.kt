package com.example.monni.data.repository.auth

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String) : String?
}