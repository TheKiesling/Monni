package com.example.monni.database

import androidx.core.content.ContextCompat
import com.example.monni.R
import java.time.LocalDate
import java.util.*

data class User(
    val username: String,
    val password: String,
    val categories: List<Category>,
    val limit: Double
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
                            amount = 115.05
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

    fun getNotis(): List<Notification> {
        val notis = notifications.sortedBy{ it.dateLimit}
        return notis
    }

    fun getUser(username: String): User {
        val user = users.first{it.username.equals(username)}
        return user
    }

    fun getCategories(username: String): List<Category> {
        val user = getUser(username)
        val categories = user.categories
        return categories
    }

    fun getTotalCategories(username: String): Double{
        var totalSum:Double = 0.0;
        val categories = getCategories(username)
        for(currentCategory in categories){
            totalSum += currentCategory.amount
        }
        return totalSum
    }
}