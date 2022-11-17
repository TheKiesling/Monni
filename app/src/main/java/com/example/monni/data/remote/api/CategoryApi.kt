package com.example.monni.data.remote.api

import com.example.monni.data.remote.dto.CategoryDto

interface CategoryApi {
    suspend fun getCategories(id: String): List<CategoryDto>
}