package com.example.calories.details

import com.example.calories.FoodEntry
import com.example.calories.details.adpater.EntryRow


sealed interface DetailsState
object Start: DetailsState
data class Initialize(val rows: List<EntryRow>): DetailsState
data class Loading(val showLoading: Boolean): DetailsState
data class EditEntry(val entry: FoodEntry): DetailsState
object NewEntry: DetailsState