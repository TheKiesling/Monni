package com.example.monni.data.repository.categories

import com.example.monni.data.local.entity.Category
import com.example.monni.data.remote.api.CategoryApi
import com.example.monni.data.remote.dto.mapToEntity

class CategoryRepositoryImpl(
    private val api: CategoryApi
): CategoryRepository {
    override suspend fun getCategories(id: String): List<Category> {
        val filteredDto = api.getCategories(id)
        return filteredDto.map {
            dto -> dto.mapToEntity()
        }
    }
}