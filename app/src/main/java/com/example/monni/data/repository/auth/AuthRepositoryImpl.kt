package com.example.monni.data.repository.auth

import com.example.monni.data.Resource
import com.example.monni.data.remote.api.AuthApi
import com.example.monni.data.repository.auth.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi
): AuthRepository {
    override suspend fun signInWithEmailAndPassword(email: String, password: String) : String? {
        val authResponse = authApi.signInWithEmailAndPassword(email, password)

        return if (authResponse is Resource.Success)
            authResponse.data!!
        else
            null
    }
}