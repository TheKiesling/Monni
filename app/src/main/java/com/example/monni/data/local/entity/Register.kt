package com.example.monni.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Register (
    @PrimaryKey (autoGenerate = true)
    var registerId: Int = 0,
    val id: String,
    val category: String,
    val amount: Double,
    val date: String,
    val description: String
        )