package com.example.monni.data.local.source

import androidx.room.*
import com.example.monni.data.local.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE id = :id")
    suspend fun getCategories(id: String): List<Category>

    @Query("SELECT * FROM category WHERE id = :id AND name = :name")
    suspend fun getCategory(id: String, name: String): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<Category>)

    @Query("UPDATE category SET `limit` = :limit WHERE id = :id AND name = :name")
    suspend fun updateLimit(id: String, name: String, limit: Double)

    @Query("UPDATE category SET amount=:amount WHERE id = :id AND name = :name")
    suspend fun updateAmount(amount: Double, id: String, name: String)
}