package com.example.monni.database

import androidx.core.content.ContextCompat
import com.example.monni.R
import java.time.LocalDate
import java.util.*

data class User(
    val username: String,
    val password: String,
    val limit: Double,
    val categories: List<Category>

)

data class Category(
    val name: String,
    val color: String,
    val registers: MutableList<Register>,
    val amount: Double,
    val limit: Double
)

data class Register(
    val description: String,
    val date:LocalDate,
    val amount: Double
    )

object Database {
    private val users = mutableListOf(
        User(
            username = "the_kiesling",
            password = "contraseñaSegura",
            limit = 1000.00,
            categories = listOf(
                Category(
                    name = "Actividades Recreativas",
                    color = "#FCD3F7",
                    registers = mutableListOf(
                        Register(
                            description = "Balón de futbol",
                            date = LocalDate.of(2019,7,29),
                            amount = 115.00
                        )
                    ),
                    amount = 150.00,
                    limit = 200.00
                ),


                Category(
                    name = "Gastos Fijos",
                    color = "#FFB9F1",
                    registers = mutableListOf(
                        Register(
                            description = "Bizcochito",
                            date = LocalDate.of(2019,7,29),
                            amount = 115.00
                        )
                    ),
                    amount = 100.00,
                    limit = 200.00
                ),

                Category(
                    name = "Transporte",
                    color = "#FF83EA",
                    registers = mutableListOf(
                        Register(
                            description = "Balón de futbol",
                            date = LocalDate.of(2019,7,29),
                            amount = 115.00
                        )
                    ),
                    amount = 25.00,
                    limit = 200.00
                ),

                Category(
                    name = "Comida",
                    color = "#D0C7FF",
                    registers = mutableListOf(
                        Register(
                            description = "Balón de futbol",
                            date = LocalDate.of(2019,7,29),
                            amount = 115.00
                        )
                    ),
                    amount = 75.00,
                    limit = 200.00
                ),

                Category(
                    name = "Vestimenta",
                    color = "#ADB6FD",
                    registers = mutableListOf(
                        Register(
                            description = "Balón de futbol",
                            date = LocalDate.of(2019,7,29),
                            amount = 115.00
                        )
                    ),
                    amount = 175.00,
                    limit = 200.00
                ),

                Category(
                    name = "Supermercado",
                    color = "#9796F0",
                    registers = mutableListOf(
                        Register(
                            description = "Balón de futbol",
                            date = LocalDate.of(2019,7,29),
                            amount = 115.00
                        )
                    ),
                    amount = 150.00,
                    limit = 200.00
                ),

                Category(
                    name = "Emergencia",
                    color = "#7F7FC7",
                    registers = mutableListOf(
                        Register(
                            description = "Balón de futbol",
                            date = LocalDate.of(2019,7,29),
                            amount = 115.00
                        )
                    ),
                    amount = 10.00,
                    limit = 200.00
                )

            )

        )
    )

    fun getCategories() = users[0].categories
    fun getRegisters(categoryName: String): MutableList<Register> {
        val registers: MutableList<Register> = mutableListOf()
        for (category in users[0].categories)
            if (category.name == categoryName)
                for (register in category.registers)
                    registers.add(register)
        return registers
    }
}