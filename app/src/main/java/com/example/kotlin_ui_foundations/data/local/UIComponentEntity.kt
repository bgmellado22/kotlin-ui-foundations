package com.example.kotlin_ui_foundations.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "components")
data class UIComponentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val isFavorite: Boolean = false
)