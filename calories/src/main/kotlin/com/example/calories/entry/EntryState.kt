package com.example.calories.entry

import com.example.calories.FoodEntry
import com.example.calories.modelconstant.MealType


sealed interface EntryState
object Start: EntryState
object Deleted: EntryState
object Saved: EntryState
object ShowDatePicker: EntryState
data class EntryException(val type: InputCategory): EntryState
data class EnableSave(val enable: Boolean): EntryState
data class InitializeEntry(val entry: FoodEntry): EntryState
data class SetMealType(val type: MealType): EntryState
enum class InputCategory {
    NAME, CALORIES, FAT, PROTEIN, CARBS
}
