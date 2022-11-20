package com.example.monni.data.local.source

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.monni.data.local.entity.Notification

interface NotificationDao {
    @Query("SELECT * FROM notification WHERE id = :id")
    suspend fun getNotifications(id: String): List<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: Notification)
}