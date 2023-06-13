package com.example.calories.main

import com.example.calories.FoodEntry
import com.example.calories.MealCategoryData
import com.example.calories.modelconstant.MealType

object CaloriesConverter {
    fun List<FoodEntry>.toMealCategoryData(): MutableMap<MealType, MealCategoryData> {
        val map = mutableMapOf<MealType, MealCategoryData>()
        this.groupBy { it.type }.forEach { (type, entries) ->
            val caloriesTotal = entries.map { it.calories }.filter { it < 10000f }.reduce { total, current ->
                total + current
            }
            val fatTotal = entries.mapNotNull { it.fat }.filter { it < 10000f }.takeIf { it.isNotEmpty() }?.reduce { total, current ->
                total + current
            }
            val proteinTotal = entries.mapNotNull { it.protein }.filter { it < 10000f }.takeIf { it.isNotEmpty() }?.reduce { total, current ->
                total + current
            }
            val carbsTotal = entries.mapNotNull { it.carb }.filter { it < 10000f }.takeIf { it.isNotEmpty() }?.reduce { total, current ->
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
