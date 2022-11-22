package com.example.monni.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavingTip (
    @PrimaryKey(autoGenerate = true)
    val savingTipID: Int = 0,
    val name: String,
    val description: String,
    var expand : Boolean = false
)