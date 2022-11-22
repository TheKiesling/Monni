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
            var message: String = ""
            var flag: Boolean = false
            var data = ""
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (user != null) {
                            flag = true
                            data = user.uid
                        }
                        else{
                            null
                        }

                    } else {
                        val errorCode = (task.exception).toString()
                        message =
                            if (errorCode == "ERROR_INVALID_EMAIL")
                                "El correo electrónico no es valido"
                            else if (errorCode == "ERROR_WRONG_PASSWORD")
                                "La contraseña es incorrecta"
                        else
                            "0"
                    }


                }
            if (flag)
                Resource.Success(data = data)
            else
                Resource.Error(message=message)


        } catch (e: Exception) {
            Resource.Error(message = "User not found")
        }
    }

    override suspend fun createUserWithEmailAndPassword(
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
                Resource.Error(message = "Error")
        } catch (e: Exception) {
            Resource.Error(message = "Error")
        }
    }

}