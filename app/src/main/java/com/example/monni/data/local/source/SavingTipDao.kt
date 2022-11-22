package com.example.monni.data.local.source

import androidx.room.*
import com.example.monni.data.local.entity.SavingTip

@Dao
interface SavingTipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savingTip: SavingTip)

    @Query("SELECT * FROM savingtip")
    suspend fun getSavingTips(): MutableList<SavingTip>
}