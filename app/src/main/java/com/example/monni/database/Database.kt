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

data class Notification(
    val dateLimit: LocalDate,
    val title: String,
    val desc: String,
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

    private var notifications = mutableListOf(
        Notification(
            dateLimit = LocalDate.of(2022,12,25),
            title = "Saldar regalo de navidad",
            desc = "Muy bien! Interesante ver que Aida tiene 68 años en un lado y 61 en otro."
        ),
        Notification(
            dateLimit = LocalDate.of(2022, 11, 2),
            title = "Tercer entregable",
            desc = "Excelente trabajo! En próximas presentaciones utilizar plantillas acorde al tema a presentar."
        ),
        Notification(
            dateLimit = LocalDate.of(2023,2,28),
            title = "El diseño del material audiovisual es adecuado: 4.",
            desc = "Esto me hace pensar que no hicieron el research suficiente para elegir la librería, ya que literal la primera oración del artículo dice:"
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
    fun getNotis(): List<Notification> {
        val notis = notifications.sortedBy{ it.dateLimit}
        return notis
    }
}