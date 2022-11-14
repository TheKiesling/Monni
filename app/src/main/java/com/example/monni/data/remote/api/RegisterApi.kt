package com.example.monni.data.remote.api

import com.example.monni.data.remote.dto.CategoryDto
import com.example.monni.data.remote.dto.RegisterDto

interface RegisterApi {
    suspend fun insert(registerDto: RegisterDto)
    suspend fun getRegisters(category: String): List<RegisterDto>?
}