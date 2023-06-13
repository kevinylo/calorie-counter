package com.example.persistencecalories.api

import androidx.room.*
import com.example.calories.FoodEntry
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime

@Dao
abstract class FoodEntryDao {
    @Query("SELECT * FROM food_entry WHERE entry_date <= :date ORDER BY entry_date DESC LIMIT 100")
    abstract fun foodEntriesBeforeDateTime(date: DateTime): Observable<List<FoodEntry>>

    @Query("SELECT * FROM food_entry WHERE entry_date >= :date ORDER BY entry_date DESC LIMIT 100")
    abstract fun foodEntriesTodayOr(date: DateTime): Single<List<FoodEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertFoodEntry(vararg entry: FoodEntry): Completable

    @Delete
    abstract fun deleteFoodEntry(entry: FoodEntry): Completable

    @Update
    abstract fun updateFoodEntry(entry: FoodEntry): Completable

    @Query("SELECT * FROM food_entry WHERE name LIKE :key ORDER BY entry_date DESC LIMIT 100")
    abstract fun foodEntriesNameMatching(key: String): Single<List<FoodEntry>>

    @Query("DELETE FROM food_entry")
    abstract fun clearFoodEntries(): Completable
}