package com.example.monni.data.local.source

import androidx.room.*
import com.example.monni.data.local.entity.Notification

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification WHERE id = :id")
    suspend fun getNotifications(id: String): MutableList<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: Notification)

    @Query("DELETE FROM notification WHERE notificationID = :id")
    suspend fun delete(id: Int): Int
}