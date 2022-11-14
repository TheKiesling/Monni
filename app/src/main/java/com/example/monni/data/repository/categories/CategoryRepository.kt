package com.example.monni.data.repository.categories

import com.example.monni.data.local.entity.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}