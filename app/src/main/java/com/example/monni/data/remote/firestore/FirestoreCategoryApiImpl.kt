package com.example.monni.data.remote.firestore

import com.example.monni.data.remote.api.CategoryApi
import com.example.monni.data.remote.dto.CategoryDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class FirestoreCategoryApiImpl(
    private val db: FirebaseFirestore
): CategoryApi {
    override suspend fun getCategories(id: String): List<CategoryDto> {
        return try {
            val res = db
                .collection("users")
                .get()
                .await()

            res.documents.map { documentSnapshot ->
                documentSnapshot.toObject<CategoryDto>()!!
            }
        } catch (e: Exception){
            listOf<CategoryDto>()
        }
    }
}