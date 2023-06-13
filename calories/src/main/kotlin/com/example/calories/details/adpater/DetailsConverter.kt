package com.example.calories.details.adpater

import com.example.calories.FoodEntry
import org.joda.time.DateTimeZone

object DetailsConverter {
    fun List<FoodEntry>.toEntryRow(): List<EntryRow> {
        val entryRows = mutableListOf<EntryRow>()
        val entriesByDate = this.groupBy { it.entryDate.toDateTime(DateTimeZone.forOffsetHours(-8)).toLocalDate() }
        entriesByDate.forEach { (date, entryOfSameDate) ->
            entryRows.add(EntryRowDate(date = date))
            val datedEntryByMealType = entryOfSameDate.groupBy { it.type }
            datedEntryByMealType.forEach { (mealType, entriesOfSameMealType) ->
                entryRows.add(EntryRowMealType(mealType = mealType))
                entryRows.addAll(
                    entriesOfSameMealType.map { foodEntry ->
                        EntryRowMealEntry(foodEntry)
                    })
            }
            entryRows.add(
                EntryRowCalories(
                    caloriesTotal = entryOfSameDate.map { it.calories }.reduce { total, current ->
                        total + current
                    }
                ))
            entryRows.add(EntryRowDivider)
        }

        return entryRows
    }
}