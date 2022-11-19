package com.example.monni.data.local.source

import androidx.room.*
import com.example.monni.data.local.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE id = :id")
    suspend fun getCategories(id: String): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<Category>)
}