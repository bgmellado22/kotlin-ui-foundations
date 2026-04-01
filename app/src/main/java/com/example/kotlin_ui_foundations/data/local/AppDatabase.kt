package com.example.kotlin_ui_foundations.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UIComponentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun componentDao(): ComponentDao
}