package com.example.monni.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey val email: String,
    val goal: Double,
    val savings: Double,
)