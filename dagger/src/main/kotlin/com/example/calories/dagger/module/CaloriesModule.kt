package com.example.calories.dagger.module

import android.content.Context
import androidx.room.Room
import co.example.calories.CaloriesManagerImpl
import co.example.calories.EntryRepositoryImpl
import com.example.manager.CaloriesManager
import com.example.persistencecalories.api.FoodEntryDao
import com.example.persistencecalories.impl.FoodEntryDatabase
import com.example.repository.EntryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    (AppModule::class)
])

class CaloriesModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideCaloriesRepository(dao: FoodEntryDao): EntryRepository {
        return EntryRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideCaloriesManager(
        repo: EntryRepository
    ): CaloriesManager {
        return CaloriesManagerImpl(repo)
    }

    @Provides
    @Singleton
    fun provideFoodEntryDao(database: FoodEntryDatabase): FoodEntryDao {
        return database.foodEntryDao()
    }

    @Provides
    @Singleton
    fun provideFoodEntryDatabase(context: Context): FoodEntryDatabase {
        return Room.databaseBuilder(
            context,
            FoodEntryDatabase::class.java,
            FoodEntryDatabase.DATABASE_NAME
        )
            .build()
    }
}