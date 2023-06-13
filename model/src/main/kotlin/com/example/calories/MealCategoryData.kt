package com.example.calories

import com.example.calories.modelconstant.MealType

data class MealCategoryData(
    val type: MealType,
    val totalCalories: Float,
    val totalFat: Float? = null,
    val totalProtein: Float? = null,
    val totalCarbs: Float? = null
)
