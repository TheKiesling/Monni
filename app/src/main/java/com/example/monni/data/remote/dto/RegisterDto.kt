package com.example.monni.data.remote.dto

import com.example.monni.data.local.entity.Register
import java.time.LocalDate

data class RegisterDto(
    val amount: Double,
    val date: LocalDate,
    val desc: String
)

fun RegisterDto.mapToEntity(): Register = Register(
    amount = amount,
    date = date,
    desc = desc
)
