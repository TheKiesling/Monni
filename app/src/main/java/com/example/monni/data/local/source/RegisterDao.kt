package com.example.monni.data.local.source

import androidx.room.*
import com.example.monni.data.local.entity.Register

@Dao
interface RegisterDao {
    @Query("SELECT * FROM register WHERE id = :id AND category = :category")
    suspend fun getRegisters(id: String, category: String): List<Register>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(register: Register)
}