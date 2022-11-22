package com.example.monni.data.local.source

import androidx.room.Database
import androidx.room.RoomDatabase
<<<<<<< Updated upstream
import com.example.monni.data.local.entity.Category
import com.example.monni.data.local.entity.Register

@Database(entities = [Category::class, Register::class], version = 1)
abstract class CategoryDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun registerDao(): RegisterDao
=======
import com.example.monni.data.local.entity.*

@Database(entities = [Category::class, Register::class, Notification::class, User::class, SavingTip::class], version = 1)
abstract class CategoryDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun registerDao(): RegisterDao
    abstract fun notificationDao(): NotificationDao
    abstract fun savingTipsDao(): SavingTipDao
    abstract fun userDao(): UserDao
>>>>>>> Stashed changes
}