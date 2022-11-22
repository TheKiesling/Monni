package com.example.monni.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notification(
    val id: String,
    val dateLimit: String,
    @PrimaryKey val title: String,
    val desc: String
)
