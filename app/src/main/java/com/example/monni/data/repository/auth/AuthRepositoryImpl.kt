package com.example.monni.data.repository.auth

import com.example.monni.data.Resource
import com.example.monni.data.remote.api.AuthApi

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

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): String? {
        val authResponse = authApi.createUserWithEmailAndPassword(email, password)

        return if (authResponse is Resource.Success)
            authResponse.data!!
        else
            null
    }
}