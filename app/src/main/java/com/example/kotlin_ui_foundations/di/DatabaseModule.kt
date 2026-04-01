package com.example.kotlin_ui_foundations.di

import android.content.Context
import androidx.room.Room
import com.example.kotlin_ui_foundations.data.local.AppDatabase
import com.example.kotlin_ui_foundations.data.local.ComponentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ui_foundations_db"
        ).build()
    }

    @Provides
    fun provideComponentDao(db: AppDatabase): ComponentDao {
        return db.componentDao()
    }
}