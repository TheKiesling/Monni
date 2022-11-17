package com.example.monni.data.local.entity

import com.example.monni.data.remote.dto.CategoryDto
import com.example.monni.data.remote.dto.RegisterDto

data class Category (
        val id: String,
        val amount: Double,
        val color: String,
        val limit: Double,
        val name: String
        )
