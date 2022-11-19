package com.example.monni.data.remote.dto

import com.example.monni.data.local.entity.Category

data class CategoryDto(
    val id: String = "",
    val amount: Double = 0.0,
    val color: String = "",
    val limit: Double = 0.0,
    val name: String = ""
)

fun CategoryDto.mapToEntity(): Category = Category(
    id = id,
    amount = amount,
    color = color,
    limit = limit,
    name = name
)