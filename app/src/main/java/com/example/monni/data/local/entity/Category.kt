package com.example.monni.data.local.entity

import com.example.monni.data.remote.dto.CategoryDto
import com.example.monni.data.remote.dto.RegisterDto

data class Category (
        val amount: Double,
        val color: String,
        val limit: Double,
        val name: String
        )

fun Category.mapToDto(): CategoryDto = CategoryDto(
        amount = amount,
        color = color,
        limit = limit,
        name = name
)