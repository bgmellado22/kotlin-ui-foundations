package com.example.kotlin_ui_foundations.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ComponentDao {
    @Query("SELECT * FROM components ORDER BY name ASC")
    fun getAllComponents(): Flow<List<UIComponentEntity>>

    @Query("SELECT * FROM components WHERE id = :id")
    suspend fun getComponentById(id: Int): UIComponentEntity?

    @Update
    suspend fun updateComponent(component: UIComponentEntity)

    @Query("SELECT * FROM components WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<UIComponentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponent(component: UIComponentEntity)
}