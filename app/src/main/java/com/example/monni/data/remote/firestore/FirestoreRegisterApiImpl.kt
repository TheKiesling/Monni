package com.example.monni.data.remote.firestore

import com.example.monni.data.remote.api.RegisterApi
import com.example.monni.data.remote.dto.RegisterDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class FirestoreRegisterApiImpl(
    private val db: FirebaseFirestore
): RegisterApi {
    override suspend fun insert(registerDto: RegisterDto) {
        db.collection("registers")
            .add(registerDto)
            .await()
    }

    override suspend fun getRegisters(category: String): List<RegisterDto>? {
        return try {
            val res = db
                .collection("registers")
                .whereEqualTo("category", category)
                .get()
                .await()
            return res.documents.map { documentSnapshot ->
                documentSnapshot.toObject<RegisterDto>()!!
            }
        } catch(e: Exception){
            null
        }
    }
}