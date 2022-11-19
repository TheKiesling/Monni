package com.example.monni.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Register (
    @PrimaryKey
    val id: String,
    val category: String,
    val amount: Double,
    val date: String,
    val description: String
        )