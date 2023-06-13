package com.example.calories.main

import co.example.calories.core.mrp.Renderer
import com.example.calories.modelconstant.MealType
import io.reactivex.Observable

interface CaloriesRenderer: Renderer<CaloriesState> {
    val addEntryClicks: Observable<MealType>
    val entriesUpdated: Observable<Unit>
}