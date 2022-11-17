package com.example.monni.data.remote.dto

import com.example.monni.data.local.entity.Category

data class CategoryDto(
    val id: String,
    val amount: Double,
    val color: String,
    val limit: Double,
    val name: String
)

fun CategoryDto.mapToEntity(): Category = Category(
    amount = amount,
    color = color,
    limit = limit,
    name = name
)