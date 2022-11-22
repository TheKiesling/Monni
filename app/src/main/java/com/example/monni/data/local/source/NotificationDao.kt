package com.example.monni.data.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.monni.data.local.entity.Notification

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification WHERE id = :id")
    suspend fun getNotifications(id: String): MutableList<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: Notification)

    @Query("DELETE FROM notification WHERE title = :id")
    suspend fun delete(id: String)
}