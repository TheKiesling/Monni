package com.example.monni.data.local.entity

import com.example.monni.data.remote.dto.RegisterDto
import java.time.LocalDate

data class Register (
    val amount: Double,
    val date: LocalDate,
    val desc: String
        )

fun Register.mapToDto(): RegisterDto = RegisterDto(
    amount = amount,
    date = date,
    desc = desc
)