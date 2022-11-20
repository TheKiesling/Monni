package com.example.monni.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category (
        val id: String,
        val amount: Double,
        val color: String,
        val limit: Double,
        @PrimaryKey val name: String,
        )
