package com.example.monni.data.repository.auth

import com.example.monni.data.Resource

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String) : String?
    suspend fun createUserWithEmailAndPassword(email: String, password: String): String?
}