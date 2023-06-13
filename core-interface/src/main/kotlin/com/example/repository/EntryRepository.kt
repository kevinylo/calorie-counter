package com.example.repository

import com.example.calories.FoodEntry
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

interface EntryRepository {
    fun foodEntriesBefore(dateTime: DateTime? = DateTime.now(DateTimeZone.forOffsetHours(-8))): Observable<List<FoodEntry>>
    fun foodEntriesTodayOr(dateTime: DateTime? = DateTime.now(DateTimeZone.forOffsetHours(-8)).toLocalDate().toDateTimeAtStartOfDay()): Single<List<FoodEntry>>
    fun insertFoodEntry(entry: FoodEntry): Completable
    fun deleteFoodEntry(entry: FoodEntry): Completable
    fun updateFoodEntry(entry: FoodEntry): Completable
    fun search(key: String): Single<List<FoodEntry>>
    fun clear(): Completable
}