package com.example.monni.database

import com.example.monni.R
import com.example.monni.data.local.entity.Category
import com.example.monni.data.local.entity.Register
import com.example.monni.data.local.entity.SavingTip
import java.time.LocalDate
import java.util.*

data class User(
    val username: String,
    val categories: List<Category>,
    val limit: Double,
    val savings: Double,
    val goal: Double
)

data class Notification(
    val dateLimit: LocalDate,
    val title: String,
    val desc: String,
)

object Database {

    private val users = mutableListOf(
        User(
            username = "Adrian",
            limit = 1000.00,
            savings = 500.00,
            goal = 700.00,
            categories = listOf(
                Category(
                    id = "Adrian",
                    name = "Actividades Recreativas",
                    color = "#FCD3F7",
                    amount = 150.00,
                    limit = 200.00
                ),
                Category(
                    id = "Adrian",
                    name = "Gastos Fijos",
                    color = "#FFB9F1",
                    amount = 100.00,
                    limit = 200.00
                ),

                Category(
                    id = "Adrian",
                    name = "Transporte",
                    color = "#FF83EA",
                    amount = 25.00,
                    limit = 200.00
                ),

                Category(
                    id = "Adrian",
                    name = "Comida",
                    color = "#D0C7FF",
                    amount = 75.00,
                    limit = 200.00
                ),

                Category(
                    id = "Adrian",
                    name = "Vestimenta",
                    color = "#ADB6FD",
                    amount = 175.00,
                    limit = 200.00
                ),

                Category(
                    id = "Adrian",
                    name = "Supermercado",
                    color = "#9796F0",
                    amount = 150.00,
                    limit = 200.00
                ),

                Category(
                    id = "Adrian",
                    name = "Emergencia",
                    color = "#7F7FC7",
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
            desc = "Muy bien! Interesante ver que Aida tiene 68 a??os en un lado y 61 en otro."
        ),
        Notification(
            dateLimit = LocalDate.of(2022, 11, 2),
            title = "Tercer entregable",
            desc = "Excelente trabajo! En pr??ximas presentaciones utilizar plantillas acorde al tema a presentar."
        ),
        Notification(
            dateLimit = LocalDate.of(2023,2,28),
            title = "El dise??o del material audiovisual es adecuado: 4.",
            desc = "Esto me hace pensar que no hicieron el research suficiente para elegir la librer??a, ya que literal la primera oraci??n del art??culo dice:"
        )
    )

    private var savingTips = mutableListOf(
        SavingTip(
            name = "30-day period",
            description = "Keep track of all of your finances over a 30-day period",
            expand = false
        ),
        SavingTip(
            name = "Identify",
            description = "Identify any variable costs that you can start cutting back",
            expand = false
        ),
        SavingTip(
            name = "Sell your unused items",
            description = "Not only does this help declutter your home, but it can also mean earning quite a good amount of extra money",
            expand = false
        )
    )

    fun getNotis(): List<Notification> {
        val notis = notifications.sortedBy{ it.dateLimit}
        return notis
    }

    fun getSavingTips(): List<SavingTip> {
        val savingTips = savingTips
        return savingTips
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