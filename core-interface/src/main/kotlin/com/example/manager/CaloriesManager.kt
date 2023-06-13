package com.example.manager

import com.example.calories.FoodEntry
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime

interface CaloriesManager {
    fun loadEntries(dateTime: DateTime? = null): Observable<List<FoodEntry>>
    fun add(entry: FoodEntry): Completable
    fun update(entry: FoodEntry): Completable
    fun delete(entry: FoodEntry): Completable
    fun search(key: String): Single<List<FoodEntry>>
}