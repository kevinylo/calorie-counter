package com.example.calories.main

import com.example.calories.FoodEntry
import com.example.calories.MealCategoryData
import com.example.calories.modelconstant.MealType
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

object CaloriesConverter {
    fun List<FoodEntry>.toMealCategoryData(): MutableMap<MealType, MealCategoryData> {
        val map = mutableMapOf<MealType, MealCategoryData>()
        this.filter {
            it.entryDate.toDateTime(DateTimeZone.forOffsetHours(-8)) >=
                    DateTime.now(DateTimeZone.forOffsetHours(-8))
                        .toLocalDate().toDateTimeAtStartOfDay(DateTimeZone.forOffsetHours(-8))
        }.groupBy { it.type }.forEach { (type, entries) ->
            val caloriesTotal = entries.map { it.calories }.reduce { total, current ->
                total + current
            }
            val fatTotal = entries.mapNotNull { it.fat }.takeIf { it.isNotEmpty() }?.reduce { total, current ->
                total + current
            }
            val proteinTotal = entries.mapNotNull { it.protein }.takeIf { it.isNotEmpty() }?.reduce { total, current ->
                total + current
            }
            val carbsTotal = entries.mapNotNull { it.carb }.takeIf { it.isNotEmpty() }?.reduce { total, current ->
                total + current
            }
            map[type] = MealCategoryData(
                type = type,
                totalCalories = caloriesTotal,
                totalFat = fatTotal,
                totalProtein = proteinTotal,
                totalCarbs = carbsTotal
            )
        }
        return map
    }

    fun MutableMap<MealType, MealCategoryData>.patchWithAllCategories(): Map<MealType, MealCategoryData> {
        MealType.values().forEach { type ->
            if (!this.containsKey(type)) {
                this[type] = MealCategoryData(
                    type = type,
                    totalCalories = 0f,
                    totalFat = 0f,
                    totalProtein = 0f,
                    totalCarbs = 0f
                )
            }
        }
        return this.toMap()
    }
}
