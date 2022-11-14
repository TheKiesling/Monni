package com.example.monni.data.repository.register

import com.example.monni.data.local.entity.Register

interface RegisterRepository {
    suspend fun createRegister(register: Register)
    suspend fun getRegisters(category: String): List<Register>?
}