package com.example.monni.database

import androidx.core.content.ContextCompat
import com.example.monni.R
import java.util.*

data class User(
    val username: String,
    val password: String,
    val registers: List<Register>,
    val limit: Double
)

data class Category(
    val id: Int,
    val name: String,
    val color: String,
    val registers: MutableList<Register>,
    val amount: Double,
    val limit: Double
)

data class Register(
    val category: Int,
    val description: String,
    val date: Date,
    val amount: Double
    )

object Database {
    private val users = mutableListOf(
        User(
            username = "the_kiesling",
            password = "contraseñaSegura",
            registers = mutableListOf(
                Register(
                    category = 0,
                    description = "Balón de futbol",
                    date = Date(2019, 7,29),
                    amount = 115.00
                )
            ),
            limit = 1000.00
        )
    )

    private val categories = listOf(
        Category(
            id = 0,
            name = "Actividades recreativas",
            color = "#FCD3F7",
            registers = mutableListOf(
                Register(
                    category = 0,
                    description = "Balón de futbol",
                    date = Date(2019, 7,29),
                    amount = 115.00
                )
            ),
            amount = 150.00,
            limit = 200.00
        )
    )

    fun getCategories() = categories
}