package com.example.persistencecalories.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.calories.FoodEntry
import com.example.persistencecalories.api.FoodEntryDao
import com.example.persistencecalories.impl.converters.FoodEntryConverters

@Database(
    entities = [FoodEntry::class],
    version = 1
)
@TypeConverters(FoodEntryConverters::class)
abstract class FoodEntryDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "food_entry.db"
    }

    abstract fun foodEntryDao(): FoodEntryDao
}