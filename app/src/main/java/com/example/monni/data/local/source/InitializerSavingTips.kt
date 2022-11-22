package com.example.monni.data.local.source
import com.example.monni.data.local.entity.SavingTip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var Database: CategoryDatabase

private val limitCardTip = SavingTip(
    name = "Limit on your card",
    description = "Set a limit to how much you can spend on your credit or debit cards. This stops you from overspending"
)

private val unusedItemsTip = SavingTip(
    name = "Sell your unused items",
    description = "Not only does this help declutter your home, but it can also mean earning quite a good amount of extra money"
)

private val savingAccountTip = SavingTip(
    name = "Designated savings account",
    description = "To save money fast, you need to separate the money you spend on your daily needs from the money you intend to save. This means setting up a designated savings account. "
)

private val RecordExpensesTip = SavingTip(
    name = "Record your expenses",
    description = "The first step to start saving money is figuring out how much you spend. Keep track of all your expenses "
)

object InitializerSavingTips {
    fun createTip(name: String, description: String, database: CategoryDatabase) {
        val savingTip = SavingTip(
            name = name,
            description = description
        )
        Database = database
        CoroutineScope(Dispatchers.IO).launch {
            Database.savingTipsDao().insert(savingTip)
        }
    }

    fun createTipCall(database:CategoryDatabase) {
        val listOfTips = listOf(
            limitCardTip, unusedItemsTip, savingAccountTip,
            RecordExpensesTip
        )
        for (tip in listOfTips) {
            createTip(tip.name, tip.description, database)
        }
    }
}


