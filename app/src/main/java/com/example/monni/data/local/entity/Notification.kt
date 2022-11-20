package com.example.monni.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notification(
    @PrimaryKey (autoGenerate = true)
    val notificationID: Int = 0,
    val id: String,
    val dateLimit: String,
    val title: String,
    val desc: String
)
