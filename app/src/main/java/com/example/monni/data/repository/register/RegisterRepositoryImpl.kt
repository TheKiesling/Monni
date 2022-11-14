package com.example.monni.data.repository.register

import com.example.monni.data.local.entity.Register
import com.example.monni.data.local.entity.mapToDto
import com.example.monni.data.remote.api.RegisterApi
import com.example.monni.data.remote.dto.mapToEntity

class RegisterRepositoryImpl(
    private val api: RegisterApi
): RegisterRepository {
    override suspend fun createRegister(register: Register) {
        val dtoToInsert = register.mapToDto()
        api.insert(dtoToInsert)
    }

    override suspend fun getRegisters(category: String): List<Register>? {
        val filteredDto = api.getRegisters(category)
        return filteredDto?.map {
            dto -> dto.mapToEntity()
        }
    }
}