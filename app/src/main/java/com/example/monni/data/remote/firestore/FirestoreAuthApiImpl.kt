package com.example.monni.data.remote.firestore

import com.example.monni.data.Resource
import com.example.monni.data.remote.api.AuthApi
import com.google.firebase.FirebaseError.ERROR_INVALID_EMAIL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreAuthApiImpl: AuthApi{
    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String> {
        return try {
            val auth = Firebase.auth
            val response = auth.signInWithEmailAndPassword(email, password).await()
            val user = response.user

            if (user != null)
                Resource.Success(data = user.uid)
            else
                Resource.Error(message = "Error en la ejecución")

        } catch (e: Exception) {
            var message = when(e.toString()){
                "ERROR_INVALID_EMAIL" -> "El correo electrónico no es válido"
                "ERROR_INVALID_CUSTOM_TOKEN" -> "Ingrese todos los campos necesarios"
                "ERROR_INVALID_CREDENTIAL" -> "Formato incorrecto en ingreso de datos"
                "ERROR_CUSTOM_TOKEN_MISMATCH" -> "Error en la verificación del usuario"
                "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta"
                "ERROR_USER_MISMATCH" -> "No existe una cuenta registrada con este correo"
                "ERROR_MISSING_EMAIL" -> "Ingrese un correo en el campo indicado"
                "ERROR_WEAK_PASSWORD" -> "La contraseña se encuentra en un formato inválido (mínimo 6 caracteres)"
                "ERROR_USER_NOT_FOUND" -> "No existe una cuenta registrada con este correo"
                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> "Ya existe una cuenta registrada con este correo"
                "ERROR_EMAIL_ALREADY_IN_USE"-> "Ya existe una cuenta registrada con este correo"
                else -> "Error en la ejecución"
            }
            Resource.Error(message = message)
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<String> {
        return try {
            val auth = Firebase.auth
            val response = auth.createUserWithEmailAndPassword(email, password).await()

            val user = response.user
            if (user != null)
                Resource.Success(data = user.uid)
            else
                Resource.Error(message = "Error en la ejecución")
        } catch (e: Exception) {
            var message = when(e.toString()){
                "ERROR_INVALID_EMAIL" -> "El correo electrónico no es válido"
                "ERROR_INVALID_CUSTOM_TOKEN" -> "Ingrese todos los campos necesarios"
                "ERROR_INVALID_CREDENTIAL" -> "Formato incorrecto en ingreso de datos"
                "ERROR_CUSTOM_TOKEN_MISMATCH" -> "Error en la verificación del usuario"
                "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta"
                "ERROR_USER_MISMATCH" -> "No existe una cuenta registrada con este correo"
                "ERROR_MISSING_EMAIL" -> "Ingrese un correo en el campo indicado"
                "ERROR_WEAK_PASSWORD" -> "La contraseña se encuentra en un formato inválido (mínimo 6 caracteres)"
                "ERROR_USER_NOT_FOUND" -> "No existe una cuenta registrada con este correo"
                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> "Ya existe una cuenta registrada con este correo"
                "ERROR_EMAIL_ALREADY_IN_USE"-> "Ya existe una cuenta registrada con este correo"
                else -> "Error en la ejecución"
            }
            Resource.Error(message = message)
        }
    }

}