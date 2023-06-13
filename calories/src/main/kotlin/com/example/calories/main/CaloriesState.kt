package com.example.calories.main

import com.example.calories.MealCategoryData
import com.example.calories.modelconstant.MealType
import com.github.mikephil.charting.data.PieEntry


sealed interface CaloriesState
object Start: CaloriesState
data class AddEntry(val type: MealType): CaloriesState
data class SetPieEntries(val pieEntries: List<PieEntry>): CaloriesState
data class UpdateCategories(val data: Map<MealType, MealCategoryData>): CaloriesState
