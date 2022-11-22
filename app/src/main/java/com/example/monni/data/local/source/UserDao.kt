package com.example.monni.data.local.source

import androidx.room.*
import com.example.monni.data.local.entity.SavingTip
import com.example.monni.data.local.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUser(email: String): User

    @Query("UPDATE user SET `goal` = :goal WHERE email = :email")
    suspend fun updateLimit(email: String, goal: Double)
}