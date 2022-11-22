package com.example.monni.data.local.source

import androidx.room.*
import com.example.monni.data.local.entity.Register
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

    @Query("UPDATE user SET `savings` = :savings WHERE email = :email")
    suspend fun updateSavings(email: String, savings: Double)

    @Query("SELECT `savings` FROM user WHERE email = :email")
    suspend fun getSavings(email: String): Double

}