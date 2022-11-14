package com.example.monni.data.remote.api

import com.example.monni.data.Resource

interface AuthApi {
    suspend fun signInWithEmailAndPassword(email: String, password: String) : Resource<String>
}