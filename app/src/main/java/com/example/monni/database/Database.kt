package com.example.monni.database

import androidx.core.content.ContextCompat
import com.example.monni.R
import java.time.LocalDate
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
    val date:LocalDate,
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
                    date = LocalDate.of(2019,7,29),
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
                    date = LocalDate.of(2019,7,29),
                    amount = 115.00
                )
            ),
            amount = 150.00,
            limit = 200.00
        ),

        Category(
            id = 1,
            name = "Gastos Fijos",
            color = "#FFB9F1",
            registers = mutableListOf(
                Register(
                    category = 1,
                    description = "Balón de futbol",
                    date = LocalDate.of(2019,7,29),
                    amount = 115.00
                )
            ),
            amount = 100.00,
            limit = 200.00
        ),

        Category(
            id = 2,
            name = "Transporte",
            color = "#FF83EA",
            registers = mutableListOf(
                Register(
                    category = 2,
                    description = "Balón de futbol",
                    date = LocalDate.of(2019,7,29),
                    amount = 115.00
                )
            ),
            amount = 25.00,
            limit = 200.00
        ),

        Category(
            id = 3,
            name = "Comida",
            color = "#D0C7FF",
            registers = mutableListOf(
                Register(
                    category = 3,
                    description = "Balón de futbol",
                    date = LocalDate.of(2019,7,29),
                    amount = 115.00
                )
            ),
            amount = 75.00,
            limit = 200.00
        ),

        Category(
            id = 4,
            name = "Vestimenta",
            color = "#ADB6FD",
            registers = mutableListOf(
                Register(
                    category = 4,
                    description = "Balón de futbol",
                    date = LocalDate.of(2019,7,29),
                    amount = 115.00
                )
            ),
            amount = 175.00,
            limit = 200.00
        ),

        Category(
            id = 5,
            name = "Supermercado",
            color = "#9796F0",
            registers = mutableListOf(
                Register(
                    category = 5,
                    description = "Balón de futbol",
                    date = LocalDate.of(2019,7,29),
                    amount = 115.00
                )
            ),
            amount = 150.00,
            limit = 200.00
        ),

        Category(
            id = 6,
            name = "Emergencia",
            color = "#7F7FC7",
            registers = mutableListOf(
                Register(
                    category = 6,
                    description = "Balón de futbol",
                    date = LocalDate.of(2019,7,29),
                    amount = 115.00
                )
            ),
            amount = 10.00,
            limit = 200.00
        )





    )

    fun getCategories() = categories
    fun  getRegisters(): MutableList<Register> {
        val registers: MutableList<Register> = mutableListOf()
        for (category in categories){
            registers.add(category.registers[0])
        }
        return registers
    }
}