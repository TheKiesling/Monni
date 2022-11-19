package com.example.monni.data.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.monni.data.local.entity.Register

@Dao
interface RegisterDao {
    @Query("SELECT * FROM register WHERE id = :id AND category = :category")
    suspend fun getRegisters(id: String, category: String): List<Register>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(register: Register)
}